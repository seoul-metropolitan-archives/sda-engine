package com.bgf.shbank.domain.mng.error.sh01001190;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001190Mapper extends MyBatisMapper {

    List<Sh01001190> findAll();

    Sh01001190 findOne(Sh01001190 sh01001190);

    int update(Sh01001190 sh01001190);

    int delete(Sh01001190 sh01001190);

    int insert(Sh01001190 sh01001190);
}