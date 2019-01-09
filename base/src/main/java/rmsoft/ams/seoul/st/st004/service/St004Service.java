package rmsoft.ams.seoul.st.st004.service;

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
import rmsoft.ams.seoul.st.st004.dao.St004Mapper;
import rmsoft.ams.seoul.st.st004.vo.St00401VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St004Service extends BaseService {
    @Inject
    private St004Mapper st004Mapper;
    @Autowired
    private StArrangeContainersResultRepository stArrangeContainersResultRepository;

    public Page<St00401VO> getStArrangeContainersResult(Pageable pageable, RequestParams<St00401VO> requestParams) {
        St00401VO st00401VO = new St00401VO();
        st00401VO.setStatusUuid(requestParams.getString("statusUuid"));
        st00401VO.setContainerType(requestParams.getString("containerTypeUuid"));
        st00401VO.setContainerName(requestParams.getString("containerName"));
        st00401VO.setLocationUuid(requestParams.getString("locationUuid"));
        return filter(st004Mapper.getStArrangeContainersResult(st00401VO), pageable, "", St00401VO.class);
    }

    public ApiResponse updateStatus(List<St00401VO> list) {
        List<StArrangeContainersResult> stArrangeContainersResultList = ModelMapperUtils.mapList(list,StArrangeContainersResult.class);
        StArrangeContainersResult orgStArrangeContainerResult = null;
        int index = 0;
        String changeStatus = "";
        for(StArrangeContainersResult stArrangeContainersResult : stArrangeContainersResultList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStArrangeContainerResult = stArrangeContainersResultRepository.findOne(stArrangeContainersResult.getId());
            stArrangeContainersResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stArrangeContainersResult.setInsertDate(orgStArrangeContainerResult.getInsertDate());
            stArrangeContainersResult.setInsertUuid(orgStArrangeContainerResult.getInsertUuid());
            stArrangeContainersResultRepository.save(stArrangeContainersResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveArrangeRecordList(List<St00401VO> list) {
        List<StArrangeContainersResult> stArrangeContainersResultList = ModelMapperUtils.mapList(list,StArrangeContainersResult.class);
        StArrangeContainersResult orgStArrangeContainerResult = null;
        for(StArrangeContainersResult stArrangeContainersResult : stArrangeContainersResultList) {
            if(stArrangeContainersResult.isDeleted()){
                stArrangeContainersResultRepository.delete(stArrangeContainersResult);
            }else{
                if(stArrangeContainersResult.isModified()){
                    orgStArrangeContainerResult = stArrangeContainersResultRepository.findOne(stArrangeContainersResult.getId());
                    stArrangeContainersResult.setInsertDate(orgStArrangeContainerResult.getInsertDate());
                    stArrangeContainersResult.setInsertUuid(orgStArrangeContainerResult.getInsertUuid());
                }
                else{
                    stArrangeContainersResult.setArrangeContainersResultUuid(UUIDUtils.getUUID());
                    stArrangeContainersResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                    stArrangeContainersResult.setArrangedDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                    stArrangeContainersResult.setDescription("");
                }
                stArrangeContainersResultRepository.save(stArrangeContainersResult);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}