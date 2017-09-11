package com.bgf.shbank.domain.mng.equip.sh02001110;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001110Mapper extends MyBatisMapper {

    List<Sh02001110> findAll();

    Sh02001110 findOne(Sh02001110 sh02001110);

    int update(Sh02001110 sh02001110);

    int delete(Sh02001110 sh02001110);

    int insert(Sh02001110 sh02001110);
}