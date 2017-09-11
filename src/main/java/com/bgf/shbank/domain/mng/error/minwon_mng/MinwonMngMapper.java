package com.bgf.shbank.domain.mng.error.minwon_mng;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface MinwonMngMapper extends MyBatisMapper {

    List<MinwonMng> findAll();

    MinwonMng findOne(MinwonMng minwonMng);

    int update(MinwonMng minwonMng);

    int delete(MinwonMng minwonMng);

    int insert(MinwonMng minwonMng);
}