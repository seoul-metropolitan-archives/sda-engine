/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99901VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99902VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99903VO;

import java.util.List;

/**
 * Wf999Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Wf999Mapper extends MyBatisMapper {
    /**
     * Find all workflow result list.
     *
     * @param Wf99901VO the wf 00401 vo
     * @return the list
     */
    List<Wf99901VO> findAllWorkflowResult(Wf99901VO Wf99901VO);

    /**
     * Find workflow job result list.
     *
     * @param workflowResultUuid the workflow result uuid
     * @return the list
     */
    List<Wf99902VO> findWorkflowJobResult(String workflowResultUuid);

    /**
     * Find parameter result list.
     *
     * @param jobResultUuid the job result uuid
     * @return the list
     */
    List<Wf99903VO> findParameterResult(String jobResultUuid);
}