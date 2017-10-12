package rmsoft.ams.seoul.ad.ad003.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad003.dao.Ad003Mapper;
import rmsoft.ams.seoul.ad.ad003.vo.Ad003VO_03;
import rmsoft.ams.seoul.common.repository.AdCodeDetailRepository;
import rmsoft.ams.seoul.common.repository.AdCodeHeaderRepository;

import java.util.List;

@Service
public class Ad003Service extends BaseService {

    @Autowired
    private Ad003Mapper mapper;

    @Autowired
    private AdCodeHeaderRepository codeHeaderRepository;

    @Autowired
    private AdCodeDetailRepository detailRepository;

    public List<Ad003VO_03> getCode(Ad003VO_03 param) {
        return mapper.getCode(param);
    }
}
