package com.bgf.shbank.domain.mng.etc.sh04001130;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001130Mapper extends MyBatisMapper {

    List<Sh04001130> findAll();

    Sh04001130 findOne(Sh04001130 sh04001130);

    int update(Sh04001130 sh04001130);

    int delete(Sh04001130 sh04001130);

    int insert(Sh04001130 sh04001130);
}