package rmsoft.ams.seoul.cl.cl003.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl001.dao.Cl001Mapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl003.dao.Cl003Mapper;
import rmsoft.ams.seoul.cl.cl003.vo.Cl003;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;

import javax.inject.Inject;
import java.util.List;

@Service
public class Cl003Service extends BaseService {

    @Inject
    private Cl003Mapper cl003Mapper;

    public Page<Cl00301VO> getClassifiedRecordList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {

        Cl00301VO cl00301VO = new Cl00301VO();

        return filter(cl003Mapper.getClassificationSchemeList(cl00301VO), pageable, "", Cl00301VO.class);
    }
}
