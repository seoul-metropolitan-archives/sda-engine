package rmsoft.ams.seoul.st.st026.service;

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

import rmsoft.ams.seoul.common.domain.StRfidMachine;
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;

import rmsoft.ams.seoul.common.repository.StRfidMachineRepository;
import rmsoft.ams.seoul.st.st026.dao.St026Mapper;
import rmsoft.ams.seoul.st.st026.vo.St026VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St026Service extends BaseService {
    @Inject
    private St026Mapper st026Mapper;

    @Autowired
    private StRfidMachineRepository stRfidMachineRepository;

    @Autowired
    private StExceptRecordResultRepository stExceptRecordResultRepository;


    public Page<St026VO> getStRfidMachine(Pageable pageable, RequestParams<St026VO> requestParams) {
        St026VO St026VO = new St026VO();
        St026VO.setMachineTypeUuid(requestParams.getString("machineTypeUuid"));
        St026VO.setMachineId(requestParams.getString("machineId"));
        St026VO.setMachineName(requestParams.getString("machineName"));
        St026VO.setIp(requestParams.getString("ip"));
        //검색조건 추가시

        return filter(st026Mapper.getStRfidMachine(St026VO), pageable, "", St026VO.class);
    }
    @Transactional
    public ApiResponse saveStRfidMachine(List<St026VO> list) {
        List<StRfidMachine> stInoutExceptList = ModelMapperUtils.mapList(list, StRfidMachine.class);
        StRfidMachine orgStRfidMachine = null;
        int cnt = 0;

        for(StRfidMachine stRfidMachine : stInoutExceptList){
            if(stRfidMachine.isDeleted()){
                stRfidMachineRepository.delete(stRfidMachine);

                //여기서 st_except_record_result도 삭제 해야된다.
            }else{
                if(stRfidMachine.isCreated()){
                    /*int no = jdbcTemplate.queryForObject("SELECT ST_READER_MACHINE_SEQ.NEXTVAL FROM dual", int.class);
                    stRfidMachine.setNo(no);*/
                }else if(stRfidMachine.isModified()){
                    orgStRfidMachine = stRfidMachineRepository.findOne(stRfidMachine.getId());
                    stRfidMachine.setInsertDate(orgStRfidMachine.getInsertDate());
                    stRfidMachine.setInsertUuid(orgStRfidMachine.getInsertUuid());
                }

                stRfidMachineRepository.save(stRfidMachine);
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
        return filter(st026Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
