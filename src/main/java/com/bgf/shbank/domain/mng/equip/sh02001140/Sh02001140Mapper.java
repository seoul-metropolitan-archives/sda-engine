package com.bgf.shbank.domain.mng.equip.sh02001140;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001140Mapper extends MyBatisMapper {

    List<Sh02001140> findAll();

    Sh02001140 findOne(Sh02001140 sh02001140);

    int update(Sh02001140 sh02001140);

    int delete(Sh02001140 sh02001140);

    int insert(Sh02001140 sh02001140);
}