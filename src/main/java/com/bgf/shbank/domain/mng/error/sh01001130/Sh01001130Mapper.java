package com.bgf.shbank.domain.mng.error.sh01001130;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001130Mapper extends MyBatisMapper {

    List<Sh01001130> findAll();

    Sh01001130 findOne(Sh01001130 sh01001130);

    int update(Sh01001130 sh01001130);

    int delete(Sh01001130 sh01001130);

    int insert(Sh01001130 sh01001130);
}