package com.bgf.shbank.domain.mng.error.sh01001160;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001160Mapper extends MyBatisMapper {

    List<Sh01001160> findAll();

    Sh01001160 findOne(Sh01001160 sh01001160);

    int update(Sh01001160 sh01001160);

    int delete(Sh01001160 sh01001160);

    int insert(Sh01001160 sh01001160);
}