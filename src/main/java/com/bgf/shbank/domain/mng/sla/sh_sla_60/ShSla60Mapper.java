package com.bgf.shbank.domain.mng.sla.sh_sla_60;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla60Mapper extends MyBatisMapper {

    List<ShSla60> findAll();

    ShSla60 findOne(ShSla60 shSla60);

    int update(ShSla60 shSla60);

    int delete(ShSla60 shSla60);

    int insert(ShSla60 shSla60);
}