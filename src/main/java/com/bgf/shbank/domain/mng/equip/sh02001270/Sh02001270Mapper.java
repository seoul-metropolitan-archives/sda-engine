package com.bgf.shbank.domain.mng.equip.sh02001270;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001270Mapper extends MyBatisMapper {

    List<Sh02001270> findAll();

    Sh02001270 findOne(Sh02001270 sh02001270);

    int update(Sh02001270 sh02001270);

    int delete(Sh02001270 sh02001270);

    int insert(Sh02001270 sh02001270);
}