package com.bgf.shbank.domain.mng.equip.facility_status;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface FacilityStatusMapper extends MyBatisMapper {

    List<FacilityStatus> findAll();

    FacilityStatus findOne(FacilityStatus facilityStatus);

    int update(FacilityStatus facilityStatus);

    int delete(FacilityStatus facilityStatus);

    int insert(FacilityStatus facilityStatus);
}