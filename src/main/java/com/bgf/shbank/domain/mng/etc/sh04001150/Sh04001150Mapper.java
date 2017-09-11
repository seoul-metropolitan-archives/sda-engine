package com.bgf.shbank.domain.mng.etc.sh04001150;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001150Mapper extends MyBatisMapper {

    List<Sh04001150> findAll();

    Sh04001150 findOne(Sh04001150 sh04001150);

    int update(Sh04001150 sh04001150);

    int delete(Sh04001150 sh04001150);

    int insert(Sh04001150 sh04001150);
}