package com.bgf.shbank.domain.mng.equip.sh02001180;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001180Mapper extends MyBatisMapper {

    List<Sh02001180> findAll();

    Sh02001180 findOne(Sh02001180 sh02001180);

    int update(Sh02001180 sh02001180);

    int delete(Sh02001180 sh02001180);

    int insert(Sh02001180 sh02001180);
}