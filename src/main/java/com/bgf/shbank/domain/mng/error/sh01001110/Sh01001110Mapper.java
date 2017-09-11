package com.bgf.shbank.domain.mng.error.sh01001110;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh01001110Mapper extends MyBatisMapper {

    List<Sh01001110> findAll(Sh01001110 sh01001110);

    Sh01001110 findOne(Sh01001110 sh01001110);

    int update(Sh01001110 sh01001110);

    int delete(Sh01001110 sh01001110);

    int insert(Sh01001110 sh01001110);
}