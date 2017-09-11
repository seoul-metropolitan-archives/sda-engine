package com.bgf.shbank.domain.mng.error.error_status;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ErrorStatusService extends BaseService<ErrorStatus, ErrorStatus.ErrorStatusId> {

    @Inject
    public ErrorStatusMapper errorStatusMapper;

    @Inject
    public ErrorStatusService(ErrorStatusRepository errorStatusRepository) {
        super(errorStatusRepository);
    }

    public Page<ErrorStatus> find(Pageable pageable, String filter) {
        return filter(errorStatusMapper.findAll(), pageable, filter, ErrorStatus.class);
    }

    public Page<ErrorStatus> findHistory(Pageable pageable, RequestParams<ErrorStatus> requestParams) {

        String filter = requestParams.getString("filter", "");

        ErrorStatus errorStatus = new ErrorStatus();
        errorStatus.setBranchCode(requestParams.getString("branchCode"));
        errorStatus.setCornerCode(requestParams.getString("cornerCode"));
        errorStatus.setTerminalNo(requestParams.getString("terminalNo"));
        errorStatus.setStartDate(requestParams.getTimestamp("startDate"));
        errorStatus.setEndDate(requestParams.getTimestamp("endDate"));

        return filter(errorStatusMapper.findHistory(errorStatus), pageable, filter, ErrorStatus.class);
    }

    public ErrorStatusVO findOne(RequestParams<ErrorStatusVO> requestParams) {

        ErrorStatus errorStatus = new ErrorStatus();

        errorStatus.setErrorDatetime(requestParams.getTimestamp("errorDatetime"));
        errorStatus.setBranchCode(requestParams.getString("branchCode"));
        errorStatus.setCornerCode(requestParams.getString("cornerCode"));
        errorStatus.setTerminalNo(requestParams.getString("terminalNo"));
        errorStatus.setCalleeReqSeqNo(requestParams.getString("calleeReqSeqNo"));

        return buildVO(errorStatusMapper.findOne(errorStatus));
    }

    private ErrorStatusVO buildVO(ErrorStatus errorStatus) {

        if (errorStatus == null) {
            return new ErrorStatusVO();
        } else {
            BoundMapperFacade<ErrorStatus, ErrorStatusVO> mapper =
                    ModelMapperUtils.getMapper("ErrorStatus", this.getClass().getPackage().getName());
            return mapper.map(errorStatus);
        }
    }
}