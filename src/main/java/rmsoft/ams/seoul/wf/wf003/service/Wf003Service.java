/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf003.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.WfWorkflow;
import rmsoft.ams.seoul.common.domain.WfWorkflowJob;
import rmsoft.ams.seoul.common.repository.WfWorkflowJobRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowRepository;
import rmsoft.ams.seoul.wf.wf003.dao.Wf003Mapper;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00302VO;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 005 service.
 */
@Slf4j
@Service
public class Wf003Service extends BaseService {

    @Autowired
    private WfWorkflowRepository wfWorkflowRepository;

    @Autowired
    private WfWorkflowJobRepository wfWorkflowJobRepository;

    @Inject
    private Wf003Mapper wf003Mapper;

    /**********************************************************************************************
     * JOB 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 Workflow 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Wf00301VO> findAllWorkflow(Pageable pageable, RequestParams<Wf00301VO> requestParams) {

        Wf00301VO wf00301VO = new Wf00301VO();
        wf00301VO.setWorkflowName(requestParams.getString("workflowName"));
        wf00301VO.setServiceUuid(requestParams.getString("serviceUuid"));
        wf00301VO.setUseYn(requestParams.getString("useYn"));

        return filter(wf003Mapper.findAllWorkflow(wf00301VO), pageable, "", Wf00301VO.class);
    }

    /**
     * JOB 정보 저장
     *
     * @param wf00301VOList the ac 00501 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveWorkflow(List<Wf00301VO> wf00301VOList) {
        List<WfWorkflow> wfWorkflowList = ModelMapperUtils.mapList(wf00301VOList, WfWorkflow.class);
        WfWorkflow orgWfWorkflow = null;

        for (WfWorkflow wfWorkflow : wfWorkflowList) {
            orgWfWorkflow = wfWorkflowRepository.findOne(wfWorkflow.getId());

            if (orgWfWorkflow == null) {
                // created
                //wfWorkflow.setWorkflowUuid(UUIDUtils.getUUID());
                wfWorkflowRepository.save(wfWorkflow);
            } else {
                if (wfWorkflow.isDeleted()) {
                    wfWorkflowRepository.delete(wfWorkflow);
                } else {
                    wfWorkflow.setInsertDate(orgWfWorkflow.getInsertDate());
                    wfWorkflow.setInsertUuid(orgWfWorkflow.getInsertUuid());

                    wfWorkflowRepository.save(wfWorkflow);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


    /**********************************************************************************************
     * 파라미터 관련  Service Methods
     **********************************************************************************************
     */

    /**
     * Workflow Job 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Wf00302VO> findWorkflowJob(Pageable pageable, RequestParams<Wf00302VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(wf003Mapper.findWorkflowJob(requestParams.getString("workflowUuid")), pageable, filter, Wf00302VO.class);
    }

    /**
     * Workflow Job 정보 저장
     *
     * @param wf00302VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveWorkfloeJob(List<Wf00302VO> wf00302VOList) {
        List<WfWorkflowJob> wfWorkflowJobList = ModelMapperUtils.mapList(wf00302VOList, WfWorkflowJob.class);
        WfWorkflowJob orgWfWorkflowJob = null;

        for (WfWorkflowJob wfWorkflowJob : wfWorkflowJobList) {
            orgWfWorkflowJob = wfWorkflowJobRepository.findOne(wfWorkflowJob.getId());

            if (orgWfWorkflowJob == null) {
                // created
                wfWorkflowJob.setWorkflowJobUuid(UUIDUtils.getUUID());
                wfWorkflowJobRepository.save(wfWorkflowJob);
            } else {
                if (wfWorkflowJob.isDeleted()) {
                    wfWorkflowJobRepository.delete(wfWorkflowJob);
                } else {
                    wfWorkflowJob.setInsertDate(orgWfWorkflowJob.getInsertDate());
                    wfWorkflowJob.setInsertUuid(orgWfWorkflowJob.getInsertUuid());

                    wfWorkflowJobRepository.save(wfWorkflowJob);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}