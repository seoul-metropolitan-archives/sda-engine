package rmsoft.ams.seoul.st.st025.service;

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
import rmsoft.ams.seoul.common.domain.StReaderMachine;
import rmsoft.ams.seoul.common.repository.StReaderMachineRepository;
import rmsoft.ams.seoul.st.st025.dao.St025Mapper;
import rmsoft.ams.seoul.st.st025.vo.St02501VO;

import javax.inject.Inject;
import java.util.List;

@Service
public class St025Service extends BaseService {

    @Inject
    private St025Mapper st025Mapper;

    @Autowired
    private StReaderMachineRepository stReaderMachineRepository;


    public Page<St02501VO> getReaderMachine(Pageable pageable, RequestParams<St02501VO> requestParams) {

        St02501VO st02501VO = new St02501VO();
        st02501VO.setMachineId(requestParams.getString("machineId"));
        st02501VO.setMachineName(requestParams.getString("machineName"));
        st02501VO.setGateUuid(requestParams.getString("gateUuid"));
        st02501VO.setIp(requestParams.getString("ip"));

        return filter(st025Mapper.getReaderMachine(st02501VO), pageable, "", St02501VO.class);
    }

    @Transactional
    public ApiResponse saveReaderMachine(List<St02501VO> list) {
        List<StReaderMachine> stReaderMachineList = ModelMapperUtils.mapList(list, StReaderMachine.class);
        StReaderMachine orgStReaderMachine = null;

        for(StReaderMachine stReaderMachine : stReaderMachineList){
            if(stReaderMachine.isDeleted()){
                stReaderMachineRepository.delete(stReaderMachine);
            }else{
                if(stReaderMachine.isCreated()){
                    /*int no = jdbcTemplate.queryForObject("SELECT ST_READER_MACHINE_SEQ.NEXTVAL FROM dual", int.class);
                    stReaderMachine.setNo(no);*/
                }else if(stReaderMachine.isModified()){
                    orgStReaderMachine = stReaderMachineRepository.findOne(stReaderMachine.getId());
                    stReaderMachine.setInsertDate(orgStReaderMachine.getInsertDate());
                    stReaderMachine.setInsertUuid(orgStReaderMachine.getInsertUuid());
                }

                stReaderMachineRepository.save(stReaderMachine);
            }
        }



        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
