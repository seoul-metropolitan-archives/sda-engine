package com.bgf.shbank.domain.mng.equip.corner_manage;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface CornerManageMapper extends MyBatisMapper {

    List<CornerManage> findAll();

    CornerManage findOne(CornerManage cornerManage);

    int update(CornerManage cornerManage);

    int delete(CornerManage cornerManage);

    int insert(CornerManage cornerManage);
}