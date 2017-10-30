package rmsoft.ams.seoul.common.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.dao.CommonPopupMapper;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.vo.BaseColumnVO;

import java.util.List;

@Service
public class CommonPopupService extends BaseService {
    @Autowired
    private CommonPopupMapper commonPopupMapper;

    public List<BaseColumnVO> getColumnInfo(AdPopupHeader adPopupHeader)
    {
        return commonPopupMapper.getColumnInfo(adPopupHeader);
    }


}
