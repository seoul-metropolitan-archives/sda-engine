package com.bgf.shbank.domain.mng.equip.sh02001230;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001230Mapper extends MyBatisMapper {

    List<Sh02001230> findAll();

    Sh02001230 findOne(Sh02001230 sh02001230);

    int update(Sh02001230 sh02001230);

    int delete(Sh02001230 sh02001230);

    int insert(Sh02001230 sh02001230);
}