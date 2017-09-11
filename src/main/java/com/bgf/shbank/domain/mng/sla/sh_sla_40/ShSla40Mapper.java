package com.bgf.shbank.domain.mng.sla.sh_sla_40;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla40Mapper extends MyBatisMapper {

    List<ShSla40> findAll();

    ShSla40 findOne(ShSla40 shSla40);

    int update(ShSla40 shSla40);

    int delete(ShSla40 shSla40);

    int insert(ShSla40 shSla40);
}