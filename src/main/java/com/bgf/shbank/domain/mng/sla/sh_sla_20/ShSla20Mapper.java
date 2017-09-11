package com.bgf.shbank.domain.mng.sla.sh_sla_20;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla20Mapper extends MyBatisMapper {

    List<ShSla20> findAll();

    ShSla20 findOne(ShSla20 shSla20);

    int update(ShSla20 shSla20);

    int delete(ShSla20 shSla20);

    int insert(ShSla20 shSla20);
}