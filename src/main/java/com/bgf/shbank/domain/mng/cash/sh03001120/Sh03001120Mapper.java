package com.bgf.shbank.domain.mng.cash.sh03001120;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001120Mapper extends MyBatisMapper {

    List<Sh03001120> findAll(Sh03001120 sh03001120);

    Sh03001120 findOne(Sh03001120 sh03001120);

    int update(Sh03001120 sh03001120);

    int delete(Sh03001120 sh03001120);

    int insert(Sh03001120 sh03001120);
}