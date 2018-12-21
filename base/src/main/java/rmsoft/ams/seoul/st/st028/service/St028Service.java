package rmsoft.ams.seoul.st.st028.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StGate;
import rmsoft.ams.seoul.common.repository.StGateRepository;
import rmsoft.ams.seoul.st.st028.dao.St028Mapper;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;

import javax.inject.Inject;
import java.util.List;

@Service
public class St028Service extends BaseService {

    @Inject
    private St028Mapper st028Mapper;

    @Autowired
    private StGateRepository stGateRepository;

    public Page<St02801VO> getStGate(Pageable pageable, RequestParams<St02801VO> requestParams) {
        St02801VO st02801VO = new St02801VO();
        //검색조건 추가시

        return filter(st028Mapper.getStGate(st02801VO), pageable, "", St02801VO.class);
    }

    @Transactional
    public ApiResponse saveGate(List<St02801VO> list) {
        List<StGate> stGateList = ModelMapperUtils.mapList(list, StGate.class);
        StGate orgStGate = null;
        for(StGate stGate : stGateList){
            if(stGate.isDeleted()){
                stGateRepository.delete(stGate);
            }else{
                if(stGate.isCreated()){
                    int no = jdbcTemplate.queryForObject("SELECT ST_GATE_SEQ.NEXTVAL FROM dual", int.class);
                    stGate.setNo(no);
                }else if(stGate.isModified()){
                    orgStGate = stGateRepository.findOne(stGate.getId());
                    stGate.setInsertDate(stGate.getInsertDate());
                    stGate.setInsertUuid(stGate.getInsertUuid());

                }
                stGateRepository.save(stGate);
            }
        }


        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
