package com.bgf.shbank.domain.mng.equip.terminal_status;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class TerminalStatusService extends BaseService<TerminalStatus, TerminalStatus.TerminalStatusId> {

    @Inject
    public TerminalStatusService(TerminalStatusRepository terminalStatusRepository) {
        super(terminalStatusRepository);
    }

    public Page<TerminalStatus> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, TerminalStatus.class);
    }

    public Page<TerminalStatus> find(Pageable pageable, RequestParams<TerminalStatus> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");

        QTerminalStatus qTerminalStatus = QTerminalStatus.terminalStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qTerminalStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qTerminalStatus.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qTerminalStatus.terminalNo.eq(terminalNo));
        }

        /*if (isNotEmpty(filter)) {

            filter = buildLikeExpression(filter);

            BooleanBuilder likeBuilder = new BooleanBuilder();

            likeBuilder.or(qTerminalStatus.jisaCode.like(jisaCode))
                    .or(qTerminalStatus.groupNm.like(filter))
                    .or(qTerminalStatus.code.like(filter))
                    .or(qTerminalStatus.name.like(filter));

            builder.and(likeBuilder);
        }*/

        pageable = buildPageable(pageable);

        Page<TerminalStatus> resultPage = findAll(builder, pageable);

        return resultPage;
    }

    public List<TerminalStatus> find(RequestParams<TerminalStatusVO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");

        QTerminalStatus qTerminalStatus = QTerminalStatus.terminalStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qTerminalStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qTerminalStatus.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qTerminalStatus.terminalNo.eq(terminalNo));
        }



        List<TerminalStatus> resultList = findAll(builder);

        return resultList;
    }

    /*private String buildLikeExpression(String filter) {
        return "%" + filter + "%";
    }*/

    private Pageable buildPageable(Pageable pageable) {

        Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "branchCode");

        Sort sort;

        if (pageable.getSort() == null) {
            Sort.Order defaultOrder = new Sort.Order(Sort.Direction.ASC, "jisaCode");
            sort = new Sort(defaultOrder, sortOrder);
        } else {
            sort = pageable.getSort().and(new Sort(sortOrder));
        }

        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    public TerminalStatus findOne(TerminalStatus terminalStatus) {

        String jisaCode = terminalStatus.getJisaCode();
        String branchCode = terminalStatus.getBranchCode();
        String cornerCode = terminalStatus.getCornerCode();
        String terminalNo = terminalStatus.getTerminalNo();

        QTerminalStatus qTerminalStatus = QTerminalStatus.terminalStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qTerminalStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qTerminalStatus.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qTerminalStatus.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qTerminalStatus.terminalNo.eq(terminalNo));
        }

        return findOne(builder);
    }
}