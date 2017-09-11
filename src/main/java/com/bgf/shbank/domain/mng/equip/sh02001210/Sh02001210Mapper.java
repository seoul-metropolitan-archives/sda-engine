package com.bgf.shbank.domain.mng.equip.sh02001210;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001210Mapper extends MyBatisMapper {

    List<Sh02001210> findAll();

    Sh02001210 findOne(Sh02001210 sh02001210);

    int update(Sh02001210 sh02001210);

    int delete(Sh02001210 sh02001210);

    int insert(Sh02001210 sh02001210);
}