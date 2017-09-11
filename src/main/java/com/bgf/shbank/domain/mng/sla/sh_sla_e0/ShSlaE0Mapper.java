package com.bgf.shbank.domain.mng.sla.sh_sla_e0;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSlaE0Mapper extends MyBatisMapper {

    List<ShSlaE0> findAll();

    ShSlaE0 findOne(ShSlaE0 shSlaE0);

    int update(ShSlaE0 shSlaE0);

    int delete(ShSlaE0 shSlaE0);

    int insert(ShSlaE0 shSlaE0);
}