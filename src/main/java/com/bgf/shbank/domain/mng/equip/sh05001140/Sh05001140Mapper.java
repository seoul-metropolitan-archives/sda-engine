package com.bgf.shbank.domain.mng.equip.sh05001140;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh05001140Mapper extends MyBatisMapper {

    List<Sh05001140> findAll();

    Sh05001140 findOne(Sh05001140 sh05001140);

    int update(Sh05001140 sh05001140);

    int delete(Sh05001140 sh05001140);

    int insert(Sh05001140 sh05001140);
}