package com.bgf.shbank.domain.mng.equip.sh02001100;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh02001100ModelMapper extends CustomMapper<Sh02001100, Sh02001100VO> {

    @Override
    public void mapAtoB(Sh02001100 src, Sh02001100VO dest, MappingContext context) {

        LocalDateTime noticeDatetime = src.getNoticeDatetime().toLocalDateTime();
        dest.setNoticeDatetime(DateUtils.convertToString(noticeDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime taskApplyDate = src.getTaskApplyDate().toLocalDateTime();
        dest.setTaskApplyDate(DateUtils.convertToString(taskApplyDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime workDate = src.getWorkDate().toLocalDateTime();
        dest.setWorkDate(DateUtils.convertToString(workDate, "yyyy-MM-dd HH:mm:ss"));

    }
}
