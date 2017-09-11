package com.bgf.shbank.domain.mng.cash.sh03001160;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001160Mapper extends MyBatisMapper {

    List<Sh03001160> findAll();

    Sh03001160 findOne(Sh03001160 sh03001160);

    Sh03001160 nextSeqNo(Sh03001160 sh03001160);

    int update(Sh03001160 sh03001160);

    int delete(Sh03001160 sh03001160);

    int insert(Sh03001160 sh03001160);
}