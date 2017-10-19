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
@Table(name = "CL_CLASS")
@IdClass(ClClass.ClClassId.class)
@Alias("ClClass")
public class ClClass extends BaseJpaModel<ClClass.ClClassId> {
    @Id
    @Column(name = "CLASS_UUID", length = 40, nullable = false)
    @Comment(value = "분류UUID")
    private String classUuid;

    @Column(name = "CLASSIFICATION_SCHEME_UUID", length = 36, nullable = false)
    @Comment(value = "분류체계UUID")
    private String classificationSchemeUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "상태UUID")
    private String statusUuid;

    @Column(name = "PARENT_CLASS_UUID", length = 36)
    @Comment(value = "상위분류UUID")
    private String parentClassUuid;

    @Column(name = "CLASS_CODE", length = 30, nullable = false)
    @Comment(value = "분류코드")
    private String classCode;

    @Column(name = "CLASS_NAME", length = 50, nullable = false)
    @Comment(value = "분류명")
    private String className;

    @Column(name = "CLASS_LEVEL_UUID", length = 36, nullable = false)
    @Comment(value = "분류등급UUID")
    private String classLevelUuid;

    @Column(name = "ORDER_NO")
    @Comment(value = "주문번호")
    private String orderNo;

    @Column(name = "ORDER_KEY", length = 100)
    @Comment(value = "주문Key")
    private String orderKey;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public ClClassId getId() { return ClClassId.of(classUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ClClassId implements Serializable {
        @NonNull
        private String classUuid;
    }
}
