package rmsoft.ams.seoul.st.st010.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st010.dao.St010Mapper;

import javax.inject.Inject;

@Service
public class St010Service extends BaseService {

    @Inject
    private St010Mapper st010Mapper;



}
