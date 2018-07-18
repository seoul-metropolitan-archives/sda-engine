/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00401VO;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00402VO;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00403VO;

import java.util.List;

/**
 * Ac004Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Ac004Mapper extends MyBatisMapper {
    /**
     * Find all group list.
     *
     * @param ac00401VO the ac 00401 vo
     * @return the list
     */
    List<Ac00401VO> findAllGroup(Ac00401VO ac00401VO);

    /**
     * Find user group user by user group uuid list.
     *
     * @param userGroupUuid the user group uuid
     * @return the list
     */
    List<Ac00402VO> findUserGroupUserByUserGroupUuid(String userGroupUuid);

    /**
     * Find user role list.
     *
     * @param ac00403VO the ac 00403 vo
     * @return the list
     */
    List<Ac00403VO> findUserRole(Ac00403VO ac00403VO);
}