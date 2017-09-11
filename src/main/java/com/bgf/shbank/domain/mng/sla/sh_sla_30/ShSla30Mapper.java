package com.bgf.shbank.domain.mng.sla.sh_sla_30;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla30Mapper extends MyBatisMapper {

    List<ShSla30> findAll();

    ShSla30 findOne(ShSla30 shSla30);

    int update(ShSla30 shSla30);

    int delete(ShSla30 shSla30);

    int insert(ShSla30 shSla30);
}