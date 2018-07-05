package rmsoft.ams.seoul.cl.cl003.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl003.dao.Cl003Mapper;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;
import rmsoft.ams.seoul.common.domain.ClClassifiedRecords;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Cl 003 service.
 */
@Service
public class Cl003Service extends BaseService {

    @Inject
    private Cl003Mapper cl003Mapper;

    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    public Page<Cl00301VO> getClassAggregationList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {

        Cl00301VO cl00301VO = new Cl00301VO();
        cl00301VO.setClassUuid(requestParams.getString("classUuid"));
        cl00301VO.setStatusUuid(requestParams.getString("statusUuid01"));
//        cl00301VO.setAggregationCode(requestParams.getString("aggregationCode"));
//        cl00301VO.setTitle(requestParams.getString("title"));
//        cl00301VO.setTypeUuid(requestParams.getString("typeUuid"));
//        cl00301VO.setArrangedFromDate(requestParams.getString("arrangedFromDate01"));
//        cl00301VO.setArrangedToDate(requestParams.getString("arrangedToDate01"));
//        cl00301VO.setContainerUuid(requestParams.getString("containerUuid"));
        return filter(cl003Mapper.getClassAggregationList(cl00301VO), pageable, "", Cl00301VO.class);
    }

    public Page<Cl00301VO> getClassItemList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {

        Cl00301VO cl00301VO = new Cl00301VO();
        cl00301VO.setClassUuid(requestParams.getString("classUuid"));
        cl00301VO.setStatusUuid(requestParams.getString("statusUuid02"));
        return filter(cl003Mapper.getClassItemList(cl00301VO), pageable, "", Cl00301VO.class);
    }

    public ApiResponse updateClassifyRecordsStatus(List<Cl00301VO> list) {
//        List<ClClassifiedRecords> clClassifiedRecordsList = ModelMapperUtils.mapList(list,ClClassifiedRecords.class);
//        ClClassifiedRecords orgClClassifiedRecords = null;
//        int index = 0;
//        String changeStatus = "";
//        for(ClClassifiedRecords clClassifiedRecords : clClassifiedRecordsList) {
//            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
//            orgStArrangeRecordResult = stArrangeRecordsResultRepository.findOne(stArrangeRecordsResult.getId());
//            stArrangeRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111",changeStatus));
//            stArrangeRecordsResult.setInsertDate(orgStArrangeRecordResult.getInsertDate());
//            stArrangeRecordsResult.setInsertUuid(orgStArrangeRecordResult.getInsertUuid());
//            stArrangeRecordsResultRepository.save(stArrangeRecordsResult);
//            index++;
//        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    public Page<St00303VO> getSelectedItem(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setClassUuid(requestParams.getString("classUuid"));

        return filter(cl003Mapper.getSelectedItem(st00303VO), pageable, "", St00303VO.class);
    }
}
