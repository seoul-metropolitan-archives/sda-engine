/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf001.service;

import com.querydsl.core.types.Predicate;
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
import rmsoft.ams.seoul.common.domain.QWfParameter;
import rmsoft.ams.seoul.common.domain.QWfWorkflowJob;
import rmsoft.ams.seoul.common.domain.WfJob;
import rmsoft.ams.seoul.common.domain.WfParameter;
import rmsoft.ams.seoul.common.repository.WfJobRepository;
import rmsoft.ams.seoul.common.repository.WfParameterRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowJobRepository;
import rmsoft.ams.seoul.wf.wf001.dao.Wf001Mapper;
import rmsoft.ams.seoul.wf.wf001.vo.Wf00101VO;
import rmsoft.ams.seoul.wf.wf001.vo.Wf00102VO;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 005 service.
 */
@Slf4j
@Service
public class Wf001Service extends BaseService {

    @Autowired
    private WfJobRepository wfJobRepository;

    @Autowired
    private WfParameterRepository wfParameterRepository;

    @Autowired
    private WfWorkflowJobRepository wfWorkflowJobRepository;

    @Inject
    private Wf001Mapper wf001Mapper;

    /**********************************************************************************************
     * JOB 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 Job 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page page
     */
    public Page<Wf00101VO> findAllJob(Pageable pageable, RequestParams<Wf00101VO> requestParams) {

        Wf00101VO wf00101VO = new Wf00101VO();
        wf00101VO.setJobName(requestParams.getString("jobName"));
        wf00101VO.setApi(requestParams.getString("api"));
        wf00101VO.setUseYn(requestParams.getString("useYn"));

        return filter(wf001Mapper.findAllJob(wf00101VO), pageable, "", Wf00101VO.class);
    }

    /**
     * JOB 정보 저장
     *
     * @param wf00101VOList the ac 00501 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveJob(List<Wf00101VO> wf00101VOList) {
        List<WfJob> wfJobList = ModelMapperUtils.mapList(wf00101VOList, WfJob.class);
        WfJob orgWfJob = null;

        for (WfJob wfJob : wfJobList) {
            orgWfJob = wfJobRepository.findOne(wfJob.getId());

            if (orgWfJob == null) {
                // created
                //wfJob.setJobUuid(UUIDUtils.getUUID());
                wfJobRepository.save(wfJob);
            } else {
                if (wfJob.isDeleted()) {
                    wfJobRepository.delete(wfJob);

                    // job 이 삭제되면 관련 parameter 삭제
                    QWfParameter qWfParameter = QWfParameter.wfParameter;
                    Predicate predicate = qWfParameter.jobUuid.eq(wfJob.getJobUuid());
                    wfParameterRepository.delete(wfParameterRepository.findAll(predicate));

                    // job 이 삭제되면 관련 workflow job 삭제
                    QWfWorkflowJob qWfWorkflowJob = QWfWorkflowJob.wfWorkflowJob;
                    Predicate predicate1 = qWfWorkflowJob.jobUuid.eq(wfJob.getJobUuid());
                    wfWorkflowJobRepository.delete(wfWorkflowJobRepository.findAll(predicate1));
                } else {
                    wfJob.setInsertDate(orgWfJob.getInsertDate());
                    wfJob.setInsertUuid(orgWfJob.getInsertUuid());

                    wfJobRepository.save(wfJob);
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
    public Page<Wf00102VO> findParameter(Pageable pageable, RequestParams<Wf00102VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(wf001Mapper.findParameter(requestParams.getString("jobUuid")), pageable, filter, Wf00102VO.class);
    }

    /**
     * 파라미터 정보 저장
     *
     * @param wf00102VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveParameter(List<Wf00102VO> wf00102VOList) {
        List<WfParameter> wfParameterList = ModelMapperUtils.mapList(wf00102VOList, WfParameter.class);
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