/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.common.vo.BaseColumnVO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301_P0101VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301_P0102VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00302VO;

import java.util.List;

/**
 * Wf003Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Wf003Mapper extends MyBatisMapper {
    /**
     * Find all workflow list.
     *
     * @param wf00301VO the wf 00301 vo
     * @return the list
     */
    List<Wf00301VO> findAllWorkflow(Wf00301VO wf00301VO);

    /**
     * Find workflow job list.
     *
     * @param workflowUuid the workflow uuid
     * @return the list
     */
    List<Wf00302VO> findWorkflowJob(String workflowUuid);

    /**
     * Find all job list.
     *
     * @param wf00301_p0101VO the wf 00301 p 0101 vo
     * @return the list
     */
    List<Wf00301_P0101VO> findAllJob(Wf00301_P0101VO wf00301_p0101VO);

    /**
     * Find parameter list.
     *
     * @param jobUuid the job uuid
     * @return the list
     */
    List<Wf00301_P0102VO> findParameter(String jobUuid);

    /**
     * Gets column info.
     *
     * @param jobUuid the job uuid
     * @return the column info
     */
    List<BaseColumnVO> getColumnInfo(String jobUuid);

    /**
     * Gets batch id.
     *
     * @return the batch id
     */
    int getBatchId();
}