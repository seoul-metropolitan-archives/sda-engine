package com.bgf.shbank.domain.mng.sla.sh_sla_f0;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSlaF0Mapper extends MyBatisMapper {

    List<ShSlaF0> findAll();

    ShSlaF0 findOne(ShSlaF0 shSlaF0);

    int update(ShSlaF0 shSlaF0);

    int delete(ShSlaF0 shSlaF0);

    int insert(ShSlaF0 shSlaF0);
}