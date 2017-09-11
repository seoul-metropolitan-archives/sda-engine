package com.bgf.shbank.domain.mng.sla.sh_sla_10;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ShSla10Mapper extends MyBatisMapper {

    List<ShSla10> findAll();

    ShSla10 findOne(ShSla10 shSla10);

    int update(ShSla10 shSla10);

    int delete(ShSla10 shSla10);

    int insert(ShSla10 shSla10);
}