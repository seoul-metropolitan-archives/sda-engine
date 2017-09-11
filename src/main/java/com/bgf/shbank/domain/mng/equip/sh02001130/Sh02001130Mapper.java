package com.bgf.shbank.domain.mng.equip.sh02001130;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001130Mapper extends MyBatisMapper {

    List<Sh02001130> findAll();

    Sh02001130 findOne(Sh02001130 sh02001130);

    int update(Sh02001130 sh02001130);

    int delete(Sh02001130 sh02001130);

    int insert(Sh02001130 sh02001130);
}