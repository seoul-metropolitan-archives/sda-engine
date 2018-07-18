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

/**
 * The type Rc level of description.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_LEVEL")
@IdClass(RcLevelOfDescription.RcLevelOfDescriptionId.class)
@Alias("RcLevelOfDescription")
public class RcLevelOfDescription extends BaseJpaModel<RcLevelOfDescription.RcLevelOfDescriptionId> {

    @Id
    @Column(name = "LEVEL_UUID", length = 36, nullable = false)
    @Comment(value = "LEVEL_UUID")
    private String levelUuid;

    @Column(name = "LEVEL_NO",  nullable = false)
    @Comment(value = "LEVLE_NO")
    private String levelNo;

    @Column(name = "LEVEL_NAME", length = 50, nullable = false)
    @Comment(value = "LEVEL_NAME")
    private String levelName;

    @Column(name = "SHORT_NAME", length = 50, nullable = false)
    @Comment(value = "SHORT_NAME")
    private String shortName;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYN;


    @Override
    public RcLevelOfDescriptionId getId() {
        return RcLevelOfDescriptionId.of(levelUuid);
    }

    /**
     * The type Rc level of description id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcLevelOfDescriptionId implements Serializable {

        @NonNull
        private String levelUuid;
    }
}