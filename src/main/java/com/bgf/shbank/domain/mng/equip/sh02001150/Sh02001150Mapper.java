package com.bgf.shbank.domain.mng.equip.sh02001150;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001150Mapper extends MyBatisMapper {

    List<Sh02001150> findAll();

    Sh02001150 findOne(Sh02001150 sh02001150);

    int update(Sh02001150 sh02001150);

    int delete(Sh02001150 sh02001150);

    int insert(Sh02001150 sh02001150);
}