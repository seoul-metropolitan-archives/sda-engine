/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac008.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac008.vo.Ac00801VO;

import java.util.List;

/**
 * Ac008Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac008Mapper extends MyBatisMapper {
    List<Ac00801VO> findAllProgram(Ac00801VO ac00801VO);
}