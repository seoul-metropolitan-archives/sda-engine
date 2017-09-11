package com.bgf.shbank.domain.mng.sla.sh_sla_70;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla70Mapper extends MyBatisMapper {

    List<ShSla70> findAll();

    ShSla70 findOne(ShSla70 shSla70);

    int update(ShSla70 shSla70);

    int delete(ShSla70 shSla70);

    int insert(ShSla70 shSla70);
}