/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac009.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac009.vo.Ac00901VO;

import java.util.List;

/**
 * Ac009Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac009Mapper extends MyBatisMapper {
    List<Ac00901VO> findAllMenu(Ac00901VO ac00901VO);
}