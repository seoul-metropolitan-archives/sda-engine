package rmsoft.ams.seoul.st.st001.service;

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
import rmsoft.ams.seoul.common.domain.*;
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
import java.util.Map;

import com.querydsl.core.types.Predicate;

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
        st00101VO.setStatusUuid (requestParams.getString("statusUuid01"));
        st00101VO.setUseYn(requestParams.getString("useYn01"));
        return filter(st001Mapper.getStRepositoryList(st00101VO), pageable, "", St00101VO.class);
    }

    public Page<St00102VO> getStShelfList(Pageable pageable, RequestParams<St00102VO> requestParams) {
        St00102VO st00102VO = new St00102VO();
        st00102VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st00102VO.setShelfCode(requestParams.getString("shelfCode"));
        st00102VO.setShelfName(requestParams.getString("shelfName"));
        st00102VO.setStatusUuid (requestParams.getString("statusUuid02"));
        st00102VO.setUseYn(requestParams.getString("useYn02"));

        return filter(st001Mapper.getStShelfList(st00102VO), pageable, "", St00102VO.class);
    }

    public Page<St00103VO> getLocationList(Pageable pageable, RequestParams<St00103VO> requestParams) {
        St00103VO st00103VO = new St00103VO();
        st00103VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st00103VO.setRowNo(requestParams.getString("rowNo"));
        st00103VO.setColumnNo(requestParams.getString("columnNo"));
        st00103VO.setStatusUuid (requestParams.getString("statusUuid03"));
        st00103VO.setUseYn(requestParams.getString("useYn03"));

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

    @Transactional
    public ApiResponse saveRepositoryList(List<St00101VO> list) {
        List<StRepository> stRepositoryList = ModelMapperUtils.mapList(list, StRepository.class);
        StRepository orgStRepository = null;
        int cnt = 0;
        for (StRepository stRepository : stRepositoryList) {
            if(stRepository.isDeleted()){
                stRepositoryRepository.delete(stRepository);
                //연관 데이터 삭제
                QStShelf qStShelf = QStShelf.stShelf;
                Predicate predicate = qStShelf.shelfUuid.eq(stRepository.getRepositoryUuid());
                Iterable<StShelf> delStShelf = stShelfRepository.findAll(predicate);
                delStShelf.forEach(stShelf -> {
                    // Shelf 삭제
                    stShelfRepository.delete(stShelf.getId());

                    // Shelf 관련된 Location 삭제
                    QStLocation qStLocation = QStLocation.stLocation;
                    stLocationRepository.delete(stLocationRepository.findAll(qStLocation.shelfUuid.eq(stShelf.getShelfUuid())));
                });


                stShelfRepository.delete(stShelfRepository.findAll(predicate));


            }else{
                if(stRepository.isCreated()){
                    String orgCodeSeq = jdbcTemplate.queryForObject("SELECT ST_REPOSITORY_CODE_SEQ.NEXTVAL FROM dual", String.class);
                    String repositoryCodeSeq = orgCodeSeq;
                    for (cnt = 0; cnt < Math.abs(orgCodeSeq.length() - 3); cnt++) {
                        repositoryCodeSeq = "0" + repositoryCodeSeq;
                    }
                    stRepository.setRepositoryCode(repositoryCodeSeq);
                    stRepository.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                }else if(stRepository.isModified()){
                    orgStRepository = stRepositoryRepository.findOne(stRepository.getId());
                    stRepository.setInsertDate(orgStRepository.getInsertDate());
                    stRepository.setInsertUuid(orgStRepository.getInsertUuid());

                    if(stRepository.getUseYn().equals("N")){
                        jdbcTemplate.update("UPDATE ST_SHELF SET USE_YN = 'N' WHERE REPOSITORY_UUID = '" + stRepository.getRepositoryUuid() + "' ");
                        List<Map<String,Object>> results = jdbcTemplate.queryForList("SELECT SHELF_UUID FROM ST_SHELF WHERE REPOSITORY_UUID ='" + stRepository.getRepositoryUuid() + "'");
                        for (Map m : results){
                            jdbcTemplate.update("UPDATE ST_LOCATION SET USE_YN = 'N' WHERE SHELF_UUID = '" + m.get("SHELF_UUID") + "' ");
                        }
                    }
                }
                stRepositoryRepository.save(stRepository);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveShelfList(List<St00102VO> list) {
        List<StShelf> stShelfList = ModelMapperUtils.mapList(list, StShelf.class);
        StShelf orgStShelf = null;
        int cnt = 0;
        for (StShelf stShelf : stShelfList) {
            if(stShelf.isDeleted()){
                stShelfRepository.delete(stShelf);
            }else{
                if(stShelf.isCreated()){
                    String orgCodeSeq = jdbcTemplate.queryForObject("SELECT ST_SHELF_CODE_SEQ.NEXTVAL FROM dual", String.class);
                    String shelfCodeSeq = orgCodeSeq;
                    for (cnt = 0; cnt < Math.abs(orgCodeSeq.length() - 3); cnt++) {
                        shelfCodeSeq = "0" + shelfCodeSeq;
                    }
                    stShelf.setShelfCode(shelfCodeSeq);
                    stShelf.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                }else if(stShelf.isModified()){
                    orgStShelf = stShelfRepository.findOne(stShelf.getId());
                    stShelf.setInsertDate(orgStShelf.getInsertDate());
                    stShelf.setInsertUuid(orgStShelf.getInsertUuid());

                    if(stShelf.getUseYn().equals("N")){
                        jdbcTemplate.update("UPDATE ST_SHELF SET USE_YN = 'N' WHERE REPOSITORY_UUID = '" + stShelf.getShelfUuid() + "' ");
                    }
                }
                stShelfRepository.save(stShelf);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveLocationList(List<St00103VO> list) {
        List<StLocation> stLocationList = ModelMapperUtils.mapList(list, StLocation.class);
        StLocation orgStLocation = null;
        for (StLocation stLocation : stLocationList) {
            if(stLocation.isDeleted()){
                stLocationRepository.delete(stLocation);
            }else{
                if(stLocation.isCreated()){
                    stLocation.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                }else if(stLocation.isModified()){
                    orgStLocation = stLocationRepository.findOne(stLocation.getId());
                    stLocation.setInsertDate(orgStLocation.getInsertDate());
                    stLocation.setInsertUuid(orgStLocation.getInsertUuid());
                }
                stLocationRepository.save(stLocation);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}