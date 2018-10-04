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
 * @since 2017 -09-26 오후 3:44
 */
public interface Ac007Mapper extends MyBatisMapper {
    /**
     * Find all role list.
     *
     * @param ac00701VO the ac 00701 vo
     * @return the list
     */
    List<Ac00701VO> findAllRole(Ac00701VO ac00701VO);

    /**
     * Find role menu list.
     *
     * @param roleUuid the role uuid
     * @return the list
     */
    List<Ac00702VO> findRoleMenu(Ac00702VO ac00702VO);

    /**
     * Find permission list.
     *
     * @param programUuid the program uuid
     * @return the list
     */
    List<Ac00703VO> findPermission(Ac00702VO ac00702VO);
}