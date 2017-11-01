/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.WfWorkflowJob;

/**
 * WfWorkflowJobRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017-11-01 오후 3:59
 **/
@Repository
public interface WfWorkflowJobRepository extends AXBootJPAQueryDSLRepository<WfWorkflowJob, WfWorkflowJob.WfWorkflowJobId> {
}
