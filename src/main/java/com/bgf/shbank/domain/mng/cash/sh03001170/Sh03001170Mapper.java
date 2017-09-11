package com.bgf.shbank.domain.mng.cash.sh03001170;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001170Mapper extends MyBatisMapper {

    List<Sh03001170> findAll(Sh03001170 sh03001170);

    Sh03001170 findOne(Sh03001170 sh03001170);

    int update(Sh03001170 sh03001170);

    int delete(Sh03001170 sh03001170);

    int insert(Sh03001170 sh03001170);
}