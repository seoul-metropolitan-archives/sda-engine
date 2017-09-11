package com.bgf.shbank.domain.mng.equip.sh02001120;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001120Mapper extends MyBatisMapper {

    List<Sh02001120> findAll();

    Sh02001120 findOne(Sh02001120 sh02001120);

    int update(Sh02001120 sh02001120);

    int delete(Sh02001120 sh02001120);

    int insert(Sh02001120 sh02001120);
}