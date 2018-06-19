package rmsoft.ams.seoul.st.st002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.ClClass;
import rmsoft.ams.seoul.common.domain.RsRecordSchedule;
import rmsoft.ams.seoul.common.domain.StContainer;
import rmsoft.ams.seoul.common.repository.StContainerRepository;
import rmsoft.ams.seoul.st.st002.dao.St002Mapper;
import rmsoft.ams.seoul.st.st002.vo.St00201VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;


@Service
public class St002Service extends BaseService {

    @Inject
    private St002Mapper st002Mapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StContainerRepository stContainerRepository;

    public Page<St00201VO> getContainerHierarchyList(Pageable pageable) {

        return filter(st002Mapper.getContainerHierarchyList(),pageable,"",St00201VO.class);

    }
    public Page<St00201VO> getContainerList(Pageable pageable, RequestParams<St00201VO> requestParams) {

        St00201VO st00201VO = new St00201VO();
        st00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        st00201VO.setContainerName(requestParams.getString("containerName"));
        st00201VO.setParentContainerUuid(requestParams.getString("parentContainerUuid"));
        st00201VO.setContainerTypeUuid(requestParams.getString("containerTypeUuid"));
        st00201VO.setControlNumber(requestParams.getString("controlNumber"));
        st00201VO.setProvenance(requestParams.getString("provenance"));
        st00201VO.setUseYn(requestParams.getString("useYn"));

        return filter(st002Mapper.getContainerList(st00201VO), pageable, "", St00201VO.class);
    }

    @Transactional
    public ApiResponse saveContainerList(List<St00201VO> list) {
        List<StContainer> stContainerList = ModelMapperUtils.mapList(list, StContainer.class);
        StContainer orgStContainer = null;
        int cnt = 0;
        String orderKey = "";
        Boolean parentChanged = false;
        for (StContainer stContainer : stContainerList) {
            if(stContainer.isDeleted()){
                cnt = jdbcTemplate.queryForObject("select COUNT(*) FROM ST_CONTAINER WHERE PARENT_CONTAINER_UUID = '"+ stContainer.getContainerUuid()+"' ", Integer.class);
                if(cnt == 0){
                    stContainerRepository.delete(stContainer);
                }
            }else{
                if(isEmpty(stContainer.getParentContainerUuid())){stContainer.setParentContainerUuid(""); }
                if(isEmpty(stContainer.getOrderNo())){stContainer.setOrderNo(""); }

                stContainer.setCreationStartDate(stContainer.getCreationStartDate().replace("-","") );
                stContainer.setCreationEndDate(stContainer.getCreationEndDate().replace("-","") );

                if(stContainer.isCreated()){
                    stContainer.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                    parentChanged = true;
                }else if(stContainer.isModified()){
                    orgStContainer = stContainerRepository.findOne(stContainer.getId());
                    stContainer.setInsertDate(orgStContainer.getInsertDate());
                    stContainer.setInsertUuid(orgStContainer.getInsertUuid());

                    if(isEmpty(orgStContainer.getParentContainerUuid())){orgStContainer.setParentContainerUuid(""); }
                    if(isEmpty(orgStContainer.getOrderNo())){orgStContainer.setOrderNo(""); }

                    if(!orgStContainer.getParentContainerUuid().equals(stContainer.getParentContainerUuid()) ||
                            !orgStContainer.getOrderNo().equals(stContainer.getOrderNo())){
                        parentChanged = true;
                    }
                }
                if(parentChanged){
                    orderKey = jdbcTemplate.queryForObject("select FC_ST_CONTAINER_SORTKEY('" + stContainer.getParentContainerUuid() + "' , '" + stContainer.getOrderNo() + "') from dual", String.class);
                    stContainer.setOrderKey(orderKey);
                    parentChanged = false;
                }

                stContainerRepository.save(stContainer);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    public ApiResponse updateStatus(List<St00201VO> list) {
        List<StContainer> stContainerList = ModelMapperUtils.mapList(list, StContainer.class);
        StContainer orgStContainer = null;
        int index = 0;
        String changeStatus = "";
        for(StContainer stContainer : stContainerList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStContainer = stContainerRepository.findOne(stContainer.getId());
            stContainer.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stContainer.setCreationStartDate(stContainer.getCreationStartDate().replace("-","") );
            stContainer.setCreationEndDate(stContainer.getCreationEndDate().replace("-","") );
            stContainer.setInsertDate(orgStContainer.getInsertDate());
            stContainer.setInsertUuid(orgStContainer.getInsertUuid());
            stContainerRepository.save(stContainer);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public Page<St00201VO> getSelectedContainerList(Pageable pageable, RequestParams<St00201VO> requestParams) {

        St00201VO st00201VO = new St00201VO();
        st00201VO.setContainerUuid(requestParams.getString("containerUuid"));
        st00201VO.setOrderNo(requestParams.getString("orderNo"));
        st00201VO.setOrderKey(requestParams.getString("orderKey"));
        st00201VO.setStatusUuid(requestParams.getString("statusUuid"));
        st00201VO.setUseYn(requestParams.getString("useYn"));
        st00201VO.setOrderKey1(requestParams.getString("orderKey1"));


        return filter(st002Mapper.getSelectedContainerList(st00201VO), pageable, "", St00201VO.class);
    }
}
