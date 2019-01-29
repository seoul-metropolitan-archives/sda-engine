package rmsoft.ams.seoul.st.st004.service;

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
import rmsoft.ams.seoul.common.domain.StArrangeContainersResult;
import rmsoft.ams.seoul.common.repository.StArrangeContainersResultRepository;
import rmsoft.ams.seoul.st.st001.vo.St00103VO;
import rmsoft.ams.seoul.st.st002.vo.St00201VO;
import rmsoft.ams.seoul.st.st004.dao.St004Mapper;
import rmsoft.ams.seoul.st.st004.vo.St00401VO;
import rmsoft.ams.seoul.st.st004.vo.St00402VO;
import rmsoft.ams.seoul.st.st004.vo.St00403VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class St004Service extends BaseService {
    @Inject
    private St004Mapper st004Mapper;
    @Autowired
    private StArrangeContainersResultRepository stArrangeContainersResultRepository;

    public Page<St00401VO> getStArrangeContainersResult(Pageable pageable, RequestParams<St00401VO> requestParams) {
        St00401VO st00401VO = new St00401VO();
        st00401VO.setStatusUuid(requestParams.getString("statusUuid"));
        st00401VO.setContainerType(requestParams.getString("containerTypeUuid"));
        st00401VO.setContainerName(requestParams.getString("containerName"));
        st00401VO.setLocationUuid(requestParams.getString("locationUuid"));
        return filter(st004Mapper.getStArrangeContainersResult(st00401VO), pageable, "", St00401VO.class);
    }

    public ApiResponse updateStatus(List<St00401VO> list) {
        List<StArrangeContainersResult> stArrangeContainersResultList = ModelMapperUtils.mapList(list,StArrangeContainersResult.class);
        StArrangeContainersResult orgStArrangeContainerResult = null;
        int index = 0;
        String changeStatus = "";
        for(StArrangeContainersResult stArrangeContainersResult : stArrangeContainersResultList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStArrangeContainerResult = stArrangeContainersResultRepository.findOne(stArrangeContainersResult.getId());
            stArrangeContainersResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stArrangeContainersResult.setInsertDate(orgStArrangeContainerResult.getInsertDate());
            stArrangeContainersResult.setInsertUuid(orgStArrangeContainerResult.getInsertUuid());
            stArrangeContainersResult.setArrangedDate(orgStArrangeContainerResult.getArrangedDate());
            stArrangeContainersResult.setContainerUuid(orgStArrangeContainerResult.getContainerUuid());
            stArrangeContainersResult.setLocationUuid(orgStArrangeContainerResult.getLocationUuid());
            stArrangeContainersResultRepository.save(stArrangeContainersResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveArrangeRecordList(List<St00401VO> list) {
        List<StArrangeContainersResult> stArrangeContainersResultList = ModelMapperUtils.mapList(list,StArrangeContainersResult.class);
        StArrangeContainersResult orgStArrangeContainerResult = null;
        for(StArrangeContainersResult stArrangeContainersResult : stArrangeContainersResultList) {
            if(stArrangeContainersResult.isDeleted()){
                stArrangeContainersResultRepository.delete(stArrangeContainersResult);
            }else{
                if(stArrangeContainersResult.isModified()){
                    orgStArrangeContainerResult = stArrangeContainersResultRepository.findOne(stArrangeContainersResult.getId());
                    stArrangeContainersResult.setInsertDate(orgStArrangeContainerResult.getInsertDate());
                    stArrangeContainersResult.setInsertUuid(orgStArrangeContainerResult.getInsertUuid());
                    stArrangeContainersResultRepository.save(stArrangeContainersResult);
                }else if(stArrangeContainersResult.getArrangeContainersResultUuid() != null){
                    stArrangeContainersResultRepository.delete(stArrangeContainersResult);
                }
                else{


                    //이쪽에서 들어 가야 되는데 전부 다 들어 가야된다!?
                    //자식을 전부다 빼와야 된다?

                    StringBuilder sb = new StringBuilder();
                    sb.append(" select * from ( ");
                    sb.append(" SELECT");
                    sb.append("       CONTAINER_UUID as containerUuid ");
                    sb.append(" FROM ST_CONTAINER A ");
                    sb.append(" WHERE FC_AD_CODE_NM(STATUS_UUID) = 'Confirm' ");
                    sb.append(" START WITH A.CONTAINER_UUID = '"+stArrangeContainersResult.getContainerUuid()+"' ");
                    sb.append(" CONNECT BY PRIOR CONTAINER_UUID = PARENT_CONTAINER_UUID ");
                    sb.append(" ORDER SIBLINGS BY ORDER_KEY ) main ");
                    sb.append(" MINUS ");
                    sb.append(" select CONTAINER_UUID as containerUuid ");
                    sb.append(" from ST_ARRANGE_CONTAINERS_RESULT ");
                    sb.append(" WHERE LOCATION_UUID = '"+stArrangeContainersResult.getLocationUuid()+"' ");


                    List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sb.toString());


                    for(int i = 0 ; i < resultList.size() ; i++){
                        stArrangeContainersResult.setArrangeContainersResultUuid(UUIDUtils.getUUID());
                        stArrangeContainersResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                        stArrangeContainersResult.setArrangedDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                        //stArrangeContainersResult.setDescription("");
                        stArrangeContainersResult.setContainerUuid(String.valueOf(resultList.get(i).get("containerUuid")));

                        stArrangeContainersResultRepository.save(stArrangeContainersResult);
                    }

                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public Page<St00402VO> getLocationList(Pageable pageable, RequestParams<St00402VO> requestParams) {
        St00402VO st00402VO = new St00402VO();
        st00402VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st00402VO.setRowNo(requestParams.getString("rowNo"));
        st00402VO.setColumnNo(requestParams.getString("columnNo"));
        st00402VO.setStatusUuid (requestParams.getString("statusUuid"));
        st00402VO.setUseYn(requestParams.getString("useYn"));

        return filter(st004Mapper.getLocationList(st00402VO), pageable, "", St00402VO.class);
    }

    public Page<St00403VO> getSelectedContainerList(Pageable pageable, RequestParams<St00403VO> requestParams) {

        St00403VO st00403VO = new St00403VO();
        st00403VO.setContainerUuid(requestParams.getString("containerUuid"));
        st00403VO.setOrderNo(requestParams.getString("orderNo"));
        st00403VO.setOrderKey(requestParams.getString("orderKey"));
        st00403VO.setStatusUuid(requestParams.getString("statusUuid"));
        st00403VO.setUseYn(requestParams.getString("useYn"));
        st00403VO.setOrderKey1(requestParams.getString("orderKey1"));

        //검색 조건 추가
        st00403VO.setContainerName(requestParams.getString("containerName"));
        st00403VO.setContainerTypeUuid(requestParams.getString("containerTypeUuid"));

        return filter(st004Mapper.getSelectedContainerList(st00403VO), pageable, "", St00403VO.class);
    }
}