package com.bgf.shbank.domain.mng.equip.sh02001280;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface Sh02001280Mapper extends MyBatisMapper {

    List<Sh02001280> findAll();

    Sh02001280 findOne(Sh02001280 sh02001280);

    int update(Sh02001280 sh02001280);

    int delete(Sh02001280 sh02001280);

    int insert(Sh02001280 sh02001280);
}