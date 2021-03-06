/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.wf.wf002.vo.Wf00201VO;
import rmsoft.ams.seoul.wf.wf002.vo.Wf00202VO;

import java.util.List;

/**
 * Wf002Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Wf002Mapper extends MyBatisMapper {
    /**
     * Find all workflow list.
     *
     * @param wf00201VO the wf 00201 vo
     * @return the list
     */
    List<Wf00201VO> findAllWorkflow(Wf00201VO wf00201VO);

    /**
     * Find workflow job list.
     *
     * @param workflowUuid the workflow uuid
     * @return the list
     */
    List<Wf00202VO> findWorkflowJob(String workflowUuid);
}