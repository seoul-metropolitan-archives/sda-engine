package com.bgf.shbank.domain.mng.etc.sh04001190;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001190Mapper extends MyBatisMapper {

    List<Sh04001190> findAll();

    Sh04001190 findOne(Sh04001190 sh04001190);

    int update(Sh04001190 sh04001190);

    int delete(Sh04001190 sh04001190);

    int insert(Sh04001190 sh04001190);
}