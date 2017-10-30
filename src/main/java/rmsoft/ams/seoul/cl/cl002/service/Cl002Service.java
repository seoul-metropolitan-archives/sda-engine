package rmsoft.ams.seoul.cl.cl002.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl002.dao.Cl002Mapper;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;

import javax.inject.Inject;

@Service
public class Cl002Service extends BaseService {

    @Inject
    private Cl002Mapper cl002Mapper;

    public Cl00101VO getClassificationScheme(){

        return cl002Mapper.getClassificationScheme();
    }

    public Page<Cl00201VO> getClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(requestParams.getString("classificationSchemeUuid"));
        cl00201VO.setParentClassUuid(requestParams.getString("parentClassUuid"));
        cl00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        cl00201VO.setClassCode(requestParams.getString("classCode"));
        cl00201VO.setClassName(requestParams.getString("className"));
        cl00201VO.setClassLevelUuid(requestParams.getString("classLevelUuid"));
        cl00201VO.setUseYn(requestParams.getString("useYn"));

        return filter(cl002Mapper.getClassList(cl00201VO), pageable, "", Cl00201VO.class);
    }

    public Page<Cl00201VO> getClassHierarchyList(Pageable pageable, String classificationSchemeUuid) {

        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassificationSchemeUuid(classificationSchemeUuid);

        return filter(cl002Mapper.getClassHierarchyList(cl00201VO), pageable, "", Cl00201VO.class);
    }
}
