/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac007.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00701VO;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00702VO;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00703VO;

import java.util.List;

/**
 * Ac007Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac007Mapper extends MyBatisMapper {
    List<Ac00701VO> findAllRole(Ac00701VO ac00701VO);

    List<Ac00702VO> findRoleMenu(String roleUuid);

    List<Ac00703VO> findPermission(String programUuid);
}