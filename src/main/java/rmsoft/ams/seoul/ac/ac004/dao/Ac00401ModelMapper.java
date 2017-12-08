/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac004.dao;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00401VO;
import rmsoft.ams.seoul.common.domain.AcUserGroup;

/**
 * Ac004ModelMapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:36
 */
@Component
public class Ac00401ModelMapper extends CustomMapper<AcUserGroup, Ac00401VO> {

    @Override
    public void mapAtoB(AcUserGroup src, Ac00401VO dest, MappingContext context) {
        //TODO something
    }
}

