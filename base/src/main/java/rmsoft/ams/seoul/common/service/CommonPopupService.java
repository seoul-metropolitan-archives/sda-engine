package rmsoft.ams.seoul.common.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.dao.CommonPopupDAO;
import rmsoft.ams.seoul.common.dao.CommonPopupMapper;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Common popup service.
 */
@Service
public class CommonPopupService extends BaseService {
    @Autowired
    private CommonPopupMapper commonPopupMapper;

    @Autowired
    private CommonPopupDAO commonPopupDAO;

    /**
     * Gets popup info.
     *
     * @param adPopupHeader the ad popup header
     * @return the popup info
     */
    public Map<String,Object> getPopupInfo(AdPopupHeader adPopupHeader)
    {
        Map<String,Object> popupInfo = new HashMap<String,Object>();
        popupInfo.put("popupInfo",commonPopupMapper.popupInfo(adPopupHeader));
        popupInfo.put("columnInfo",commonPopupMapper.getColumnInfo(adPopupHeader));
        return popupInfo;
    }

    /**
     * Search list.
     *
     * @param param the param
     * @return the list
     */
    public List<Map<String,Object>> search(Map<String,Object> param)
    {
        String sql = "";
        sql = commonPopupMapper.getSQL(param);
        return commonPopupDAO.search(sql, param);
    }

}
