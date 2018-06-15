package rmsoft.ams.seoul.st.st001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.common.domain.StLocation;
import rmsoft.ams.seoul.common.domain.StRepository;
import rmsoft.ams.seoul.common.domain.StShelf;
import rmsoft.ams.seoul.common.repository.StLocationRepository;
import rmsoft.ams.seoul.common.repository.StRepositoryRepository;
import rmsoft.ams.seoul.common.repository.StShelfRepository;
import rmsoft.ams.seoul.st.st001.dao.St001Mapper;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st001.vo.St00102VO;
import rmsoft.ams.seoul.st.st001.vo.St00103VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

@Service
public class St001Service extends BaseService {

    @Inject
    private St001Mapper st001Mapper;

    @Autowired
    private StRepositoryRepository stRepositoryRepository;

    @Autowired
    private StShelfRepository stShelfRepository;

    @Autowired
    private StLocationRepository stLocationRepository;

    public Page<St00101VO> getStRepositoryList(Pageable pageable, RequestParams<St00101VO> requestParams) {
        St00101VO st00101VO = new St00101VO();
        st00101VO.setRepositoryCode(requestParams.getString("repositoryCode"));
        st00101VO.setRepositoryName(requestParams.getString("repositoryName"));
        st00101VO.setStatusUuid (requestParams.getString("statusUuid"));
        st00101VO.setUseYn(requestParams.getString("useYn"));
        return filter(st001Mapper.getStRepositoryList(st00101VO), pageable, "", St00101VO.class);
    }

    public Page<St00102VO> getStShelfList(Pageable pageable, RequestParams<St00102VO> requestParams) {
        St00102VO st00102VO = new St00102VO();
        st00102VO.setShelfCode(requestParams.getString("shelfCode"));
        st00102VO.setShelfName(requestParams.getString("shelfName"));
        st00102VO.setStatusUuid (requestParams.getString("statusUuid"));
        st00102VO.setUseYn(requestParams.getString("useYn"));

        return filter(st001Mapper.getStShelfList(st00102VO), pageable, "", St00102VO.class);
    }

    public Page<St00103VO> getLocationList(Pageable pageable, RequestParams<St00103VO> requestParams) {
        St00103VO st00103VO = new St00103VO();
        st00103VO.setRowNo(requestParams.getString("rowNo"));
        st00103VO.setColumnNo(requestParams.getString("columnNo"));
        st00103VO.setStatusUuid (requestParams.getString("statusUuid"));
        st00103VO.setUseYn(requestParams.getString("useYn"));

        return filter(st001Mapper.getLocationList(st00103VO), pageable, "", St00103VO.class);
    }

    public ApiResponse updateRepositoryStatus(List<St00101VO> list) {
        List<StRepository> stRepositoryList = ModelMapperUtils.mapList(list,StRepository.class);
        StRepository orgStReposoitory = null;
        int index = 0;
        String changeStatus = "";
        for(StRepository stRepository : stRepositoryList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStReposoitory = stRepositoryRepository.findOne(stRepository.getId());
            stRepository.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stRepository.setInsertDate(orgStReposoitory.getInsertDate());
            stRepository.setInsertUuid(orgStReposoitory.getInsertUuid());
            stRepositoryRepository.save(stRepositoryList);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateShelfStatus(List<St00102VO> list) {
        List<StShelf> stShelfList = ModelMapperUtils.mapList(list,StShelf.class);
        StShelf orgStShelf = null;
        int index = 0;
        String changeStatus = "";
        for(StShelf stShelf : stShelfList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStShelf = stShelfRepository.findOne(stShelf.getId());
            stShelf.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stShelf.setInsertDate(orgStShelf.getInsertDate());
            stShelf.setInsertUuid(orgStShelf.getInsertUuid());
            stShelfRepository.save(stShelfList);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateLocationStatus(List<St00103VO> list) {
        List<StLocation> stLocationList = ModelMapperUtils.mapList(list,StLocation.class);
        StLocation orgStLocation = null;
        int index = 0;
        String changeStatus = "";
        for(StLocation stLocation : stLocationList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStLocation = stLocationRepository.findOne(stLocation.getId());
            stLocation.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stLocation.setInsertDate(orgStLocation.getInsertDate());
            stLocation.setInsertUuid(orgStLocation.getInsertUuid());
            stLocationRepository.save(stLocationList);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}