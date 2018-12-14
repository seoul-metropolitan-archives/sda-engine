package rmsoft.ams.seoul.cl.cl002.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl002.dao.Cl002Mapper;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00202VO;
import rmsoft.ams.seoul.common.domain.ClClass;
import rmsoft.ams.seoul.common.domain.ClClassCon;
import rmsoft.ams.seoul.common.repository.ClClassConRepository;
import rmsoft.ams.seoul.common.repository.ClClassRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Cl 002 service.
 */
@Service
public class Cl002Service extends BaseService {

    @Inject
    private Cl002Mapper cl002Mapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ClClassRepository clClassRepository;

    @Autowired
    private ClClassConRepository clClassConRepository;

    /**
     * Gets classification scheme.
     *
     * @return the classification scheme
     */
    public Cl00101VO getClassificationScheme() {

        return cl002Mapper.getClassificationScheme();
    }

    /**
     * Gets class list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the class list
     */
    public Page<Cl00201VO> getClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        cl00201VO.setParentClassUuid(requestParams.getString("parentClassUuid"));
        cl00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00201VO.setClassCode(requestParams.getString("classCode"));
        cl00201VO.setClassLevelUuid(requestParams.getString("classLevelUuid"));
        cl00201VO.setUseYn(requestParams.getString("useYn"));

        return filter(cl002Mapper.getClassList(cl00201VO), pageable, "", Cl00201VO.class);
    }

    /**
     * Gets selected class list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the selected class list
     */
    public Page<Cl00201VO> getSelectedClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        cl00201VO.setOrderNo(requestParams.getString("orderNo"));
        cl00201VO.setOrderKey(requestParams.getString("orderKey"));
        cl00201VO.setParentClassCode(StringUtils.upperCase(requestParams.getString("parentClassCode")));
        cl00201VO.setParentClassName(StringUtils.upperCase(requestParams.getString("parentClassName")));
        cl00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00201VO.setClassCode(StringUtils.upperCase(requestParams.getString("classCodeForm")));
        cl00201VO.setClassName(StringUtils.upperCase(requestParams.getString("className")));
        cl00201VO.setUseYn(requestParams.getString("useYn"));
        cl00201VO.setClassLevelUuid(requestParams.getString("classLevelUuid"));
        cl00201VO.setClassUuid(requestParams.getString("classUuid"));
        cl00201VO.setOrderKey1(requestParams.getString("orderKey1"));


        return filter(cl002Mapper.getSelectedClassList(cl00201VO), pageable, "", Cl00201VO.class);
    }

    /**
     * Gets class hierarchy list.
     *
     * @param pageable                 the pageable
     * @param classificationSchemeUuid the classification scheme uuid
     * @return the class hierarchy list
     */
    public Page<Cl00201VO> getClassHierarchyList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        cl00201VO.setAggregationUuid(requestParams.getString("aggregationUuid"));

        if(requestParams.getString("className").equals("cl003")){
            return filter(cl002Mapper.getClassHierarchyListForClassify(cl00201VO), pageable, "", Cl00201VO.class);
        }else{
            return filter(cl002Mapper.getClassHierarchyList(cl00201VO), pageable, "", Cl00201VO.class);
        }
    }

    /**
     * Gets selected class detail.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the selected class detail
     */
    public Cl00202VO getSelectedClassDetail(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassUuid(requestParams.getString("classUuid"));
        return cl002Mapper.getSelectedClassDetail(cl00201VO);
    }

    /**
     * Update status confirm.
     *
     * @param lists the lists
     */
    @Transactional
    public void updateStatusConfirm(List<Cl00201VO> lists) {
        List<ClClass> clClassList = ModelMapperUtils.mapList(lists, ClClass.class);
        ClClass orgClClass = null;
        String changeStatusNm = "";
        String changeStatusCd = "";
        int index = 0;
        for (Cl00201VO cl00201VO : lists) {
            orgClClass = clClassRepository.findOne(clClassList.get(index).getId());
            changeStatusNm = cl00201VO.getChangeStatus() == "" ? "Draft" : cl00201VO.getChangeStatus();
            changeStatusCd = CommonCodeUtils.getCodeDetailUuid("CD113", changeStatusNm);
            if (orgClClass.getStatusUuid() != changeStatusCd) {
                cl00201VO.setStatusUuid(changeStatusCd);
                cl00201VO.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                cl00201VO.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            }
            cl002Mapper.updateStatusConfirm(cl00201VO);
            index++;
        }
    }

    /**
     * Update status cancel api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse updateStatusCancel(List<Cl00201VO> lists)  {
        try{
            List<ClClass> clClassList = ModelMapperUtils.mapList(lists, ClClass.class);
            ClClass orgClClass = null;
            String changeStatusNm = "";
            String changeStatusCd = "";
            int index = 0;
            for (Cl00201VO cl00201VO : lists) {
                if(0 < getClassForClassify(cl00201VO)){
                   throw new Exception();
                }else{
                    orgClClass = clClassRepository.findOne(clClassList.get(index).getId());
                    changeStatusNm = cl00201VO.getChangeStatus() == "" ? "Draft" : cl00201VO.getChangeStatus();
                    changeStatusCd = CommonCodeUtils.getCodeDetailUuid("CD113", changeStatusNm);
                    if (orgClClass.getStatusUuid() != changeStatusCd) {
                        cl00201VO.setStatusUuid(changeStatusCd);
                        cl00201VO.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                        cl00201VO.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                    }
                    cl002Mapper.updateStatusConfirm(cl00201VO);
                    index++;
                }
            }
//            List<ClClass> clClassList = ModelMapperUtils.mapList(list, ClClass.class);
//            ClClass orgClClass = null;
//            int index = 0;
//            String changeStatus = "";
//            for (ClClass clClass : clClassList) {
//                //Classify 체크
//                changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
//                orgClClass = clClassRepository.findOne(clClass.getId());
//                if(!orgClClass.getStatusUuid().equals(CommonCodeUtils.getCodeDetailUuid("CD113", changeStatus))){
//                    clClass.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD113", changeStatus));
//                    clClass.setInsertDate(orgClClass.getInsertDate());
//                    clClass.setInsertUuid(orgClClass.getInsertUuid());
//                    clClassRepository.save(clClass);
//                }
//                index++;
//            }
            return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
        }catch(Exception e){
            return ApiResponse.of(ApiStatus.SUCCESS, "CLASSIFIED");
        }
    }

    /**
     * Update class list api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse updateClassList(List<Cl00201VO> list) {
        List<ClClass> clClassList = ModelMapperUtils.mapList(list, ClClass.class);
        ClClass orgClClass = null;
        ClClassCon clClassCon = null;
        ClClassCon orgClClassCon = null;
        String ctUuid = "";
        String detailCode = "";
        String maxCode = "";
        String maxDefaultCode = "";
        String orderKey = "";
        int index = 0;

        for (ClClass clClass : clClassList) {
                maxCode = getMaxClassCode(clClass.getClassificationSchemeUuid());
                if (StringUtils.isNotEmpty(maxCode)) { //분류코드 조합
                    maxDefaultCode = StringUtils.trim(maxCode).substring(0, 7);
                    ctUuid = StringUtils.trim(maxCode).substring(7);
                    ctUuid = String.valueOf(Integer.parseInt(ctUuid) + 1);

                    if (ctUuid.length() == 1) {
                        ctUuid = "000" + ctUuid;
                    } else if (ctUuid.length() == 2) {
                        ctUuid = "00" + ctUuid;
                    } else if (ctUuid.length() == 3) {
                        ctUuid = "0" + ctUuid;
                    }
                    detailCode = maxDefaultCode + ctUuid;
                }
            if (clClass.isCreated()){
                ctUuid = jdbcTemplate.queryForObject("select FC_CL_CLS_CLASS_CODE('" + clClass.getClassificationSchemeUuid() + "') from dual", String.class);
                clClass.setClassCode(ctUuid);
                // Oracle Function Call
                orderKey = jdbcTemplate.queryForObject("select FC_CL_CLASS_SORTKEY('" + clClass.getParentClassUuid() + "' , '" + clClass.getOrderNo() + "') from dual", String.class);
                clClass.setOrderKey(orderKey);
                clClass.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD113", "Draft"));

            }

            if (!clClass.isDeleted()) {
                if (clClass.isModified()) {
                    orgClClass = clClassRepository.findOne(clClass.getId());
                    if(isEmpty(orgClClass.getParentClassUuid())){ orgClClass.setParentClassUuid("");}
                    if(isEmpty(orgClClass.getOrderNo())){ orgClClass.setOrderNo("");}
                    if(isEmpty(clClass.getParentClassUuid())){ clClass.setParentClassUuid("");}
                    if(isEmpty(clClass.getOrderNo())){ clClass.setOrderNo("");}

                    if(!orgClClass.getParentClassUuid().equals(clClass.getParentClassUuid()) ||
                            !orgClClass.getOrderNo().equals(clClass.getOrderNo())){
                        orderKey = jdbcTemplate.queryForObject("select FC_CL_CLASS_SORTKEY('" + clClass.getParentClassUuid() + "' , '" + clClass.getOrderNo() + "') from dual", String.class);
                        clClass.setOrderKey(orderKey);
                    }

                    clClass.setInsertDate(orgClClass.getInsertDate());
                    clClass.setInsertUuid(orgClClass.getInsertUuid());
                }
                clClassRepository.save(clClass);

                clClassCon = new ClClassCon();
                clClassCon.setClassUuid(clClass.getClassUuid());
                orgClClassCon = clClassConRepository.findOne(clClassCon.getId());

                orgClClassCon = orgClClassCon== null ? clClassCon : orgClClassCon;

                orgClClassCon.setCreationStartDate(list.get(index).getCreationStartDate());
                orgClClassCon.setCreationEndDate(list.get(index).getCreationEndDate());
                orgClClassCon.setAccumulationStartDate(list.get(index).getAccumulationStartDate());
                orgClClassCon.setAccumulationEndDate(list.get(index).getAccumulationEndDate());
                clClassConRepository.save(orgClClassCon);

            } else if (clClass.isDeleted()) {
                if (getChildClass(clClass.getClassUuid()) == 0) {

                    clClassCon = new ClClassCon();
                    clClassCon.setClassUuid(clClass.getClassUuid());
                    orgClClassCon = clClassConRepository.findOne(clClassCon.getId());
                    if(orgClClassCon != null){
                        clClassConRepository.delete(clClassCon);
                    }
                    clClassRepository.delete(clClass);
                }else{
                    throw new ApiException("CL", "002_01");
                }
            }
            index++;
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update class con.
     *
     * @param requestParams the request params
     */
    @Transactional
    public ApiResponse updateClassCon(Cl00202VO cl00202Vo) {


        ClClassCon clClassCon = new ClClassCon();
        clClassCon.setClassUuid(cl00202Vo.getClassUuid());
        ClClassCon orgClClassCon = clClassConRepository.findOne(clClassCon.getId());

        orgClClassCon = orgClClassCon == null ? clClassCon : orgClClassCon;
        orgClClassCon.setScopeContent(cl00202Vo.getScopeContent());
        orgClClassCon.setRulesConversionUuid(cl00202Vo.getRulesConversionUuid());
        orgClClassCon.setCreationStartDate(cl00202Vo.getCreationStartDate());
        orgClClassCon.setCreationEndDate(cl00202Vo.getCreationEndDate());
        orgClClassCon.setAccumulationStartDate(cl00202Vo.getAccumulationStartDate());
        orgClClassCon.setAccumulationEndDate(cl00202Vo.getAccumulationEndDate());
        clClassConRepository.save(orgClClassCon);

        ClClass clClass = new ClClass();
        clClass.setClassUuid(cl00202Vo.getClassUuid());
        ClClass orgClClass = clClassRepository.findOne(clClass.getId());
        orgClClass.setStatusDescription(cl00202Vo.getStatusDescription());
        orgClClass.setLevelOfDetailUuid(cl00202Vo.getLevelOfDetailUuid());
        orgClClass.setDescription(cl00202Vo.getDescription());
        clClassRepository.save(orgClClass);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Gets child class.
     *
     * @param classUuid the class uuid
     * @return the child class
     */
    public int getChildClass(String classUuid) {
        return cl002Mapper.getChildClass(classUuid);
    }

    /**
     * Gets max class code.
     *
     * @param classificationSchemeUuid the classification scheme uuid
     * @return the max class code
     */
    public String getMaxClassCode(String classificationSchemeUuid) {
        return cl002Mapper.getMaxClassCode(classificationSchemeUuid);
    }

    public int getClassForClassify(Cl00201VO cl00201VO){
        return cl002Mapper.getClassForClassify(cl00201VO);
    }
}
