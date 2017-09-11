package com.bgf.shbank.domain.mng.etc.sh04001110;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001110Mapper extends MyBatisMapper {

    List<Sh04001110> findAll();

    Sh04001110 findOne(Sh04001110 sh04001110);

    int update(Sh04001110 sh04001110);

    int delete(Sh04001110 sh04001110);

    int insert(Sh04001110 sh04001110);
}