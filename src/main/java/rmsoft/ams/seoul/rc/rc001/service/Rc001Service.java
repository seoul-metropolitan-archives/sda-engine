package rmsoft.ams.seoul.rc.rc001.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.rc.rc001.dao.Rc001Mapper;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;

import java.util.ArrayList;
import java.util.List;

@Service("Rc001Service")
public class Rc001Service extends BaseService
{
    @Autowired
    private Rc001Mapper rc001Mapper;

    public List<Rc00101VO> getAllNode(Rc00101VO param)
    {
        List<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(rc001Mapper.getAggregationNode(param));

        if(null != param.getUuid() && !param.getUuid().equals(""))
            nodes.addAll(rc001Mapper.getItemNode(param));

        return nodes;
    }

}
