package rmsoft.ams.seoul.ad.ad004.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_D;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_H;

import java.util.List;

@Repository
public class AD004DAO {

    @Autowired
    private AD004Mapper mapper;

    public List<Ad004_H> searchPopupHeader(Ad004_H param)
    {
        return mapper.searchPopupHeader(param);
    }
    public List<Ad004_D> searchPopupDetail(Ad004_D param)
    {
        return mapper.searchPopupDetail(param);
    }
}
