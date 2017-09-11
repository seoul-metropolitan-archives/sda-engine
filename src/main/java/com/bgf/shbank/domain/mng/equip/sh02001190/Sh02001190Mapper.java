package com.bgf.shbank.domain.mng.equip.sh02001190;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001190Mapper extends MyBatisMapper {

    List<Sh02001190> findAll();

    Sh02001190 findOne(Sh02001190 sh02001190);

    int update(Sh02001190 sh02001190);

    int delete(Sh02001190 sh02001190);

    int insert(Sh02001190 sh02001190);
}