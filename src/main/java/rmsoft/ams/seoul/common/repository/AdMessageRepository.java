/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AdMessage;

/**
 * AdMessageRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-20 오후 5:34
 */
@Repository
public interface AdMessageRepository extends AXBootJPAQueryDSLRepository<AdMessage, AdMessage.AdMessageId> {
}
