package com.bgf.shbank.domain.mng.cash.sh03001210;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001210Mapper extends MyBatisMapper {

    List<Sh03001210> findAll();

    Sh03001210 findOne(Sh03001210 sh03001210);

    int update(Sh03001210 sh03001210);

    int delete(Sh03001210 sh03001210);

    int insert(Sh03001210 sh03001210);
}