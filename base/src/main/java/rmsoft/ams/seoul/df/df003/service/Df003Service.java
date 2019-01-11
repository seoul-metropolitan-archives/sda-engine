package rmsoft.ams.seoul.df.df003.service;

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
import rmsoft.ams.seoul.common.domain.DfResult;
import rmsoft.ams.seoul.common.repository.DfResultRepository;
import rmsoft.ams.seoul.df.df003.dao.Df003Mapper;
import rmsoft.ams.seoul.df.df003.vo.Df00301VO;
import rmsoft.ams.seoul.df.df003.vo.Df00302VO;
import rmsoft.ams.seoul.df.df003.vo.Df00303VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class Df003Service extends BaseService {

    @Autowired
    private Df003Mapper mapper;

    @Autowired
    private DfResultRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Df00301VO> searchTree(RequestParams<Df00301VO> param) {
        Df00301VO df00301VO = new Df00301VO();

        df00301VO.setDisposalFreezeEventUuid(param.getString("disposalFreezeEventUuid"));
        df00301VO.setDisposalFreezeDegreeUuid(param.getString("disposalFreezeDegreeUuid"));

        return mapper.searchTree(df00301VO);
    }

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Df00302VO> searchList(RequestParams<Df00302VO> param) {
        Df00302VO df00302VO = new Df00302VO();

        df00302VO.setDisposalFreezeEventUuid(param.getString("disposalFreezeEventUuid"));
        df00302VO.setDisposalFreezeDegreeUuid(param.getString("disposalFreezeDegreeUuid"));
        df00302VO.setAggregationUuid(param.getString("aggregationUuid"));
        df00302VO.setItemUuid(param.getString("itemUuid"));
        df00302VO.setFreezedFromDate(param.getString("freezedFromDate"));
        df00302VO.setFreezedToDate(param.getString("freezedToDate"));

        return mapper.searchList(df00302VO);
    }

    /**
     * Save entity type api response.
     *
     * @param itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Df00302VO> itemList) {

        List<DfResult> saveList = ModelMapperUtils.mapList(itemList, DfResult.class);

        DfResult orgItem = null;

        for (DfResult dfResult : saveList) {
            if (dfResult.isDeleted()) {
                repository.delete(dfResult);
            } else {
                if (dfResult.isModified()) {
                    orgItem = repository.findOne(dfResult.getId());
                    dfResult.setInsertDate(orgItem.getInsertDate());
                    dfResult.setInsertUuid(orgItem.getInsertUuid());
                } else {
                    dfResult.setDisposalFreezeResultUuid(UUIDUtils.getUUID());
                    dfResult.setFreezedDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                }

                repository.save(dfResult);
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
    public ApiResponse unfreeze(List<Df00302VO> list) {
        if (list.size() == 0) {
            return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
        }

        if (list.get(0).getDisposalFreezeResultUuid() == null) {
            Df00302VO df00302VO = new Df00302VO();
            df00302VO.setDisposalFreezeEventUuid(list.get(0).getDisposalFreezeEventUuid());
            df00302VO.setDisposalFreezeDegreeUuid(list.get(0).getDisposalFreezeDegreeUuid());

            list = mapper.searchList(df00302VO);
        }

        List<DfResult> saveList = ModelMapperUtils.mapList(list, DfResult.class);

        for (DfResult dfResult : saveList) {
            repository.delete(dfResult);
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public Page<Df00302VO> search(Pageable pageable, RequestParams<Df00302VO> requestParams) {

        Df00302VO df00302VO = new Df00302VO();
        df00302VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        df00302VO.setDisposalFreezeEventUuid(requestParams.getString("disposalFreezeEventUuid"));
        df00302VO.setDisposalFreezeDegreeUuid(requestParams.getString("disposalFreezeDegreeUuid"));

        return filter(mapper.search(df00302VO), pageable, "", Df00302VO.class);
    }

    public List<Df00303VO> searchAggregationTree(RequestParams<Df00303VO> requestParams) {

        Df00303VO df00303VO = new Df00303VO();
        df00303VO.setTypeUuid(CommonCodeUtils.getCodeDetailUuid("CD127", "Normal"));

        return mapper.searchAggregationTree(df00303VO);
    }

}