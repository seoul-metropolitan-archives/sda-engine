package com.bgf.shbank.domain.mng.error.sh01001180;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001180Mapper extends MyBatisMapper {

    List<Sh01001180> findAll();

    Sh01001180 findOne(Sh01001180 sh01001180);

    int update(Sh01001180 sh01001180);

    int delete(Sh01001180 sh01001180);

    int insert(Sh01001180 sh01001180);
}