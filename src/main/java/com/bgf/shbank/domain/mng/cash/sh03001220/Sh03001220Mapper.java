package com.bgf.shbank.domain.mng.cash.sh03001220;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh03001220Mapper extends MyBatisMapper {

    List<Sh03001220> findAll(Sh03001220 sh03001220);

    Sh03001220 findOne(Sh03001220 sh03001220);

    int update(Sh03001220 sh03001220);

    int delete(Sh03001220 sh03001220);

    int insert(Sh03001220 sh03001220);
}