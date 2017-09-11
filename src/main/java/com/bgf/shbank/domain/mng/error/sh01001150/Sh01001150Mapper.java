package com.bgf.shbank.domain.mng.error.sh01001150;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001150Mapper extends MyBatisMapper {

    List<Sh01001150> findAll();

    Sh01001150 findOne(Sh01001150 sh01001150);

    int update(Sh01001150 sh01001150);

    int delete(Sh01001150 sh01001150);

    int insert(Sh01001150 sh01001150);
}