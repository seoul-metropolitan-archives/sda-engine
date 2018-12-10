package rmsoft.ams.seoul.st.st006.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.repository.ClClassConRepository;
import rmsoft.ams.seoul.common.repository.ClClassRepository;
import rmsoft.ams.seoul.common.repository.ClClassifyRecordResultRepository;
import rmsoft.ams.seoul.st.st006.dao.St006Mapper;
import rmsoft.ams.seoul.st.st006.vo.St00601VO;
import rmsoft.ams.seoul.st.st006.vo.St00603VO;

import javax.inject.Inject;
import java.util.List;


/**
 * The type Cl 003 service.
 */
@Service
public class St006Service extends BaseService {

    @Inject
    private St006Mapper st006Mapper;
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
    public Page<St00601VO> getStContainer(Pageable pageable, RequestParams<St00601VO> requestParams) {

        St00601VO st00601VO = new St00601VO();
        // st00601VO.setClassUuid(requestParams.getString("classUuid"));
        // st00601VO.setStatusUuid(requestParams.getString("statusUuid01"));
//        st00601VO.setAggregationCode(requestParams.getString("aggregationCode"));
//        st00601VO.setTitle(requestParams.getString("title"));
//        st00601VO.setTypeUuid(requestParams.getString("typeUuid"));
//        st00601VO.setArrangedFromDate(requestParams.getString("arrangedFromDate01"));
//        st00601VO.setArrangedToDate(requestParams.getString("arrangedToDate01"));
//        st00601VO.setContainerUuid(requestParams.getString("containerUuid"));
        return filter(st006Mapper.getStContainer(st00601VO), pageable, "", St00601VO.class);
    }


    public Page<St00603VO> getSelectedItem(Pageable pageable, RequestParams<St00603VO> requestParams) {

        St00603VO vo = new St00603VO();
        vo.setContainerUuid(requestParams.getString("containerUuid"));
//        vo.setStatusUuid(requestParams.getString("statusUuid02"));
        List<St00603VO> ddd = st006Mapper.getSelectedItem(vo);
        return filter(ddd, pageable, "", St00603VO.class);
    }

    /*public ApiResponse updateStatus(List<St00601VO> list) {
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
    public ApiResponse saveClassifiedRecordList(St00602VO st00602VO) {
        List<St00601VO> st00601VOList = st00602VO.getst00601VOList();
        List<ClClassifyRecordsResult> clClassifiedRecordsList = ModelMapperUtils.mapList(st00601VOList,ClClassifyRecordsResult.class);
        ClClassifyRecordsResult orgClClassifyRecordsResult = null;
        int i = 0;
        for(ClClassifyRecordsResult clClassifyRecordsResult : clClassifiedRecordsList) {
            if(st00602VO.getClassUuid()== null){ //삭제
                clClassifyRecordResultRepository.delete(clClassifyRecordsResult);
            }else if(null != clClassifyRecordsResult.getClassifyRecordsUuid() && !"".equals(clClassifyRecordsResult.getClassifyRecordsUuid())){ //수정
                orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
                orgClClassifyRecordsResult.setChoiceYn(clClassifyRecordsResult.getChoiceYn());
                orgClClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
                clClassifyRecordResultRepository.save(orgClClassifyRecordsResult);
            }else{ //신규
                clClassifyRecordsResult.setClassifyRecordsUuid(UUIDUtils.getUUID());
                clClassifyRecordsResult.setClassUuid(st00602VO.getClassUuid());
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

        return filter(st006Mapper.getSelectedItem(st00303VO), pageable, "", St00303VO.class);
    }
    public Page<St00303VO> getSelectedItemSchedule(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setRecordScheduleUuid(requestParams.getString("recordScheduleUuid"));

        return filter(st006Mapper.getSelectedItemSchedule(st00303VO), pageable, "", St00303VO.class);
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
        nodes.addAll(st006Mapper.getAggregationNode(param));
        return nodes;
    }
    public List<Rc00101VO> getAllNodeSchedule(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(st006Mapper.getAggregationNodeSchedule(param));
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
    }*/
}
