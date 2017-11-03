package rmsoft.ams.seoul.cl.cl002.service;

import io.netty.util.internal.StringUtil;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl002.dao.Cl002Mapper;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00202VO;
import rmsoft.ams.seoul.common.domain.ClClass;
import rmsoft.ams.seoul.common.domain.ClClassificationScheme;
import rmsoft.ams.seoul.common.domain.ClClassificationSchemeCon;
import rmsoft.ams.seoul.common.repository.ClClassRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

@Service
public class Cl002Service extends BaseService {

    @Inject
    private Cl002Mapper cl002Mapper;

    @Autowired
    private ClClassRepository clClassRepository;

    public Cl00101VO getClassificationScheme(){

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
        if(StringUtils.isNotEmpty(requestParams.getString("orderNo")) ){
            cl00201VO.setOrderNo(Integer.parseInt(requestParams.getString("orderNo")));
        }
        if(StringUtils.isNotEmpty(requestParams.getString("orderKey"))){
            cl00201VO.setOrderKey(requestParams.getString("orderKey").replaceAll("[.]","-"));
        }
        cl00201VO.setParentClassCode(requestParams.getString("parentClassCode"));
        cl00201VO.setParentClassName(requestParams.getString("parentClassName"));
        cl00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00201VO.setClassCode(requestParams.getString("classCode"));
        cl00201VO.setClassName(requestParams.getString("className"));
        cl00201VO.setUseYn(requestParams.getString("useYn"));
        cl00201VO.setClassLevelUuid(requestParams.getString("classLevelUuid"));

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
    public ApiResponse updateStatus(List<Cl00201VO> list){
        List<ClClass> clClassList = ModelMapperUtils.mapList(list,ClClass.class);
        ClClass orgClClass = null;
        int index = 0;
        String changeStatus = "";
        for (ClClass clClass : clClassList) {
            changeStatus = list.get(index).getChangeStatus() == "" ?  "Draft" : list.get(index).getChangeStatus();
            orgClClass = clClassRepository.findOne(clClass.getId());
            clClass.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD113",changeStatus));
            //clClass.setOrderKey(orgClClass.getOrderKey());
            clClass.setInsertDate(orgClClass.getInsertDate());
            clClass.setInsertUuid(orgClClass.getInsertUuid());
            clClassRepository.save(clClass);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateClassList(List<Cl00202VO> list){
        List<ClClass> clClassList = ModelMapperUtils.mapList(list,ClClass.class);
        ClClass orgClClass = null;
        ClClassificationSchemeCon clClassificationSchemeCon = null;
        String ctUuid = "";
        String detailCode = "";
        for (ClClass clClass : clClassList) {
            if(StringUtil.isNullOrEmpty(clClass.getClassificationSchemeUuid())){ //Insert
                clClass.setClassUuid(UUIDUtils.getUUID()); //UUID 생성
            }

            if (clClass.isCreated() || clClass.isModified()) {
                clClass.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Draft"));

                if(clClass.isModified()) {
                    orgClClass = clClassRepository.findOne(clClass.getId());
                    clClass.setInsertDate(orgClClass.getInsertDate());
                    clClass.setInsertUuid(orgClClass.getInsertUuid());
                }
                clClassRepository.save(clClass);
            } else if (clClass.isDeleted()) {
                /*if(getChildClass(clClassificationScheme.getClassificationSchemeUuid()) == 0 ){
                    clClassificationSchemeRepository.delete(clClassificationScheme);
                    clClassificationSchemeCon = new ClClassificationSchemeCon();
                    clClassificationSchemeCon.setClassificationSchemeUuid(clClassificationScheme.getClassificationSchemeUuid());
                    clClassificationSchemeConRepository.delete(clClassificationSchemeCon);
                }*/
                //연관 테이블 데이터 삭제 (상세)
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
