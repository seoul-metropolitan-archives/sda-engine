package com.bgf.shbank.domain.mng.etc.sh04001170;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001170Mapper extends MyBatisMapper {

    List<Sh04001170> findAll();

    Sh04001170 findOne(Sh04001170 sh04001170);

    int update(Sh04001170 sh04001170);

    int delete(Sh04001170 sh04001170);

    int insert(Sh04001170 sh04001170);
}