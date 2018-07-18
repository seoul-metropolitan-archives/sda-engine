/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.sp.sp001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.sp.sp001.vo.Sp00101VO;
import rmsoft.ams.seoul.sp.sp001.vo.Sp00102VO;

import java.util.List;

/**
 * Sp001Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Sp001Mapper extends MyBatisMapper {
    /**
     * Find all role list.
     *
     * @param ac00501VO the ac 00501 vo
     * @return the list
     */
    List<Sp00101VO> findAllRole(Sp00101VO ac00501VO);

    /**
     * Find role permission list.
     *
     * @param roleUuid the role uuid
     * @return the list
     */
    List<Sp00102VO> findRolePermission(String roleUuid);
}