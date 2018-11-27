package rmsoft.ams.seoul.cl.cl003.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.model.ModelMapperConverter;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.cl.cl003.dao.Cl003Mapper;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00302VO;
import rmsoft.ams.seoul.common.domain.ClClass;
import rmsoft.ams.seoul.common.domain.ClClassCon;
import rmsoft.ams.seoul.common.domain.ClClassifyRecordsResult;
import rmsoft.ams.seoul.common.repository.ClClassConRepository;
import rmsoft.ams.seoul.common.repository.ClClassRepository;
import rmsoft.ams.seoul.common.repository.ClClassifyRecordResultRepository;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Cl 003 service.
 */
@Service
public class Cl003Service extends BaseService {

    @Inject
    private Cl003Mapper cl003Mapper;
    @Autowired
    private ClClassRepository clClassRepository;
    @Autowired
    private ClClassConRepository clClassConRepository;
    @Autowired
    private ClClassifyRecordResultRepository clClassifyRecordResultRepository;

    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    public Page<Cl00301VO> getClassAggregationList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {

        Cl00301VO cl00301VO = new Cl00301VO();
        cl00301VO.setClassUuid(requestParams.getString("classUuid"));
        cl00301VO.setStatusUuid(requestParams.getString("statusUuid01"));
//        cl00301VO.setAggregationCode(requestParams.getString("aggregationCode"));
//        cl00301VO.setTitle(requestParams.getString("title"));
//        cl00301VO.setTypeUuid(requestParams.getString("typeUuid"));
//        cl00301VO.setArrangedFromDate(requestParams.getString("arrangedFromDate01"));
//        cl00301VO.setArrangedToDate(requestParams.getString("arrangedToDate01"));
//        cl00301VO.setContainerUuid(requestParams.getString("containerUuid"));
        return filter(cl003Mapper.getClassAggregationList(cl00301VO), pageable, "", Cl00301VO.class);
    }


    public Page<Cl00301VO> getClassItemList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {

        Cl00301VO cl00301VO = new Cl00301VO();
        cl00301VO.setClassUuid(requestParams.getString("classUuid"));
        cl00301VO.setStatusUuid(requestParams.getString("statusUuid02"));
        return filter(cl003Mapper.getClassItemList(cl00301VO), pageable, "", Cl00301VO.class);
    }

    public ApiResponse updateStatus(List<Cl00301VO> list) {
        List<ClClassifyRecordsResult> clClassifiedRecordsList = ModelMapperUtils.mapList(list,ClClassifyRecordsResult.class);
        ClClassifyRecordsResult orgClClassifyRecordsResult = null;
        int index = 0;
        String changeStatus = "";
        for(ClClassifyRecordsResult clClassifyRecordsResult : clClassifiedRecordsList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
            clClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111",changeStatus));
            clClassifyRecordsResult.setInsertDate(orgClClassifyRecordsResult.getInsertDate());
            clClassifyRecordsResult.setInsertUuid(orgClClassifyRecordsResult.getInsertUuid());
            clClassifyRecordResultRepository.save(clClassifyRecordsResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    @Transactional
    public ApiResponse saveClassifiedRecordList(Cl00302VO cl00302VO) {
        List<Cl00301VO> cl00301VOList = cl00302VO.getCl00301VOList();
        List<ClClassifyRecordsResult> clClassifiedRecordsList = ModelMapperUtils.mapList(cl00301VOList,ClClassifyRecordsResult.class);
        ClClassifyRecordsResult orgClClassifyRecordsResult = null;
        int i = 0;
        for(ClClassifyRecordsResult clClassifyRecordsResult : clClassifiedRecordsList) {
            if(cl00302VO.getClassUuid()== null){ //삭제
                clClassifyRecordResultRepository.delete(clClassifyRecordsResult);
            }else if(null != clClassifyRecordsResult.getClassifyRecordsUuid() && !"".equals(clClassifyRecordsResult.getClassifyRecordsUuid())){ //수정
                orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
                orgClClassifyRecordsResult.setChoiceYn(clClassifyRecordsResult.getChoiceYn());
                orgClClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
                clClassifyRecordResultRepository.save(orgClClassifyRecordsResult);
            }else{ //신규
                clClassifyRecordsResult.setClassifyRecordsUuid(UUIDUtils.getUUID());
                clClassifyRecordsResult.setClassUuid(cl00302VO.getClassUuid());
                clClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
                clClassifyRecordsResult.setClassifiedDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                clClassifyRecordResultRepository.save(clClassifyRecordsResult);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    public Page<St00303VO> getSelectedItem(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setClassUuid(requestParams.getString("classUuid"));

        return filter(cl003Mapper.getSelectedItem(st00303VO), pageable, "", St00303VO.class);
    }
    public Page<St00303VO> getSelectedItemSchedule(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setRecordScheduleUuid(requestParams.getString("recordScheduleUuid"));

        return filter(cl003Mapper.getSelectedItemSchedule(st00303VO), pageable, "", St00303VO.class);
    }
    public Cl00201VO getClassInfo(Pageable pageable, RequestParams<Cl00201VO> params) {
        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassUuid(params.getString("classUuid"));
        ClClass clClass = ModelMapperUtils.map(cl00201VO, ClClass.class);
        clClass = clClassRepository.findOne(clClass.getId());
        cl00201VO = ModelMapperUtils.map(clClass,Cl00201VO.class);

        ClClassCon clClassCon = ModelMapperUtils.map(cl00201VO, ClClassCon.class);
        clClassCon = clClassConRepository.findOne(clClassCon.getId());

        cl00201VO.setScopeContent(clClassCon.getScopeContent());
        cl00201VO.setRulesConversionUuid(clClassCon.getRulesConversionUuid());
        return cl00201VO;
    }
    public List<Rc00101VO> getAllNode(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(cl003Mapper.getAggregationNode(param));
        return nodes;
    }
    public List<Rc00101VO> getAllNodeSchedule(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(cl003Mapper.getAggregationNodeSchedule(param));
        return nodes;
    }
    @Transactional
    public ApiResponse saveClassDescription(Cl00201VO cl00201VO){
        ClClass clClass = ModelMapperUtils.map(cl00201VO, ClClass.class);
        ClClass orgClClass = clClassRepository.findOne(clClass.getId());
        if(orgClClass != null){
            clClass = orgClClass;
            clClass.setDescription(cl00201VO.getDescription());
            clClass.setStatusDescription(cl00201VO.getStatusDescription());
            clClass.setLevelOfDetailUuid(cl00201VO.getLevelOfDetailUuid());
            clClassRepository.save(clClass);
        }
        ClClassCon clClassCon = ModelMapperUtils.map(cl00201VO, ClClassCon.class);
        ClClassCon orgClClassCon = clClassConRepository.findOne(clClassCon.getId());

        if(orgClClassCon != null){
            orgClClassCon.setScopeContent(clClassCon.getScopeContent());
            orgClClassCon.setRulesConversionUuid(clClassCon.getRulesConversionUuid());
            orgClClassCon.setUpdateDate(null);
            orgClClassCon.setUpdateUuid(null);
            clClassConRepository.save(orgClClassCon);
        }else{
            clClassConRepository.save(clClassCon);
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
