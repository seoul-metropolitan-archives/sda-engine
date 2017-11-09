/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
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
 * @since 2017-09-26 오후 3:44
 **/
public interface Wf003Mapper extends MyBatisMapper {
    List<Wf00301VO> findAllWorkflow(Wf00301VO wf00301VO);

    List<Wf00302VO> findWorkflowJob(String workflowUuid);

    List<Wf00301_P0101VO> findAllJob(Wf00301_P0101VO wf00301_p0101VO);

    List<Wf00301_P0102VO> findParameter(String jobUuid);
}