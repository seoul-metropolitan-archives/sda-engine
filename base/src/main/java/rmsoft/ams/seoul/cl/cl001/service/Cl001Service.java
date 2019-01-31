package rmsoft.ams.seoul.cl.cl001.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad007.dao.Ad007Mapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00702VO;
import rmsoft.ams.seoul.cl.cl001.dao.Cl001Mapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00102VO;
import rmsoft.ams.seoul.common.domain.ClClassificationScheme;
import rmsoft.ams.seoul.common.domain.ClClassificationSchemeCon;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeConRepository;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Cl 001 service.
 */
@Service
public class Cl001Service extends BaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Inject
    private Cl001Mapper cl001Mapper;

    @Autowired
    private ClClassificationSchemeConRepository clClassificationSchemeConRepository;

    @Autowired
    private ClClassificationSchemeRepository clClassificationSchemeRepository;

    /**
     * Gets classification scheme list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classification scheme list
     */
//Inquiry
    public Page<Cl00101VO> getClassificationSchemeList(Pageable pageable, RequestParams<Cl00101VO> requestParams) {

        Cl00101VO cl00101VO = new Cl00101VO();
        cl00101VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00101VO.setClassificationTypeUuid(requestParams.getString("classificationTypeUuid"));
        cl00101VO.setClassificationName(requestParams.getString("classificationName"));
        cl00101VO.setClassificationCode(requestParams.getString("classificationCode"));
        cl00101VO.setUseYn(requestParams.getString("useYn"));

        return filter(cl001Mapper.getClassificationSchemeList(cl00101VO), pageable, "", Cl00101VO.class);
    }

    /**
     * Gets classification scheme detail.
     *
     * @param requestParams the request params
     * @return the classification scheme detail
     */
//Classification Scheme 상세 정보
    public Cl00102VO getClassificationSchemeDetail(RequestParams<Cl00101VO> requestParams) {
        Cl00102VO cl00102VO = new Cl00102VO();
        cl00102VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));

        cl00102VO = cl001Mapper.getClassificationSchemeDetail(cl00102VO);

        Ad00702VO ad00702VO = new Ad00702VO();

        if(requestParams.getString("classificationSchemeUuid") != null) {
            ad00702VO.setAddMetaTemplateSetUuid(requestParams.getString("addMetaTemplateSetUuid"));
        }else{
            ad00702VO.setAddMetaTemplateSetUuid(cl00102VO.getAddMetaTemplateSetUuid());
        }

        return cl00102VO;
    }
     /**
     * Update classification scheme list api response.
     *
     * @param list the list
     * @return the api response
     */
//저장(수정,삭제,생성)
    public ApiResponse updateClassificationSchemeList(List<Cl00101VO> list){
        List<ClClassificationScheme> clClassificationSchemeList = ModelMapperUtils.mapList(list,ClClassificationScheme.class);
        ClClassificationScheme orgClClassificationScheme = null;
        ClClassificationSchemeCon clClassificationSchemeCon = null;
        String ctUuid = "";
        String detailCode = "";
        for (ClClassificationScheme clClassificationScheme : clClassificationSchemeList) {

            if(clClassificationScheme.isCreated()){ //ClassificationSchemeUuid가 없을때
//                clClassificationScheme.setClassificationSchemeUuid(UUIDUtils.getUUID()); //UUID 생성
                detailCode = CommonCodeUtils.getDetailCode("CD112",clClassificationScheme.getClassificationTypeUuid());//해당분류타입의 분류코드
//                ctUuid = jdbcTemplate.queryForObject("select FC_CL_CLS_SCHEME_CODE('" + detailCode + "') from dual", String.class);
//                clClassificationScheme.setClassificationCode(ctUuid);
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
                    clClassificationSchemeCon = new ClClassificationSchemeCon();
                    clClassificationSchemeCon.setClassificationSchemeUuid(clClassificationScheme.getClassificationSchemeUuid());
                    clClassificationSchemeConRepository.delete(clClassificationSchemeCon);
                    clClassificationSchemeRepository.delete(clClassificationScheme);
                }else{
                    throw new ApiException("CL", "001_01");
                }
                //연관 테이블 데이터 삭제 (상세)
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update status api response.
     *
     * @param list the list
     * @return the api response
     */
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

    /**
     * Get max classification code string.
     *
     * @param classificationTypeUuid the classification type uuid
     * @return the string
     */
    public String getMaxClassificationCode(String classificationTypeUuid){
        Cl00101VO cl00101VO = new Cl00101VO();
        cl00101VO.setClassificationTypeUuid(classificationTypeUuid);

        return  cl001Mapper.getMaxClassificationCode(cl00101VO);
    }

    /**
     * Get child class int.
     *
     * @param classificationSchemeUuid the classification scheme uuid
     * @return the int
     */
    public int getChildClass(String classificationSchemeUuid){
        return  cl001Mapper.getChildClass(classificationSchemeUuid);
    }

    /**
     * Update classification scheme con detail.
     *
     * @param requestParams the request params
     */
    public void updateClassificationSchemeConDetail(Cl00102VO requestParams) {
        //ClClassificationSchemeCon clClassificationSchemeCon = new ClClassificationSchemeCon();
        ClClassificationSchemeCon clClassificationSchemeCon = ModelMapperUtils.map(requestParams,ClClassificationSchemeCon.class);

        if(StringUtils.isEmpty(requestParams.getClassificationSchemeUuid())){
            return;
        }

        ClClassificationSchemeCon orgClClassCon = null;

        orgClClassCon = clClassificationSchemeConRepository.findOne(clClassificationSchemeCon.getId());

        if(orgClClassCon != null){
            clClassificationSchemeCon.setInsertDate(orgClClassCon.getInsertDate());
            clClassificationSchemeCon.setInsertUuid(orgClClassCon.getInsertUuid());
        }
        clClassificationSchemeConRepository.save(clClassificationSchemeCon);
    }
}
