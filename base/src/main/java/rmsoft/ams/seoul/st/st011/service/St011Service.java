package rmsoft.ams.seoul.st.st011.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StArrangeContainersResult;
import rmsoft.ams.seoul.common.repository.StArrangeContainersResultRepository;
import rmsoft.ams.seoul.st.st011.dao.St011Mapper;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St011Service extends BaseService {
    @Inject
    private St011Mapper st011Mapper;


    //@Autowired
    //private StArrangeContainersResultRepository stArrangeContainersResultRepository;


}