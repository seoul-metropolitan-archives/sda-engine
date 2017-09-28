package rmsoft.ams.seoul.ac.ac003.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac003.dao.Ac003Repository;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003VO;
import rmsoft.ams.seoul.ac.ac003.domain.QAc003;

import javax.inject.Inject;

@Service
public class Ac003Service extends BaseService<Ac003, Ac003.Ac003Id> {

    @Inject
    public Ac003Service(Ac003Repository ac003Repository) {
        super(ac003Repository);
    }

    public Page<Ac003> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ac003.class);
    }

    public Ac003VO findOne(RequestParams<Ac003VO> requestParams) {
        QAc003 qAc003 = QAc003.ac003;

        Predicate predicate = qAc003.userId.eq(requestParams.getString("userId"));

        return buildVO(findOne(predicate));
    }

    private Ac003VO buildVO(Ac003 ac003) {

        if (ac003 == null) {
            return new Ac003VO();
        } else {
            BoundMapperFacade<Ac003, Ac003VO> mapper =
                    ModelMapperUtils.getMapper("Ac003", this.getClass().getPackage().getName());
            return mapper.map(ac003);
        }
    }
}