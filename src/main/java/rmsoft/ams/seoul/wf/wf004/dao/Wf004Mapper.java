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
 * @since 2017-09-26 오후 3:44
 **/
public interface Wf004Mapper extends MyBatisMapper {
    List<Wf00401VO> findAllWorkflow(Wf00401VO wf00401VO);

    List<Wf00402VO> findWorkflowJob(String workflowUuid);

    List<Wf00403VO> findParameter(String jobUuid);
}