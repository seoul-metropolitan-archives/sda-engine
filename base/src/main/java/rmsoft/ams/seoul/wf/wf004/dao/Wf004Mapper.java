/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00401VO;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00402VO;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00403VO;

import java.util.List;

/**
 * Wf004Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Wf004Mapper extends MyBatisMapper {
    /**
     * Find all workflow result list.
     *
     * @param wf00401VO the wf 00401 vo
     * @return the list
     */
    List<Wf00401VO> findAllWorkflowResult(Wf00401VO wf00401VO);

    /**
     * Find workflow job result list.
     *
     * @param workflowResultUuid the workflow result uuid
     * @return the list
     */
    List<Wf00402VO> findWorkflowJobResult(String workflowResultUuid);

    /**
     * Find parameter result list.
     *
     * @param jobResultUuid the job result uuid
     * @return the list
     */
    List<Wf00403VO> findParameterResult(String jobResultUuid);
}