/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AcUserGroup;

/**
 * AcUserGroupRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-13 오후 12:59
 */
@Repository
public interface AcUserGroupRepository extends AXBootJPAQueryDSLRepository<AcUserGroup, AcUserGroup.AcUserGroupId> {
}
