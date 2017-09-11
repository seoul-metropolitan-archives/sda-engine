package com.bgf.shbank.domain.mng.equip.corner_status;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface CornerStatusMapper extends MyBatisMapper {

    List<CornerStatus> findAll();

    CornerStatus findOne(CornerStatus cornerStatus);

    int update(CornerStatus cornerStatus);

    int delete(CornerStatus cornerStatus);

    int insert(CornerStatus cornerStatus);
}