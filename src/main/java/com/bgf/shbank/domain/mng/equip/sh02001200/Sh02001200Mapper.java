package com.bgf.shbank.domain.mng.equip.sh02001200;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001200Mapper extends MyBatisMapper {

    List<Sh02001200> findAll();

    Sh02001200 findOne(Sh02001200 sh02001200);

    int update(Sh02001200 sh02001200);

    int delete(Sh02001200 sh02001200);

    int insert(Sh02001200 sh02001200);
}