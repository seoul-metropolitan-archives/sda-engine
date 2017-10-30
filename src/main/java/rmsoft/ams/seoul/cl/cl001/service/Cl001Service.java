package rmsoft.ams.seoul.cl.cl001.service;

import io.netty.util.internal.StringUtil;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl001.dao.Cl001Mapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00102VO;
import rmsoft.ams.seoul.common.domain.ClClassificationScheme;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeConRepository;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
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
        for (ClClassificationScheme clClassificationScheme : clClassificationSchemeList) {

            if(StringUtil.isNullOrEmpty(clClassificationScheme.getClassificationSchemeUuid())){
                clClassificationScheme.setClassificationSchemeUuid(UUIDUtils.getUUID());
            }

            if (clClassificationScheme.isCreated() || clClassificationScheme.isModified()) {
                clClassificationScheme.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Draft"));

                if(clClassificationScheme.isModified()) {
                    orgClClassificationScheme = clClassificationSchemeRepository.findOne(clClassificationScheme.getId());
                    clClassificationScheme.setInsertDate(orgClClassificationScheme.getInsertDate());
                    clClassificationScheme.setInsertUuid(orgClClassificationScheme.getInsertUuid());
                }
                clClassificationSchemeRepository.save(clClassificationScheme);
            } else if (clClassificationScheme.isDeleted()) {
                clClassificationSchemeRepository.delete(clClassificationScheme);
                //연관 테이블 데이터 삭제 (상세)



            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /*public ClClassificationScheme findOneClClassificationScheme(Cl00101VO requestParam) {
        QClClassificationScheme qClClassificationScheme = QClClassificationScheme.clClassificationScheme;

        Predicate predicate = qClClassificationScheme.classificationSchemeUuid.eq(requestParam.getClassificationSchemeUuid());

        return clClassificationSchemeRepository.findOne(predicate);
    }*/
}
