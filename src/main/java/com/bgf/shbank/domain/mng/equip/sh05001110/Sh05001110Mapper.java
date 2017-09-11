package com.bgf.shbank.domain.mng.equip.sh05001110;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh05001110Mapper extends MyBatisMapper {

    List<Sh05001110> findAll();

    Sh05001110 findOne(Sh05001110 sh05001110);

    int update(Sh05001110 sh05001110);

    int delete(Sh05001110 sh05001110);

    int insert(Sh05001110 sh05001110);
}