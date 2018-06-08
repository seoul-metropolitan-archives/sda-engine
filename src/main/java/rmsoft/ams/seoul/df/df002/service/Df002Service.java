package rmsoft.ams.seoul.df.df002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.DfDegree;
import rmsoft.ams.seoul.common.repository.DfDegreeRepository;
import rmsoft.ams.seoul.df.df002.dao.Df002Mapper;
import rmsoft.ams.seoul.df.df002.vo.Df00201VO;
import rmsoft.ams.seoul.df.df002.vo.Df00202VO;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Df002Service extends BaseService {

    @Autowired
    private Df002Mapper mapper;

    @Autowired
    private DfDegreeRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Df00201VO> searchList(RequestParams<Df00201VO> param) {
        Df00201VO df00201VO = new Df00201VO();

        df00201VO.setFreezeYN(param.getString("freezeYN"));
        df00201VO.setEventCode(param.getString("eventCode"));
        df00201VO.setEventName(param.getString("eventName"));
        df00201VO.setDegree(param.getInt("degree"));
        df00201VO.setEndYN(param.getString("endYN"));

        return mapper.searchList(df00201VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Df00201VO> itemList) {

        List<DfDegree> dfDegreeList = ModelMapperUtils.mapList(itemList, DfDegree.class);

        DfDegree orgDfDegree = null;
        int degree = 0;

        for (DfDegree dfDegree : dfDegreeList) {
            if (dfDegree.isCreated() || dfDegree.isModified()) {
                orgDfDegree = repository.findOne(dfDegree.getId());

                if (mapper.checkDegree(dfDegree.getDisposalFreezeEventUuid()) >= dfDegree.getDegree()){
                    if(dfDegree.isCreated()) {
                        return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("DF002_05"));
                    }else if(dfDegree.isModified()){
                        if(orgDfDegree.getDegree() != dfDegree.getDegree()){
                            return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("DF002_05"));
                        }
                    }
                }

                if(dfDegree.isCreated()){ //disposalFreezeDegreeUuid 없을때
                    degree = jdbcTemplate.queryForObject("select FC_DF_DEGREE_NUMBER('"+ dfDegree.getDisposalFreezeEventUuid() +"') from dual", Integer.class);
                    dfDegree.setDegree(degree);
                }

                if(dfDegree.isModified()) {
                    dfDegree.setInsertDate(orgDfDegree.getInsertDate());
                    dfDegree.setInsertUuid(orgDfDegree.getInsertUuid());

                    if(dfDegree.getEndYn().equals("Y")){
                        dfDegree.setTerminatorUuid(SessionUtils.getCurrentLoginUserUuid());
                        dfDegree.setEndDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                    }else{
                        dfDegree.setTerminatorUuid("");
                        dfDegree.setEndDate(null);
                    }
                }

                repository.save(dfDegree);
            } else if (dfDegree.isDeleted()) {
                if (mapper.checkDegree(dfDegree.getDisposalFreezeEventUuid()) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("DF002_05"));
                } else {
                    repository.delete(dfDegree);
                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update status api response.
     *
     * @param list the list
     * @return the api response
     */
    public ApiResponse updateStatus(List<Df00201VO> list){
        List<DfDegree> dfDegreeList = ModelMapperUtils.mapList(list,DfDegree.class);
        DfDegree orgDfDegree = null;
        int index = 0;
        String changeStatus = "";
        for (DfDegree dfDegree : dfDegreeList) {
            orgDfDegree = repository.findOne(dfDegree.getId());
            dfDegree.setInsertDate(orgDfDegree.getInsertDate());
            dfDegree.setInsertUuid(orgDfDegree.getInsertUuid());
            repository.save(dfDegree);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update classification scheme con detail.
     *
     * @param requestParams the request params
     */
    public void saveDetailItem(RequestParams<Df00201VO> requestParams) {
        DfDegree dfDegree = new DfDegree();

        if(StringUtils.isEmpty(requestParams.getString("disposalFreezeEventUuid"))){
            return;
        }

        dfDegree.setDisposalFreezeEventUuid(requestParams.getString("disposalFreezeEventUuid"));

        DfDegree orgDfDegree = null;

        orgDfDegree = repository.findOne(dfDegree.getId());

        if(orgDfDegree != null){
            dfDegree = orgDfDegree;
            dfDegree.setInsertDate(orgDfDegree.getInsertDate());
            dfDegree.setInsertUuid(orgDfDegree.getInsertUuid());
        }
        repository.save(dfDegree);
    }

}