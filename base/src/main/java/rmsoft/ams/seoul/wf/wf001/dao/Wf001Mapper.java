/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.wf.wf001.vo.Wf00101VO;
import rmsoft.ams.seoul.wf.wf001.vo.Wf00102VO;

import java.util.List;

/**
 * Wf001Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Wf001Mapper extends MyBatisMapper {
    /**
     * Find all job list.
     *
     * @param wf00101VO the wf 00101 vo
     * @return the list
     */
    List<Wf00101VO> findAllJob(Wf00101VO wf00101VO);

    /**
     * Find parameter list.
     *
     * @param jobUuid the job uuid
     * @return the list
     */
    List<Wf00102VO> findParameter(String jobUuid);
}