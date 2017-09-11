package com.bgf.shbank.domain.mng.cash.sh03001110;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001110Mapper extends MyBatisMapper {

//    List<Sh03001110> findAll();

    List<Sh03001110> findAll(Sh03001110 sh03001110);

    Sh03001110 findOne(Sh03001110 sh03001110);

    int update(Sh03001110 sh03001110);

    int delete(Sh03001110 sh03001110);

    int insert(Sh03001110 sh03001110);
}