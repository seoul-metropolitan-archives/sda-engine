/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac005.vo.Ac00501VO;
import rmsoft.ams.seoul.ac.ac005.vo.Ac00502VO;

import java.util.List;

/**
 * Ac005Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac005Mapper extends MyBatisMapper {
    List<Ac00501VO> findAllRole(Ac00501VO ac00501VO);

    List<Ac00502VO> findRolePermission(String roleUuid);
}