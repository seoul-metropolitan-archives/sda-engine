/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.dip;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;
import java.util.Map;

/**
 * DIPMapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -12-06 오후 2:00
 */
public interface DIPMapper extends MyBatisMapper {
    /**
     * Find all entity type list.
     *
     * @return the list
     */
    List<Map<String, Object>> findAllEntityType();

    /**
     * Find all entity type data list.
     *
     * @param entityTypeUuid the entity type uuid
     * @return the list
     */
    List<Map<String, Object>> findAllEntityTypeData(String entityTypeUuid);

    /**
     * Update send history.
     */
    void updateSendHistory();
}