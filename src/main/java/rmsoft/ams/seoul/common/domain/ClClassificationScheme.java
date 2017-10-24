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
@Table(name = "CL_CLASSIFICATION_SCHEME")
@IdClass(ClClassificationScheme.ClClassificationSchemeId.class)
@Alias("ClClassificationScheme")
public class ClClassificationScheme extends BaseJpaModel<ClClassificationScheme.ClClassificationSchemeId> {
    @Id
    @Column(name = "CLASSIFICATION_SCHEME_UUID", length = 36, nullable = false)
    @Comment(value = "분류체계UUID")
    private String classificationSchemeUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "상태UUID")
    private String statusUuid;

    @Column(name = "CLASSIFICATION_CODE", length = 30, nullable = false)
    @Comment(value = "아이디")
    private String classificationCode;

    @Column(name = "CLASSIFICATION_NAME", length = 50, nullable = false)
    @Comment(value = "아이디")
    private String classificationName;

    @Column(name = "CLASSIFICATION_TYPE_UUID", length = 50, nullable = false)
    @Comment(value = "아이디")
    private String classificationTypeUuid;

    @Column(name = "ORDER_NO")
    @Comment(value = "주문번호")
    private String orderNo;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public ClClassificationSchemeId getId() { return ClClassificationSchemeId.of(classificationSchemeUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ClClassificationSchemeId implements Serializable {
        @NonNull
        private String classificationSchemeUuid;
    }
}
