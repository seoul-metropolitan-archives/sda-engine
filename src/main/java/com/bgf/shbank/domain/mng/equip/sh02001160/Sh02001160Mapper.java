package com.bgf.shbank.domain.mng.equip.sh02001160;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001160Mapper extends MyBatisMapper {

    List<Sh02001160> findAll();

    Sh02001160 findOne(Sh02001160 sh02001160);

    int update(Sh02001160 sh02001160);

    int delete(Sh02001160 sh02001160);

    int insert(Sh02001160 sh02001160);
}