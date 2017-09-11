package com.bgf.shbank.domain.mng.etc.sh04001200;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001200Mapper extends MyBatisMapper {

    List<Sh04001200> findAll();

    Sh04001200 findOne(Sh04001200 sh04001200);

    int update(Sh04001200 sh04001200);

    int delete(Sh04001200 sh04001200);

    int insert(Sh04001200 sh04001200);
}