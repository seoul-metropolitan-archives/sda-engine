package com.bgf.shbank.domain.mng.equip.sh02001290;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001290Mapper extends MyBatisMapper {

    List<Sh02001290> findAll();

    Sh02001290 findOne(Sh02001290 sh02001290);

    int update(Sh02001290 sh02001290);

    int delete(Sh02001290 sh02001290);

    int insert(Sh02001290 sh02001290);
}