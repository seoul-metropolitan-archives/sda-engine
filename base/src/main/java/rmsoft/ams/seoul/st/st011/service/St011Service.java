package rmsoft.ams.seoul.st.st011.service;

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
import rmsoft.ams.seoul.common.domain.StArrangeContainersResult;
import rmsoft.ams.seoul.common.domain.StExceptRecordResult;
import rmsoft.ams.seoul.common.domain.StInoutExcept;
import rmsoft.ams.seoul.common.repository.StArrangeContainersResultRepository;
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st011.dao.St011Mapper;
import rmsoft.ams.seoul.st.st011.vo.St01101VO;
import rmsoft.ams.seoul.st.st011.vo.St01102VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St011Service extends BaseService {

    @Inject
    private St011Mapper st011Mapper;

    @Autowired
    private StInoutExceptRepository stInoutExceptRepository;

    @Autowired
    private StExceptRecordResultRepository stExceptRecordResultRepository;


    public Page<St01101VO> getStInoutExcept(Pageable pageable, RequestParams<St01101VO> requestParams) {
        St01101VO st01101VO = new St01101VO();
        st01101VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st011Mapper.getStInoutExcept(st01101VO), pageable, "", St01101VO.class);
    }
    @Transactional
    public ApiResponse saveInoutExcept(List<St01101VO> list) {
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

    public void saveExceptRecordResult(List<St01102VO> list) {
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

    public Page<St01102VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        St01102VO st01102VO = new St01102VO();
        st01102VO.setInoutExceptUuid(requestParams.getString("inoutExceptUuid"));
        return filter(st011Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }
}