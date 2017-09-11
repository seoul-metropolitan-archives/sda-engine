package com.bgf.shbank.domain.mng.equip.sh02001220;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001220Mapper extends MyBatisMapper {

    List<Sh02001220> findAll();

    Sh02001220 findOne(Sh02001220 sh02001220);

    int update(Sh02001220 sh02001220);

    int delete(Sh02001220 sh02001220);

    int insert(Sh02001220 sh02001220);
}