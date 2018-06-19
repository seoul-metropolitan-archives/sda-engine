package rmsoft.ams.seoul.ad.ad008.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad008.dao.Ad008Mapper;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008VO;

import java.util.List;

@Service
public class Ad008Service extends BaseService<Ad008, String> {
    @Autowired
    private Ad008Mapper mapper;


    public Page<Ad008> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ad008.class);
    }

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad008VO> searchAuditList(RequestParams<Ad008VO> param) {
        Ad008VO ad008VO = new Ad008VO();

        ad008VO.setDescription(param.getString("description"));

        return mapper.searchAuditList(ad008VO);
    }
}