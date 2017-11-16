package rmsoft.ams.seoul.rc.rc004.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.rc.rc004.dao.Rc004Mapper;
import rmsoft.ams.seoul.rc.rc005.dao.Rc005Mapper;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import javax.inject.Inject;
import java.util.List;

@Service
public class Rc004Service extends BaseService{
    @Inject
    private Rc004Mapper rc004Mapper;

    public Page<Rc00501VO> getRecordItemList(Pageable pageable, RequestParams<Rc00501VO> requestParams){
        return null;
    }
}
