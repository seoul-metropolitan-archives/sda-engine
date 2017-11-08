package rmsoft.ams.seoul.cl.cl001.service;

import io.netty.util.internal.StringUtil;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl001.dao.Cl001Mapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00102VO;
import rmsoft.ams.seoul.common.domain.ClClassificationScheme;
import rmsoft.ams.seoul.common.domain.ClClassificationSchemeCon;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeConRepository;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Cl001Service extends BaseService {

    @Inject
    private Cl001Mapper cl001Mapper;

    @Autowired
    private ClClassificationSchemeConRepository clClassificationSchemeConRepository;

    @Autowired
    private ClClassificationSchemeRepository clClassificationSchemeRepository;

    public Page<Cl00101VO> getClassificationSchemeList(Pageable pageable, RequestParams<Cl00101VO> requestParams) {

        Cl00101VO cl00101VO = new Cl00101VO();
        cl00101VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00101VO.setClassificationTypeUuid(requestParams.getString("classificationTypeUuid"));
        cl00101VO.setClassificationName(requestParams.getString("classificationName"));
        cl00101VO.setClassificationCode(requestParams.getString("classificationCode"));
        cl00101VO.setUseYn(requestParams.getString("useYn"));

        return filter(cl001Mapper.getClassificationSchemeList(cl00101VO), pageable, "", Cl00101VO.class);
    }

    public Cl00102VO getClassificationSchemeDetail(RequestParams<Cl00101VO> requestParams) {
        Cl00102VO cl00102VO = new Cl00102VO();
        cl00102VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        return  cl001Mapper.getClassificationSchemeDetail(cl00102VO);
    }

    public ApiResponse updateClassificationSchemeList(List<Cl00101VO> list){
        List<ClClassificationScheme> clClassificationSchemeList = ModelMapperUtils.mapList(list,ClClassificationScheme.class);
        ClClassificationScheme orgClClassificationScheme = null;
        ClClassificationSchemeCon clClassificationSchemeCon = null;
        String ctUuid = "";
        String detailCode = "";
        for (ClClassificationScheme clClassificationScheme : clClassificationSchemeList) {

            if(StringUtil.isNullOrEmpty(clClassificationScheme.getClassificationSchemeUuid())){ //Insert
                clClassificationScheme.setClassificationSchemeUuid(UUIDUtils.getUUID()); //UUID 생성
                detailCode = CommonCodeUtils.getDetailCode("CD112",clClassificationScheme.getClassificationTypeUuid());//해당분류타입의 분류코드
                ctUuid = getMaxClassificationCode(clClassificationScheme.getClassificationTypeUuid());
                if(StringUtils.isNotEmpty(ctUuid)){ //분류코드 조합
                    ctUuid = StringUtils.trim(ctUuid).substring(3,6);
                    ctUuid = String.valueOf(Integer.parseInt(ctUuid) + 1);

                    if(ctUuid.length() == 1 ){
                        ctUuid = "-00" + ctUuid;
                    }else if(ctUuid.length() == 2 ){
                        ctUuid = "-0" + ctUuid;
                    }
                    detailCode += ctUuid;
                }else{
                    detailCode += "-001";

                }
                clClassificationScheme.setClassificationCode(detailCode);
                clClassificationScheme.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Draft"));
            }

            if (clClassificationScheme.isCreated() || clClassificationScheme.isModified()) {
                if(clClassificationScheme.isModified()) {
                    orgClClassificationScheme = clClassificationSchemeRepository.findOne(clClassificationScheme.getId());
                    clClassificationScheme.setInsertDate(orgClClassificationScheme.getInsertDate());
                    clClassificationScheme.setInsertUuid(orgClClassificationScheme.getInsertUuid());
                }
                clClassificationSchemeRepository.save(clClassificationScheme);
            } else if (clClassificationScheme.isDeleted()) {
                if(getChildClass(clClassificationScheme.getClassificationSchemeUuid()) == 0 ){
                    clClassificationSchemeRepository.delete(clClassificationScheme);
                    clClassificationSchemeCon = new ClClassificationSchemeCon();
                    clClassificationSchemeCon.setClassificationSchemeUuid(clClassificationScheme.getClassificationSchemeUuid());
                    clClassificationSchemeConRepository.delete(clClassificationSchemeCon);
                }
                //연관 테이블 데이터 삭제 (상세)
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateStatus(List<Cl00101VO> list){
        List<ClClassificationScheme> clClassificationSchemeList = ModelMapperUtils.mapList(list,ClClassificationScheme.class);
        ClClassificationScheme orgClClassificationScheme = null;
        int index = 0;
        String changeStatus = "";
        for (ClClassificationScheme clClassificationScheme : clClassificationSchemeList) {
            changeStatus = list.get(index).getChangeStatus() == "" ?  "Draft" : list.get(index).getChangeStatus();
            orgClClassificationScheme = clClassificationSchemeRepository.findOne(clClassificationScheme.getId());
            clClassificationScheme.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111",changeStatus));
            clClassificationScheme.setInsertDate(orgClClassificationScheme.getInsertDate());
            clClassificationScheme.setInsertUuid(orgClClassificationScheme.getInsertUuid());
            clClassificationSchemeRepository.save(clClassificationScheme);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    public String getMaxClassificationCode(String classificationTypeUuid){
        Cl00101VO cl00101VO = new Cl00101VO();
        cl00101VO.setClassificationTypeUuid(classificationTypeUuid);

        return  cl001Mapper.getMaxClassificationCode(cl00101VO);
    }
    public int getChildClass(String classificationSchemeUuid){
        return  cl001Mapper.getChildClass(classificationSchemeUuid);
    }

    public void updateClassificationSchemeConDetail(RequestParams<Cl00102VO> requestParams) {
        ClClassificationSchemeCon clClassificationSchemeCon = new ClClassificationSchemeCon();

        if(StringUtils.isEmpty(requestParams.getString("classificationSchemeUuid"))){
            return;
        }

        clClassificationSchemeCon.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        clClassificationSchemeCon.setBasedOn(requestParams.getString("basedOn"));
        clClassificationSchemeCon.setManager(requestParams.getString("manager"));
        clClassificationSchemeCon.setManagerOrganization(requestParams.getString("managerOrganization"));

        ClClassificationSchemeCon orgClClassCon = null;

        orgClClassCon = clClassificationSchemeConRepository.findOne(clClassificationSchemeCon.getId());

        if(orgClClassCon != null){
            clClassificationSchemeCon.setInsertDate(orgClClassCon.getInsertDate());
            clClassificationSchemeCon.setInsertUuid(orgClClassCon.getInsertUuid());
        }
        clClassificationSchemeConRepository.save(clClassificationSchemeCon);
    }

    /*public ClClassificationScheme findOneClClassificationScheme(Cl00101VO requestParam) {
        QClClassificationScheme qClClassificationScheme = QClClassificationScheme.clClassificationScheme;

        Predicate predicate = qClClassificationScheme.classificationSchemeUuid.eq(requestParam.getClassificationSchemeUuid());

        return clClassificationSchemeRepository.findOne(predicate);
    }*/
}
