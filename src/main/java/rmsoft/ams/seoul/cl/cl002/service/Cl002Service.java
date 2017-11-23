package rmsoft.ams.seoul.cl.cl002.service;

import io.netty.util.internal.StringUtil;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
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

    public Cl00101VO getClassificationScheme() {

        return cl002Mapper.getClassificationScheme();
    }

    public Page<Cl00201VO> getClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        /*cl00201VO.setParentClassUuid(requestParams.getString("parentClassUuid"));
        cl00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00201VO.setClassCode(requestParams.getString("classCode"));
        cl00201VO.setClassLevelUuid(requestParams.getString("classLevelUuid"));
        cl00201VO.setUseYn(requestParams.getString("useYn"));*/

        return filter(cl002Mapper.getClassList(cl00201VO), pageable, "", Cl00201VO.class);
    }

    public Page<Cl00201VO> getSelectedClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        cl00201VO.setOrderNo(requestParams.getString("orderNo"));
        cl00201VO.setOrderKey(requestParams.getString("orderKey"));
        cl00201VO.setParentClassCode(requestParams.getString("parentClassCode"));
        cl00201VO.setParentClassName(requestParams.getString("parentClassName"));
        cl00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00201VO.setClassCode(requestParams.getString("classCodeForm"));
        cl00201VO.setClassName(requestParams.getString("className"));
        cl00201VO.setUseYn(requestParams.getString("useYn"));
        cl00201VO.setClassLevelUuid(requestParams.getString("classLevelUuid"));
        cl00201VO.setClassUuid(requestParams.getString("classUuid"));
        cl00201VO.setOrderKey1(requestParams.getString("orderKey1"));

        return filter(cl002Mapper.getSelectedClassList(cl00201VO), pageable, "", Cl00201VO.class);
    }

    public Page<Cl00201VO> getClassHierarchyList(Pageable pageable, String classificationSchemeUuid) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(classificationSchemeUuid);

        return filter(cl002Mapper.getClassHierarchyList(cl00201VO), pageable, "", Cl00201VO.class);
    }

    public Cl00202VO getSelectedClassDetail(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassUuid(requestParams.getString("classUuid"));
        return cl002Mapper.getSelectedClassDetail(cl00201VO);
    }

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

    @Transactional
    public ApiResponse updateStatusCancel(List<Cl00201VO> list) {
        List<ClClass> clClassList = ModelMapperUtils.mapList(list, ClClass.class);
        ClClass orgClClass = null;
        int index = 0;
        String changeStatus = "";
        for (ClClass clClass : clClassList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgClClass = clClassRepository.findOne(clClass.getId());
            clClass.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD113", changeStatus));
            //clClass.setOrderKey(orgClClass.getOrderKey());
            clClass.setInsertDate(orgClClass.getInsertDate());
            clClass.setInsertUuid(orgClClass.getInsertUuid());
            clClassRepository.save(clClass);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse updateClassList(List<Cl00201VO> list) {
        List<ClClass> clClassList = ModelMapperUtils.mapList(list, ClClass.class);
        ClClass orgClClass = null;
        ClClassCon clClassCon = null;
        String ctUuid = "";
        String detailCode = "";
        String maxCode = "";
        String maxDefaultCode = "";
        String orderKey = "";

        for (ClClass clClass : clClassList) {
            if (StringUtil.isNullOrEmpty(clClass.getClassUuid())) { //Insert

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

                clClass.setClassUuid(UUIDUtils.getUUID()); //UUID 생성
                clClass.setClassCode(detailCode);

               clClass.setClassLevelUuid(CommonCodeUtils.getCodeDetailUuid("CD114", clClass.getClassLevelUuid()));

                clClass.setClassLevelUuid(clClass.getClassLevelUuid());

                // Oracle Function Call
                orderKey = jdbcTemplate.queryForObject("select AMS.FC_CL_CLASS_SORTKEY('" + clClass.getParentClassUuid() + "' , '" + clClass.getOrderNo() + "') from dual", String.class);
                clClass.setOrderKey(orderKey);
                clClass.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD113", "Draft"));

            }

            if (clClass.isCreated() || clClass.isModified()) {
                if (clClass.isModified()) {
                    orgClClass = clClassRepository.findOne(clClass.getId());
                    if(isEmpty(orgClClass.getParentClassUuid())){ orgClClass.setParentClassUuid("");}
                    if(isEmpty(orgClClass.getOrderNo())){ orgClClass.setOrderNo("");}
                    if(isEmpty(clClass.getParentClassUuid())){ clClass.setParentClassUuid("");}
                    if(isEmpty(clClass.getOrderNo())){ clClass.setOrderNo("");}

                    if(!orgClClass.getParentClassUuid().equals(clClass.getParentClassUuid()) ||
                                    !orgClClass.getOrderNo().equals(clClass.getOrderNo())){
                        orderKey = jdbcTemplate.queryForObject("select AMS.FC_CL_CLASS_SORTKEY('" + clClass.getParentClassUuid() + "' , '" + clClass.getOrderNo() + "') from dual", String.class);
                        clClass.setOrderKey(orderKey);
                    }

                    clClass.setInsertDate(orgClClass.getInsertDate());
                    clClass.setInsertUuid(orgClClass.getInsertUuid());
                }
                clClassRepository.save(clClass);
            } else if (clClass.isDeleted()) {
                if (getChildClass(clClass.getClassUuid()) == 0) {
                    clClassRepository.delete(clClass);
                }
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public void updateClassCon(RequestParams<Cl00202VO> requestParams) {
        ClClassCon clClassCon = new ClClassCon();
        if(StringUtils.isEmpty(requestParams.getString("classUuid"))){
            return;
        }
        clClassCon.setClassUuid(requestParams.getString("classUuid"));
        clClassCon.setAddMetadata01(requestParams.getString("detailAddMetadata01"));
        clClassCon.setAddMetadata02(requestParams.getString("detailAddMetadata02"));
        clClassCon.setAddMetadata03(requestParams.getString("detailAddMetadata03"));
        clClassCon.setAddMetadata04(requestParams.getString("detailAddMetadata04"));

        ClClassCon orgClClassCon = null;

        orgClClassCon = clClassConRepository.findOne(clClassCon.getId());

        if(orgClClassCon != null){
            clClassCon.setInsertDate(orgClClassCon.getInsertDate());
            clClassCon.setInsertUuid(orgClClassCon.getInsertUuid());
        }
        clClassConRepository.save(clClassCon);
    }

    public int getChildClass(String classUuid) {
        return cl002Mapper.getChildClass(classUuid);
    }

    public String getMaxClassCode(String classificationSchemeUuid) {
        return cl002Mapper.getMaxClassCode(classificationSchemeUuid);
    }
}
