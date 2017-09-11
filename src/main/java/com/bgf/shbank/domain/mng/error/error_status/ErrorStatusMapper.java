package com.bgf.shbank.domain.mng.error.error_status;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface ErrorStatusMapper extends MyBatisMapper {

    List<ErrorStatus> findAll();

    List<ErrorStatus> findHistory(ErrorStatus errorStatus);

    ErrorStatus findOne(ErrorStatus errorStatus);

    int update(ErrorStatus errorStatus);

    int delete(ErrorStatus errorStatus);

    int insert(ErrorStatus errorStatus);
}