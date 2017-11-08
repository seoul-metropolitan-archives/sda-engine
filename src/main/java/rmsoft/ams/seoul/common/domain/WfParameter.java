/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "WF_PARAMETER")
@IdClass(WfParameter.WfParameterId.class)
@Alias("WfParameter")
public class WfParameter extends BaseJpaModel<WfParameter.WfParameterId> {

    @Id
    @Column(name = "PARAMETER_UUID", length = 36, nullable = false)
    @Comment(value = "Parameter UUID")
    private String parameterUuid;

    @Column(name = "JOB_UUID", length = 36, nullable = false)
    @Comment(value = "Parameter UUID")
    private String jobUuid;

    @Column(name = "PARAMETER_NAME", length = 50, nullable = false)
    @Comment(value = "Parameter Name")
    private String parameterName;

    @Column(name = "INPUT_CODE_UUID", length = 36)
    @Comment(value = "Input Code UUID")
    private String inputCodeUuid;

    @Column(name = "INPUT_METHOD_UUID", length = 36, nullable = false)
    @Comment(value = "Input Method UUID")
    private String inputMethodUuid;

    @Column(name = "IN_OUT_UUID", length = 36, nullable = false)
    @Comment(value = "In Out UUID")
    private String inOutUuid;

    @Column(name = "DEFAULT_VALUE", length = 100)
    @Comment(value = "Default Value")
    private String defaultValue;

    @Column(name = "DISPLAY_YN", length = 1, nullable = false)
    @Comment(value = "Display")
    private String displayYn;

    @Column(name = "REQUIRED_YN", length = 1, nullable = false)
    @Comment(value = "Required")
    private String requiredYn;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public WfParameterId getId() {
        return WfParameterId.of(parameterUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfParameterId implements Serializable {

        @NonNull
        private String parameterUuid;
    }
}