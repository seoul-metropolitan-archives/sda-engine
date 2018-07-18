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
 * The type Ad entity type.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_GLOSSARY")
@IdClass(AdGlossary.AdGlossaryId.class)
@Alias("AdGlossary")
public class AdGlossary extends BaseJpaModel<AdGlossary.AdGlossaryId> {

    @Id
    @Column(name = "GLOSSARY_UUID", length = 36, nullable = false)
    @Comment(value = "Glossary UUID")
    private String glossaryUuid;

    @Column(name = "TERM_CODE", length = 30, nullable = false)
    @Comment(value = "Term Code")
    private String termCode;

    @Column(name = "TERM_NAME", length = 50, nullable = false)
    @Comment(value = "Term Name")
    private String termName;

    @Column(name = "DATA_TYPE_UUID", length = 50, nullable = false)
    @Comment(value = "Data Type UUID")
    private String dataTypeUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYN;

    @Override
    public AdGlossaryId getId() {
        return AdGlossaryId.of(glossaryUuid);
    }

    /**
     * The type Ad entity type id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdGlossaryId implements Serializable {

        @NonNull
        private String glossaryUuid;
    }
}