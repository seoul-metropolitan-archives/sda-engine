package com.bgf.shbank.domain.mng.error.sh01001120;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001120Mapper extends MyBatisMapper {

    List<Sh01001120> findAll();

    Sh01001120 findOne(Sh01001120 sh01001120);

    int update(Sh01001120 sh01001120);

    int delete(Sh01001120 sh01001120);

    int insert(Sh01001120 sh01001120);
}