package com.bgf.shbank.domain.mng.equip.overhaul_plan;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface OverhaulPlanMapper extends MyBatisMapper {

    List<OverhaulPlan> findAll();

    OverhaulPlan findOne(OverhaulPlan overhaulPlan);

    int update(OverhaulPlan overhaulPlan);

    int delete(OverhaulPlan overhaulPlan);

    int insert(OverhaulPlan overhaulPlan);
}