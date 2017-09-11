package com.bgf.shbank.domain.mng.cash.sh03001200;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001200Mapper extends MyBatisMapper {

    List<Sh03001200> findAll();

    Sh03001200 findOne(Sh03001200 sh03001200);

    int update(Sh03001200 sh03001200);

    int delete(Sh03001200 sh03001200);

    int insert(Sh03001200 sh03001200);
}