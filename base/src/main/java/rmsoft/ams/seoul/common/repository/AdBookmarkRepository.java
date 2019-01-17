/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AdBookmark;

/**
 * AcRoleRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-24 오후 1:24
 */
@Repository
public interface AdBookmarkRepository extends AXBootJPAQueryDSLRepository<AdBookmark, AdBookmark.AdBookMarkId> {
}
