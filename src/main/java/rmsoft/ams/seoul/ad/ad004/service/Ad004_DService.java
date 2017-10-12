package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad004.dao.AD004DAO;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_D;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_H;

import java.util.List;

@Service
public class AD004_DService extends BaseService<Ad004_D, Ad004_D.Ad004_DId> {

    @Autowired
    private AD004DAO dao;

    public List<Ad004_D> searchPopupDetail(Ad004_D param) {
        return dao.searchPopupDetail(param);
    }
}
