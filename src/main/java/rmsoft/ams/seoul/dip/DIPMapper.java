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
 * @since 2017-12-06 오후 2:00
 **/
public interface DIPMapper extends MyBatisMapper {
    List<Map<String, Object>> findAllEntityType();
    List<Map<String, Object>> findAllEntityTypeData(String entityTypeUuid);

    void updateSendHistory();
}