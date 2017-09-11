package com.bgf.shbank.domain.mng.equip.sh05001130;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh05001130Mapper extends MyBatisMapper {

    List<Sh05001130> findAll();

    Sh05001130 findOne(Sh05001130 sh05001130);

    int update(Sh05001130 sh05001130);

    int delete(Sh05001130 sh05001130);

    int insert(Sh05001130 sh05001130);
}