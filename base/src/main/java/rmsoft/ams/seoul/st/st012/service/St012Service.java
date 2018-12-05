package rmsoft.ams.seoul.st.st012.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StInoutExcept;
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;

import rmsoft.ams.seoul.st.st012.dao.St012Mapper;
import rmsoft.ams.seoul.st.st012.vo.St01201VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St012Service extends BaseService {
    @Inject
    private St012Mapper st012Mapper;

    @Autowired
    private StInoutExceptRepository stInoutExceptRepository;

    @Autowired
    private StExceptRecordResultRepository stExceptRecordResultRepository;


    public Page<St01201VO> getStInoutExcept(Pageable pageable, RequestParams<St01201VO> requestParams) {
        St01201VO St01201VO = new St01201VO();
        //St01201VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st012Mapper.getStInoutExcept(St01201VO), pageable, "", St01201VO.class);
    }
    @Transactional
    public ApiResponse saveInoutExcept(List<St01201VO> list) {
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

    /*public void saveExceptRecordResult(List<St01102VO> list) {
        List<StExceptRecordResult> stExceptRecordResults = ModelMapperUtils.mapList(list, StExceptRecordResult.class);

        for(StExceptRecordResult stExceptRecordResult : stExceptRecordResults){
            stExceptRecordResult.setExceptRecordResultUuid(UUIDUtils.getUUID());
            stExceptRecordResultRepository.save(stExceptRecordResult);
        }


    }

    public Page<St01102VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        St01102VO st01102VO = new St01102VO();
        st01102VO.setInoutExceptUuid(requestParams.getString("inoutExceptUuid"));
        return filter(st012Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
