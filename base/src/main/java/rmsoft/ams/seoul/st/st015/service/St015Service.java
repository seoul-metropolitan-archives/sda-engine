package rmsoft.ams.seoul.st.st015.service;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rmsoft.ams.seoul.common.domain.StInventoryContainerResult;
import rmsoft.ams.seoul.common.domain.StInventoryPlan;
import rmsoft.ams.seoul.common.domain.StInventoryRecordResult;
import rmsoft.ams.seoul.common.domain.StProgram;
import rmsoft.ams.seoul.common.repository.StInventoryContainerResultRepository;
import rmsoft.ams.seoul.common.repository.StInventoryPlanRepository;
import rmsoft.ams.seoul.common.repository.StInventoryRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StProgramRepository;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st015.dao.St015Mapper;
import rmsoft.ams.seoul.st.st015.vo.St01501VO;
import rmsoft.ams.seoul.st.st015.vo.St01502VO;
import rmsoft.ams.seoul.st.st015.vo.St01503VO;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;
import rmsoft.ams.seoul.st.st029.dao.St029Mapper;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

@Service
public class St015Service extends BaseService {

    @Inject
    private St015Mapper st015Mapper;

    @Autowired
    private StInventoryPlanRepository stInventoryPlanRepository;

    @Autowired
    private StInventoryContainerResultRepository stInventoryContainerResultRepository;

    @Autowired
    private StInventoryRecordResultRepository stInventoryRecordResultRepository;



    public Page<St01501VO> getStInventoryPlan(Pageable pageable, RequestParams<St01501VO> requestParams) {
        St01501VO st01501VO = new St01501VO();
        //st02901VO.setGateId(requestParams.getString("gateId"));

        return filter(st015Mapper.getStInventoryPlan(st01501VO), pageable, "", St01501VO.class);
    }

    public Page<St01502VO> getStInventoryContainerResult(Pageable pageable, RequestParams<St01502VO> requestParams) {
        St01502VO st01502VO = new St01502VO();
        st01502VO.setInventoryPlanUuid(requestParams.getString("inventoryPlanUuid"));

        //st02901VO.setGateId(requestParams.getString("gateId"));

        return filter(st015Mapper.getStInventoryContainerResult(st01502VO), pageable, "", St01502VO.class);
    }


    public Page<St01503VO> getStInventoryRecordResult(Pageable pageable, RequestParams<St01503VO> requestParams) {
        St01503VO st01503VO = new St01503VO();
        //st02901VO.setGateId(requestParams.getString("gateId"));
        st01503VO.setInventoryPlanUuid(requestParams.getString("inventoryPlanUuid"));
        //st01503VO.setContainerUuid(requestParams.getString("containerUuid"));

        return filter(st015Mapper.getStInventoryRecordResult(st01503VO), pageable, "", St01503VO.class);
    }

    @Transactional
    public ApiResponse saveGetStInventoryPlan(List<St01501VO> list) {
        List<StInventoryPlan> stInventoryPlanList = ModelMapperUtils.mapList(list, StInventoryPlan.class);

        StInventoryPlan orgStInventoryPlan = null;

        for(StInventoryPlan stInventoryPlan : stInventoryPlanList){
            if(stInventoryPlan.isDeleted()){
                stInventoryPlanRepository.delete(stInventoryPlan);
            }else{
                if(stInventoryPlan.isCreated()){
                    stInventoryPlan.setRepositoryUuid("53A20890-F09A-493B-8B43-24B14D003E00");
                    stInventoryPlan.setShelfUuid("E082DAE1-0ABA-45F6-9802-08A1F72DAEE3");
                    stInventoryPlan.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD216","Draft"));
                }else if(stInventoryPlan.isModified()){
                    orgStInventoryPlan = stInventoryPlanRepository.findOne(stInventoryPlan.getId());
                    stInventoryPlan.setInsertDate(orgStInventoryPlan.getInsertDate());
                    stInventoryPlan.setInsertUuid(orgStInventoryPlan.getInsertUuid());
                }

                stInventoryPlanRepository.save(stInventoryPlan);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateStIventoryPlanStatus(List<St01501VO> list) {
        List<StInventoryPlan> stInventoryPlanList = ModelMapperUtils.mapList(list, StInventoryPlan.class);
        StInventoryPlan orgStInventoryPlan = null;
        int index = 0;
        String changeStatus = "";

        StInventoryContainerResult stInventoryContainerResult = null;

        for(StInventoryPlan stInventoryPlan : stInventoryPlanList){
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            //바뀌는 값이 confirm일때
            if(changeStatus.equals("confirm")){
                //서고 서가 행렬단에 있는 location이 없을수도 있다. location이 없으면 모든 하위 container uuid를 가져와야한다.


                //location값이 있는경우
                List<String> ContainerList = jdbcTemplate.queryForList("select CONTAINER_UUID from ST_ARRANGE_CONTAINERS_RESULT where LOCATION_UUID =", String.class);

                //location값이 없는경우 서가 밑에 모든 container를 가져와야한다.



                //stInventoryContainerResultRepository.save()
            }


            orgStInventoryPlan = stInventoryPlanRepository.findOne(stInventoryPlan.getId());
            stInventoryPlan.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD216",changeStatus));
            stInventoryPlan.setInsertDate(orgStInventoryPlan.getInsertDate());
            stInventoryPlan.setInsertUuid(orgStInventoryPlan.getInsertUuid());
            stInventoryPlanRepository.save(stInventoryPlan);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");

    }
}
