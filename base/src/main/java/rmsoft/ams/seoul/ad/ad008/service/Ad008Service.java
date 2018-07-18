package rmsoft.ams.seoul.ad.ad008.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad008.dao.Ad008Mapper;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008;
import rmsoft.ams.seoul.ad.ad008.vo.Ad00801VO;

import java.util.List;

@Service
public class Ad008Service extends BaseService<Ad008, String> {
    @Autowired
    private Ad008Mapper mapper;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00801VO> searchList(RequestParams<Ad00801VO> param) {
        Ad00801VO ad008VO = new Ad00801VO();

        ad008VO.setEntityType(param.getString("entityType"));
        ad008VO.setPrimaryKey(param.getString("primaryKey"));
        ad008VO.setProgramName(param.getString("programName"));
        ad008VO.setFunction(param.getString("function"));
        ad008VO.setModifyDateFrom(param.getString("modifyDateFrom"));
        ad008VO.setModifyDateTo(param.getString("modifyDateTo"));

        return mapper.searchList(ad008VO);
    }
}