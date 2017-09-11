package com.bgf.shbank.domain.mng.cash.sh03001230;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001230Mapper extends MyBatisMapper {

    List<Sh03001230> findAll();

    Sh03001230 findOne(Sh03001230 sh03001230);

    int update(Sh03001230 sh03001230);

    int delete(Sh03001230 sh03001230);

    int insert(Sh03001230 sh03001230);
}