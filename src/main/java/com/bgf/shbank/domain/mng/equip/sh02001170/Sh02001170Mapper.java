package com.bgf.shbank.domain.mng.equip.sh02001170;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001170Mapper extends MyBatisMapper {

    List<Sh02001170> findAll();

    Sh02001170 findOne(Sh02001170 sh02001170);

    int update(Sh02001170 sh02001170);

    int delete(Sh02001170 sh02001170);

    int insert(Sh02001170 sh02001170);
}