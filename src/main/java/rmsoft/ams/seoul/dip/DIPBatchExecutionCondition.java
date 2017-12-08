/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.dip;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * DIPBatchExecutionCondition
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -12-06 오전 11:17
 */
public class DIPBatchExecutionCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        Environment env = context.getEnvironment();

        boolean enableBatchJob = env.getProperty("dip.process.batch.enable", Boolean.class);

        return enableBatchJob;
    }
}
