package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad000.domain.Ad000;
import rmsoft.ams.seoul.ad.ad004.dao.AD004DAO;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_D;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_H;

import java.util.List;

@Service
public class AD004_HService extends BaseService<Ad004_H, Ad004_H.Ad004_HId> {

    @Autowired
    private AD004DAO dao;

    public List<Ad004_H> searchPopupHeader(Ad004_H param) {
        return dao.searchPopupHeader(param);
    }
}
