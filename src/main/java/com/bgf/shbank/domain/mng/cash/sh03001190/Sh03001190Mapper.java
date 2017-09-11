package com.bgf.shbank.domain.mng.cash.sh03001190;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001190Mapper extends MyBatisMapper {

    List<Sh03001190FormVO> findFormAmt(Sh03001190VO sh03001190VO);

    List<Sh03001190> findAll();

    Sh03001190 findOne(Sh03001190 sh03001190);

    int update(Sh03001190 sh03001190);

    int delete(Sh03001190 sh03001190);

    int insert(Sh03001190 sh03001190);
}