/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.ExExportHistory;

@Repository
public interface ExExportHistoryRepository extends AXBootJPAQueryDSLRepository<ExExportHistory, ExExportHistory.ExExportHistoryId> {

}
