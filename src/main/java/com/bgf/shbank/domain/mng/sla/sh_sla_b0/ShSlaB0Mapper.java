package com.bgf.shbank.domain.mng.sla.sh_sla_b0;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSlaB0Mapper extends MyBatisMapper {

    List<ShSlaB0> findAll();

    ShSlaB0 findOne(ShSlaB0 shSlaB0);

    int update(ShSlaB0 shSlaB0);

    int delete(ShSlaB0 shSlaB0);

    int insert(ShSlaB0 shSlaB0);
}