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
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_LEVEL")
@IdClass(RcLevel.RcLevelId.class)
@Alias("RcLevel")
public class RcLevel extends BaseJpaModel<RcLevel.RcLevelId> {

    @Id
    @Column(name = "LEVEL_UUID", length = 36, nullable = false)
    @Comment(value = "레벨 UUID")
    private String levelUuid;

    @Column(name = "LEVEL_NO")
    @Comment(value = "레벨 No")
    private int levelNo;

    @Column(name = "LEVEL_NAME", length = 50, nullable = false)
    @Comment(value = "레벨명")
    private String levelName;

    @Column(name = "SHORT_NAME", length = 50, nullable = false)
    @Comment(value = "축약어")
    private String shortName;

    @Override
    public RcLevelId getId() {
        return RcLevelId.of(levelUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcLevelId implements Serializable {

        @NonNull
        private String levelUuid;
    }
}