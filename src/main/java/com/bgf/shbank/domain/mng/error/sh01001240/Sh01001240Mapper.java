package com.bgf.shbank.domain.mng.error.sh01001240;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001240Mapper extends MyBatisMapper {

    List<Sh01001240> findAll();

    Sh01001240 findOne(Sh01001240 sh01001240);

    int update(Sh01001240 sh01001240);

    int delete(Sh01001240 sh01001240);

    int insert(Sh01001240 sh01001240);
}