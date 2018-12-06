package rmsoft.ams.seoul.st.st013.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StExceptRecordResult;
import rmsoft.ams.seoul.common.domain.StInoutExcept;
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;
import rmsoft.ams.seoul.st.st013.dao.St013Mapper;
import rmsoft.ams.seoul.st.st013.vo.St01301VO;
import rmsoft.ams.seoul.st.st013.vo.St01302VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St013Service extends BaseService {

    @Inject
    private St013Mapper st013Mapper;

    @Autowired
    private StInoutExceptRepository stInoutExceptRepository;

    @Autowired
    private StExceptRecordResultRepository stExceptRecordResultRepository;


    public Page<St01301VO> getStInoutExcept(Pageable pageable, RequestParams<St01301VO> requestParams) {
        St01301VO st01301VO = new St01301VO();
        st01301VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st013Mapper.getStInoutExcept(st01301VO), pageable, "", St01301VO.class);
    }
    @Transactional
    public ApiResponse saveInoutExcept(List<St01301VO> list) {
        List<StInoutExcept> stInoutExceptList = ModelMapperUtils.mapList(list, StInoutExcept.class);
        StInoutExcept orgStInoutExcept = null;
        int cnt = 0;

        for(StInoutExcept stInoutExcept : stInoutExceptList){
            if(stInoutExcept.isDeleted()){
                stInoutExceptRepository.delete(stInoutExcept);

                //여기서 st_except_record_result도 삭제 해야된다.
            }else{
                if(stInoutExcept.isCreated()){
                    stInoutExcept.setRequestorUuid(SessionUtils.getCurrentLoginUserUuid());
                    stInoutExcept.setRequestDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                }else if(stInoutExcept.isModified()){
                    orgStInoutExcept = stInoutExceptRepository.findOne(stInoutExcept.getId());
                    stInoutExcept.setRequestorUuid(SessionUtils.getCurrentLoginUserUuid());
                    stInoutExcept.setInsertDate(orgStInoutExcept.getInsertDate());
                    stInoutExcept.setInsertUuid(orgStInoutExcept.getInsertUuid());
                }

                stInoutExceptRepository.save(stInoutExcept);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public void saveExceptRecordResult(List<St01302VO> list) {
        List<StExceptRecordResult> stExceptRecordResults = ModelMapperUtils.mapList(list, StExceptRecordResult.class);


        for(StExceptRecordResult stExceptRecordResult : stExceptRecordResults){
            if(stExceptRecordResult.isDeleted()){
                stExceptRecordResultRepository.delete(stExceptRecordResult);
            }else{
                stExceptRecordResult.setExceptRecordResultUuid(UUIDUtils.getUUID());
                stExceptRecordResultRepository.save(stExceptRecordResult);
            }
        }


    }

    public Page<St01302VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01302VO> requestParams) {
        St01302VO st01302VO = new St01302VO();
        st01302VO.setInoutExceptUuid(requestParams.getString("inoutExceptUuid"));
        return filter(st013Mapper.getStExceptRecordResult(st01302VO), pageable, "", St01302VO.class);

    }
}