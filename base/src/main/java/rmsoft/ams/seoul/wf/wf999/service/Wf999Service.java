/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.service;

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
import rmsoft.ams.seoul.wf.wf999.dao.Wf999Mapper;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99901VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99902VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99903VO;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 005 service.
 */
@Slf4j
@Service
public class Wf999Service extends BaseService {

    @Autowired
    private WfWorkflowRepository wfWorkflowRepository;

    @Autowired
    private WfWorkflowJobRepository wfWorkflowJobRepository;

    @Autowired
    private WfParameterRepository wfParameterRepository;

    @Inject
    private Wf999Mapper Wf999Mapper;

    /**********************************************************************************************
     * JOB 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 Workflow 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page page
     */
    public Page<Wf99901VO> findAllWorkflowResult(Pageable pageable, RequestParams<Wf99901VO> requestParams) {

        Wf99901VO Wf99901VO = new Wf99901VO();
        Wf99901VO.setServiceUuid(requestParams.getString("serviceUuid"));
        Wf99901VO.setStatusUuid(requestParams.getString("statusUuid"));
        Wf99901VO.setBatchId(requestParams.getString("batchId"));
        Wf99901VO.setWorkflowName(requestParams.getString("workflowName"));
        Wf99901VO.setExecuter(requestParams.getString("executer"));
        Wf99901VO.setMenu(requestParams.getString("menu"));
        Wf99901VO.setStartFromDate(requestParams.getString("startFromDate"));
        Wf99901VO.setStartToDate(requestParams.getString("startToDate"));
        Wf99901VO.setEndFromDate(requestParams.getString("endFromDate"));
        Wf99901VO.setEndToDate(requestParams.getString("endToDate"));


        return filter(Wf999Mapper.findAllWorkflowResult(Wf99901VO), pageable, "", Wf99901VO.class);
    }

    /**
     * JOB 정보 저장
     *
     * @param Wf99901VOList the ac 00501 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveWorkflow(List<Wf99901VO> Wf99901VOList) {
        List<WfWorkflow> wfWorkflowList = ModelMapperUtils.mapList(Wf99901VOList, WfWorkflow.class);
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
     * @return page page
     */
    public Page<Wf99902VO> findWorkflowJobResult(Pageable pageable, RequestParams<Wf99902VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(Wf999Mapper.findWorkflowJobResult(requestParams.getString("workflowResultUuid")), pageable, filter, Wf99902VO.class);
    }

    /**
     * Workflow Job 정보 저장
     *
     * @param Wf99902VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveWorkfloeJob(List<Wf99902VO> Wf99902VOList) {
        List<WfWorkflowJob> wfWorkflowJobList = ModelMapperUtils.mapList(Wf99902VOList, WfWorkflowJob.class);
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
     * @return page page
     */
    public Page<Wf99903VO> findParameterResult(Pageable pageable, RequestParams<Wf99903VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(Wf999Mapper.findParameterResult(requestParams.getString("jobResultUuid")), pageable, filter, Wf99903VO.class);
    }

    /**
     * 파라미터 정보 저장
     *
     * @param Wf99903VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveParameter(List<Wf99903VO> Wf99903VOList) {
        List<WfParameter> wfParameterList = ModelMapperUtils.mapList(Wf99903VOList, WfParameter.class);
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