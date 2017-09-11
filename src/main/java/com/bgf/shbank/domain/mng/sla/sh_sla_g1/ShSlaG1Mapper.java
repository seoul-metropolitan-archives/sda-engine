package com.bgf.shbank.domain.mng.sla.sh_sla_g1;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSlaG1Mapper extends MyBatisMapper {

    List<ShSlaG1> findAll();

    ShSlaG1 findOne(ShSlaG1 shSlaG1);

    int update(ShSlaG1 shSlaG1);

    int delete(ShSlaG1 shSlaG1);

    int insert(ShSlaG1 shSlaG1);
}