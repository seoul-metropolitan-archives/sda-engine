package com.bgf.shbank.domain.mng.cash.sh03001130;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001130Mapper extends MyBatisMapper {

    List<Sh03001130> findAll(Sh03001130 sh03001130);

    Sh03001130 findOne(Sh03001130 sh03001130);

    int update(Sh03001130 sh03001130);

    int delete(Sh03001130 sh03001130);

    int insert(Sh03001130 sh03001130);
}