package com.bgf.shbank.domain.mng.sla.sh_sla_50;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla50Mapper extends MyBatisMapper {

    List<ShSla50> findAll();

    ShSla50 findOne(ShSla50 shSla50);

    int update(ShSla50 shSla50);

    int delete(ShSla50 shSla50);

    int insert(ShSla50 shSla50);
}