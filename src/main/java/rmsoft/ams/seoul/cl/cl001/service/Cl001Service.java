package rmsoft.ams.seoul.cl.cl001.service;

import com.querydsl.core.types.Predicate;
import io.netty.util.internal.StringUtil;
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
import rmsoft.ams.seoul.cl.cl001.dao.Cl001Mapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00102VO;
import rmsoft.ams.seoul.common.domain.ClClassificationScheme;
import rmsoft.ams.seoul.common.domain.ClClassificationSchemeCon;
import rmsoft.ams.seoul.common.domain.QClClassificationSchemeCon;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeConRepository;
import rmsoft.ams.seoul.common.repository.ClClassificationSchemeRepository;

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
        return filter(cl001Mapper.getClassificationSchemeList(cl00101VO), pageable, "", Cl00101VO.class);
    }

    public Cl00102VO getClassificationSchemeDetail(RequestParams<Cl00101VO> requestParams) {

        QClClassificationSchemeCon qClClassificationSchemeCon = QClClassificationSchemeCon.clClassificationSchemeCon;
        Predicate predicate = qClClassificationSchemeCon.classificationSchemeUuid.eq(requestParams.getString("classificationSchemeUuid"));
        ClClassificationSchemeCon result = clClassificationSchemeConRepository.findOne(predicate);
        Cl00102VO cl00102VO = new Cl00102VO();

        if(result != null){
            cl00102VO =  ModelMapperUtils.map(result, Cl00102VO.class);
        }

        return cl00102VO;
    }

    public ApiResponse updateClassificationSchemeList(List<Cl00101VO> list){
        for (Cl00101VO cl00101VO : list) {
            ClClassificationScheme clClassificationScheme = ModelMapperUtils.map(cl00101VO,ClClassificationScheme.class);
            if(StringUtil.isNullOrEmpty(clClassificationScheme.getClassificationSchemeUuid())){
                clClassificationScheme.setClassificationSchemeUuid(UUIDUtils.getUUID());

            }

            if (clClassificationScheme.isCreated() || clClassificationScheme.isModified()) {
                clClassificationSchemeRepository.save(clClassificationScheme);
            } else if (clClassificationScheme.isDeleted()) {
                clClassificationSchemeRepository.delete(clClassificationScheme);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }



}
