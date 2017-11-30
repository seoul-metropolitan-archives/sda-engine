package rmsoft.ams.seoul.rc.rc002.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.repository.RcLevelRepository;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00201VO;

import java.util.List;

@Service("Rc002Service")
public class Rc002Service extends BaseService
{
    @Autowired
    private RcLevelRepository rcLevelRepository;
    public List<Rc00201VO> getLevel(){
        return ModelMapperUtils.mapList(rcLevelRepository.findAll(),Rc00201VO.class);
    }
}
