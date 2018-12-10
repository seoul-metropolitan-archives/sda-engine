package rmsoft.ams.seoul.st.st009.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.repository.StTakeoutRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StTakeoutRequestRepository;
import rmsoft.ams.seoul.st.st009.dao.St009Mapper;
import rmsoft.ams.seoul.st.st009.vo.St00901VO;

import javax.inject.Inject;

@Service
public class St009Service extends BaseService {


    @Inject
    private St009Mapper st009Mapper;

    @Autowired
    private StTakeoutRequestRepository stTakeoutRequestRepository;

    @Autowired
    private StTakeoutRecordResultRepository stTakeoutRecordResultRepository;


    public Page<St00901VO> getTakeoutRequest(Pageable pageable, RequestParams<St00901VO> requestParams) {

        St00901VO st00901VO = new St00901VO();


        return filter(st009Mapper.getTakeoutRequest(st00901VO), pageable, "", St00901VO.class);
    }
}
