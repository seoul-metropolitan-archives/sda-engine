package rmsoft.ams.seoul.ig.ig003.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00201VO;
import rmsoft.ams.seoul.ig.ig003.dao.Ig003Mapper;

import javax.inject.Inject;
import org.springframework.data.domain.Pageable;

@Service
public class Ig003Service extends BaseService {
    @Inject
    private Ig003Mapper ig003Mapper;

    public Page<Ig00201VO> getIgAccessionRecordList(Pageable pageable, RequestParams<Ig00201VO> requestParams) {
        Ig00201VO ig00201VO = new Ig00201VO();
        return filter(ig003Mapper.getIgAccessionRecordList(ig00201VO), pageable, "", Ig00201VO.class);
    }
}