package com.bgf.shbank.domain.mng.cash.sh03001150;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001150Mapper extends MyBatisMapper {

    List<Sh03001150> findAll();

    Sh03001150 findOne(Sh03001150 sh03001150);

    Sh03001150 nextSeqNo(Sh03001150 sh03001150);

    int update(Sh03001150 sh03001150);

    int delete(Sh03001150 sh03001150);

    int insert(Sh03001150 sh03001150);
}