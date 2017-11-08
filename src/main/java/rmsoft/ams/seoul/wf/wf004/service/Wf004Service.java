/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf004.service;

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
import rmsoft.ams.seoul.common.domain.WfParameter;
import rmsoft.ams.seoul.common.domain.WfWorkflow;
import rmsoft.ams.seoul.common.domain.WfWorkflowJob;
import rmsoft.ams.seoul.common.repository.WfParameterRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowJobRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowRepository;
import rmsoft.ams.seoul.wf.wf004.dao.Wf004Mapper;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00401VO;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00402VO;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00403VO;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 005 service.
 */
@Slf4j
@Service
public class Wf004Service extends BaseService {

    @Autowired
    private WfWorkflowRepository wfWorkflowRepository;

    @Autowired
    private WfWorkflowJobRepository wfWorkflowJobRepository;

    @Autowired
    private WfParameterRepository wfParameterRepository;

    @Inject
    private Wf004Mapper wf004Mapper;

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
    public Page<Wf00401VO> findAllWorkflow(Pageable pageable, RequestParams<Wf00401VO> requestParams) {

        Wf00401VO wf00401VO = new Wf00401VO();
        wf00401VO.setWorkflowName(requestParams.getString("workflowName"));
        wf00401VO.setServiceUuid(requestParams.getString("serviceUuid"));
        wf00401VO.setUseYn(requestParams.getString("useYn"));

        return filter(wf004Mapper.findAllWorkflow(wf00401VO), pageable, "", Wf00401VO.class);
    }

    /**
     * JOB 정보 저장
     *
     * @param wf00401VOList the ac 00501 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveWorkflow(List<Wf00401VO> wf00401VOList) {
        List<WfWorkflow> wfWorkflowList = ModelMapperUtils.mapList(wf00401VOList, WfWorkflow.class);
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
    public Page<Wf00402VO> findWorkflowJob(Pageable pageable, RequestParams<Wf00402VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(wf004Mapper.findWorkflowJob(requestParams.getString("workflowUuid")), pageable, filter, Wf00402VO.class);
    }

    /**
     * Workflow Job 정보 저장
     *
     * @param wf00402VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveWorkfloeJob(List<Wf00402VO> wf00402VOList) {
        List<WfWorkflowJob> wfWorkflowJobList = ModelMapperUtils.mapList(wf00402VOList, WfWorkflowJob.class);
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

    /**********************************************************************************************
     * 파라미터 관련  Service Methods
     **********************************************************************************************
     */

    /**
     * 파라미터 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Wf00403VO> findParameter(Pageable pageable, RequestParams<Wf00403VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(wf004Mapper.findParameter(requestParams.getString("jobUuid")), pageable, filter, Wf00403VO.class);
    }

    /**
     * 파라미터 정보 저장
     *
     * @param wf00403VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveParameter(List<Wf00403VO> wf00403VOList) {
        List<WfParameter> wfParameterList = ModelMapperUtils.mapList(wf00403VOList, WfParameter.class);
        WfParameter orgWfParameter = null;

        for (WfParameter wfParameter : wfParameterList) {
            orgWfParameter = wfParameterRepository.findOne(wfParameter.getId());

            if (orgWfParameter == null) {
                // created
                wfParameter.setParameterUuid(UUIDUtils.getUUID());
                wfParameterRepository.save(wfParameter);
            } else {
                if (wfParameter.isDeleted()) {
                    wfParameterRepository.delete(wfParameter);
                } else {
                    wfParameter.setInsertDate(orgWfParameter.getInsertDate());
                    wfParameter.setInsertUuid(orgWfParameter.getInsertUuid());

                    wfParameterRepository.save(wfParameter);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}