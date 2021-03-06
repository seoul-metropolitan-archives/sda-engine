package rmsoft.ams.seoul.st.st029.service;

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
import rmsoft.ams.seoul.common.domain.StProgram;
import rmsoft.ams.seoul.common.repository.StGateRepository;
import rmsoft.ams.seoul.common.repository.StProgramRepository;
import rmsoft.ams.seoul.st.st028.dao.St028Mapper;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;
import rmsoft.ams.seoul.st.st029.dao.St029Mapper;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;

import javax.inject.Inject;
import java.util.List;

@Service
public class St029Service extends BaseService {

    @Inject
    private St029Mapper st029Mapper;

    @Autowired
    private StProgramRepository stProgramRepository;

    public Page<St02901VO> getStProgram(Pageable pageable, RequestParams<St02901VO> requestParams) {
        St02901VO st02901VO = new St02901VO();
        for(String key : requestParams.getMap().keySet()){
            System.out.println(key+":"+requestParams.getMap().get(key));
        }
        //st02901VO.setGateId(requestParams.getString("gateId"));
        st02901VO.setMachineTypeUuid(requestParams.getString("machineTypeUuid"));
        st02901VO.setProgramName(requestParams.getString("programName"));
        st02901VO.setRegisterUuid(requestParams.getString("registerUuid"));
        st02901VO.setRegisterName(requestParams.getString("registerName"));
        st02901VO.setStartDate(requestParams.getString("startDate"));
        st02901VO.setEndDate(requestParams.getString("endDate"));
        return filter(st029Mapper.getStProgram(st02901VO), pageable, "", St02901VO.class);
    }

    @Transactional
    public ApiResponse saveProgram(List<St02901VO> list) {
        System.out.println("list.size():"+list.size());
        List<StProgram> stProgramList = ModelMapperUtils.mapList(list, StProgram.class);
        System.out.println("stProgramList.size():"+stProgramList.size());
        StProgram orgStProgram = null;
        for(StProgram stProgram : stProgramList)
        {
            System.out.println("stProgram.getProgramUuid():"+stProgram.getProgramUuid());
            if(stProgram.isDeleted())
            {
                stProgramRepository.delete(stProgram);
            }
            else
            {
                if(stProgram.isCreated()){
                    /*int no = jdbcTemplate.queryForObject("SELECT ST_PROGRAM_SEQ.NEXTVAL FROM dual", int.class);
                    stProgram.setNo(no);*/
                }else if(stProgram.isModified()){
                    orgStProgram = stProgramRepository.findOne(stProgram.getId());
                    stProgram.setInsertDate(orgStProgram.getInsertDate());
                    stProgram.setInsertUuid(orgStProgram.getInsertUuid());

                }
                stProgramRepository.save(stProgram);
            }
        }


        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
