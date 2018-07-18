/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AcUserGroupUser;

/**
 * AcUserGroupUserRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-13 오후 1:00
 */
@Repository
public interface AcUserGroupUserRepository extends AXBootJPAQueryDSLRepository<AcUserGroupUser, AcUserGroupUser.AcUserGroupUserId> {
}
