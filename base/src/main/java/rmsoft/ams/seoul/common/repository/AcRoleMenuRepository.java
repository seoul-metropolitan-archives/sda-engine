/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AcRoleMenu;

/**
 * AcRoleMenuRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-26 오후 4:17
 */
@Repository
public interface AcRoleMenuRepository extends AXBootJPAQueryDSLRepository<AcRoleMenu, AcRoleMenu.AcRoleMenuId> {
}
