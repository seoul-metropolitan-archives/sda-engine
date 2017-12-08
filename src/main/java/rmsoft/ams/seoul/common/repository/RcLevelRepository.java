/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcLevel;

/**
 * RcLevelRepository
 *
 * @author Hyukjun Lee
 * @version 1.0.0
 * @since 2017 -11-30 오전 12:23
 */
@Repository
public interface RcLevelRepository extends AXBootJPAQueryDSLRepository<RcLevel, RcLevel.RcLevelId> {
}
