package rmsoft.ams.seoul.st.st011.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st011.dao.St011Mapper;

import javax.inject.Inject;

@Service
public class St011Service extends BaseService {

    @Inject
    private St011Mapper st011Mapper;
}
