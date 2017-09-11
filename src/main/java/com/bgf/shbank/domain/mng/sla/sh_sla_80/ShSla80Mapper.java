package com.bgf.shbank.domain.mng.sla.sh_sla_80;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla80Mapper extends MyBatisMapper {

    List<ShSla80> findAll();

    ShSla80 findOne(ShSla80 shSla80);

    int update(ShSla80 shSla80);

    int delete(ShSla80 shSla80);

    int insert(ShSla80 shSla80);
}