package com.bgf.shbank.domain.mng.equip.corner_manage;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 28/03/2017.
 */
@Component
public class CornerManageModelMapper extends CustomMapper<CornerManage, CornerManageVO> {

    @Override
    public void mapAtoB(CornerManage src, CornerManageVO dest, MappingContext context) {

        LocalDateTime operStartTime = src.getOperStartTime().toLocalDateTime();
        dest.setOperStartTime(DateUtils.convertToString(operStartTime, "HH:mm:ss"));

        LocalDateTime operEndTime = src.getOperEndTime().toLocalDateTime();
        dest.setOperEndTime(DateUtils.convertToString(operEndTime, "HH:mm:ss"));

    }
}