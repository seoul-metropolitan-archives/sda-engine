package rmsoft.ams.seoul.st.st018.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StRfidTag;

import rmsoft.ams.seoul.common.repository.StRfidTagRepository;
import rmsoft.ams.seoul.st.st002.vo.St002VO;
import rmsoft.ams.seoul.st.st018.dao.St018Mapper;
import rmsoft.ams.seoul.st.st018.vo.St01801VO;
import rmsoft.ams.seoul.st.st018.vo.St01802VO;
import rmsoft.ams.seoul.st.st018.vo.St018PrinterVO;
import rmsoft.ams.seoul.st.st026.dao.St026Mapper;
import rmsoft.ams.seoul.st.st026.service.St026Service;
import rmsoft.ams.seoul.st.st026.vo.St026VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import rmsoft.ams.seoul.utils.PrinterUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St018Service extends BaseService {
    @Inject
    private St018Mapper st018Mapper;
    @Inject
    private St026Mapper st026Mapper;
    @Autowired
    private StRfidTagRepository stRfidTagRepository;


    public Page<St01801VO> getRcAggregation(Pageable pageable, RequestParams<St01801VO> requestParams) {
        St01801VO st01801VO = new St01801VO();
        //검색조건 추가시
        st01801VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st01801VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st01801VO.setLocationUuid(requestParams.getString("locationUuid"));
        st01801VO.setCode(requestParams.getString("code"));
        st01801VO.setTitle(requestParams.getString("title"));

        st01801VO.setPublishDateFrom(requestParams.getString("publishDateFrom"));
        st01801VO.setPublishDateTo(requestParams.getString("publishDateTo"));
        st01801VO.setPublishStatusUuid(requestParams.getString("publishStatusUuid"));

        return filter(st018Mapper.getRcAggregation(st01801VO), pageable, "", St01801VO.class);
    }

    public Page<St01802VO> getStRfidTag(Pageable pageable, RequestParams<St01802VO> requestParams) {
        St01802VO st01802VO = new St01802VO();
        st01802VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        //검색조건 추가시

        return filter(st018Mapper.getStRfidTag(st01802VO), pageable, "", St01802VO.class);
    }

    @Transactional
    public ApiResponse saveTagPublish(List<St01801VO> requestParams) {

        for(St01801VO requestParam : requestParams)
        {
            StRfidTag stRfidTag = ModelMapperUtils.map(requestParams, StRfidTag.class);

            stRfidTag.setRfidTagUuid(UUIDUtils.getUUID());
            stRfidTag.setAggregationUuid(requestParam.getAggregationUuid());
            int seq = jdbcTemplate.queryForObject("SELECT ST_READER_MACHINE_SEQ.NEXTVAL FROM dual", int.class);
            stRfidTag.setSeq(seq);
            // CD221
            //        01	SDA
            //        02	정수점검기
            //        03	PDA
            // TODO : 01, 02, 03 중 뭘넣어야 되는지 모르겠다. 일단 '정수점검기 ( 02 )' 를 넣자.
            stRfidTag.setPublishSourceTypeUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD221", "01"));

            // CD220
            //        01	미발행
            //        02	발행
            //        03	재발행
            St01802VO st01802VO = new St01802VO();
            st01802VO.setAggregationUuid(requestParam.getAggregationUuid());
            // RC_AGGREGATION 이 StRfidTag 목록을 가지는지 체크하기 위함.
            // aggregation 으로 join 함

            List<St01802VO> aSt01802VO = st018Mapper.getStRfidTag(st01802VO);
            if (aSt01802VO.size() == 0) {
                // 한번도 발행한 적이 없음
                stRfidTag.setPublishStatusUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD220", "02"));
            } else {
                // 그전에 발행한 적이 있음
                stRfidTag.setPublishStatusUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD220", "03"));
            }

            stRfidTag.setPublishDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            int tagSequence = jdbcTemplate.queryForObject("SELECT ST_RFID_TAG_SEQ.NEXTVAL FROM dual", int.class);

            St018PrinterVO tmpParam = new St018PrinterVO();
            tmpParam.setAggregationUuid(requestParam.getAggregationUuid());
            St018PrinterVO st018PrinterVO = st018Mapper.getAggreationForPrint(tmpParam);
            PrinterUtils.ModelPrinter modelPrinter = new PrinterUtils.ModelPrinter();
            //////////  Tag 발행규칙
            ///////// ex) {"서울기록원","2010","30년","0000000000002547","0000000000002547","KKRBSAA1234A"}
            //생산기관
            modelPrinter.mkOrgName = st018PrinterVO.getAuthorityName();
            //생산년도
            String mkYear = st018PrinterVO.getCreationStartDate();
            if( mkYear != null && 3 < mkYear.length() ){
                // 네자리로 잘라야 되므로.
                mkYear = mkYear.substring(0, 4);
            }
            modelPrinter.mkYear = mkYear;
            //보존기간
            modelPrinter.consDtStr = st018PrinterVO.getRetentionPeriodName();
            //관리번호
            modelPrinter.assetNo = st018PrinterVO.getAggregationCode();
            //바코드번호
            modelPrinter.barcNo = st018PrinterVO.getAggregationCode();
            // rfid 번호
            modelPrinter.rfidNo = "KKRESAA" + PrinterUtils.convertSequenceTo5Char(tagSequence); // KKRESAA는 고정값.

            String rfidMachineUuid = requestParam.getRfidMachineUuid();
            St026VO tmpSt026VO = new St026VO();
            tmpSt026VO.setRfidMachineUuid(rfidMachineUuid);
///*
            List<St026VO> aMachine = st026Mapper.getStRfidMachine(tmpSt026VO);
            if (aMachine.size() == 0) {
                throw new ApiException(ApiStatus.SYSTEM_ERROR, "UUID( " + rfidMachineUuid + " )에 해당하는 프린터가 없습니다.");
            }
            St026VO machineVO = aMachine.get(0); // 리스트로 가져오지만, 하나만 존재하므로 0번에서 빼옴

            // ip와 port 가 같이 들어 있음
            String[] splittedIpAndPort = machineVO.getIp().split(":");
            if (splittedIpAndPort.length == 1) {
                throw new ApiException(ApiStatus.SYSTEM_ERROR, "ip:port 형태로 프린터설정이 되어 있어야 합니다. ex) 192.168.0.2:9000");
            }
            PrinterUtils.startPrint(splittedIpAndPort[0], splittedIpAndPort[1], modelPrinter);//*/
            stRfidTag.setTag(PrinterUtils.generateRfidNo(modelPrinter.rfidNo));
            stRfidTagRepository.save(stRfidTag);
        }


        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


    /*public void saveExceptRecordResult(List<St01102VO> list) {
        List<StExceptRecordResult> stExceptRecordResults = ModelMapperUtils.mapList(list, StExceptRecordResult.class);

        for(StExceptRecordResult stExceptRecordResult : stExceptRecordResults){
            stExceptRecordResult.setExceptRecordResultUuid(UUIDUtils.getUUID());
            stExceptRecordResultRepository.save(stExceptRecordResult);
        }


    }

    public Page<St01102VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        St01102VO st01102VO = new St01102VO();
        st01102VO.setInoutExceptUuid(requestParams.getString("inoutExceptUuid"));
        return filter(st018Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
