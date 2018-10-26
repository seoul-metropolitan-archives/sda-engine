/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.ArrayList;
import java.util.Map;

/**
 * Wf999Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:44
 */
public interface Wf999Mapper extends MyBatisMapper {
    /**
     * @return the list
     */
    ArrayList<Map> findAllAggregationInf();

    /**
     * @return the list
     */
    ArrayList<Map> findAllItemInf();

    /**
     * @return the list
     */
    ArrayList<Map> findAllComponentInf();
}