package com.bgf.shbank.domain.mng.error.sh01001230;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001230Mapper extends MyBatisMapper {

    List<Sh01001230> findAll();

    Sh01001230 findOne(Sh01001230 sh01001230);

    int update(Sh01001230 sh01001230);

    int delete(Sh01001230 sh01001230);

    int insert(Sh01001230 sh01001230);
}