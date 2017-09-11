package com.bgf.shbank.domain.mng.etc.sh04001180;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh04001180Mapper extends MyBatisMapper {

    List<Sh04001180> findAll();

    Sh04001180 findOne(Sh04001180 sh04001180);

    int update(Sh04001180 sh04001180);

    int delete(Sh04001180 sh04001180);

    int insert(Sh04001180 sh04001180);
}