/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac006.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac006.vo.Ac00601VO;

import java.util.List;

/**
 * Ac006Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Ac006Mapper extends MyBatisMapper {
    /**
     * Find all permission list.
     *
     * @param ac00601VO the ac 00601 vo
     * @return the list
     */
    List<Ac00601VO> findAllPermission(Ac00601VO ac00601VO);
}