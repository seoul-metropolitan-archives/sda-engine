package com.bgf.shbank.domain.mng.error.error_handle_mng;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ErrorHandleMngMapper extends MyBatisMapper {

    List<ErrorHandleMng> findAll();

    ErrorHandleMng findOne(ErrorHandleMng errorHandleMng);

    int update(ErrorHandleMng errorHandleMng);

    int delete(ErrorHandleMng errorHandleMng);

    int insert(ErrorHandleMng errorHandleMng);
}