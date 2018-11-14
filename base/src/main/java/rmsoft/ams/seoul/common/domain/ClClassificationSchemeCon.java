/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Cl classification scheme con.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "CL_CLASSIFICATION_SCHEME_CON")
@IdClass(ClClassificationSchemeCon.ClClassificationSchemeId.class)
@Alias("ClClassificationSchemeCon")
public class ClClassificationSchemeCon extends SimpleJpaModel<ClClassificationSchemeCon.ClClassificationSchemeId> {
    @Id
    @Column(name = "CLASSIFICATION_SCHEME_UUID", length = 36, nullable = false)
    @Comment(value = "분류체계UUID")
    private String classificationSchemeUuid;

    @Column(name = "MANAGER_ORGANIZATION", length = 36)
    @Comment(value = "관리기관")
    private String managerOrganization;

    @Column(name = "MANAGER", length = 100)
    @Comment(value = "관리자")
    private String manager;

    @Column(name = "BASED_ON", length = 100)
    @Comment(value = "근거")
    private String basedOn;

    @Column(name = "ADD_METADATA01", length = 100 )
    @Comment(value = "추가메타데이터01")
    private String addMetadata01;

    @Column(name = "ADD_METADATA02", length = 100)
    @Comment(value = "추가메타데이터02")
    private String addMetadata02;

    @Column(name = "ADD_METADATA03", length = 100)
    @Comment(value = "추가메타데이터03")
    private String addMetadata03;

    @Column(name = "ADD_METADATA04", length = 100)
    @Comment(value = "추가메타데이터04")
    private String addMetadata04;

    @Column(name = "ADD_METADATA05", length = 100)
    @Comment(value = "추가메타데이터05")
    private String addMetadata05;

    @Column(name = "ADD_METADATA06", length = 100)
    @Comment(value = "추가메타데이터06")
    private String addMetadata06;

    @Column(name = "ADD_METADATA07", length = 100)
    @Comment(value = "추가메타데이터07")
    private String addMetadata07;

    @Column(name = "ADD_METADATA08", length = 100)
    @Comment(value = "추가메타데이터08")
    private String addMetadata08;

    @Column(name = "ADD_METADATA09", length = 100)
    @Comment(value = "추가메타데이터09")
    private String addMetadata09;

    @Column(name = "ADD_METADATA10", length = 100)
    @Comment(value = "추가메타데이터10")
    private String addMetadata10;

    @Column(name = "ADD_META_TEMPLATE_SET_UUID", length = 36)
    @Comment(value = "추가메타데이터탬플릿코드")
    private String addMetaTemplateSetUuid;

    @Override
    public ClClassificationSchemeId getId() { return ClClassificationSchemeId.of(classificationSchemeUuid); }

    /**
     * The type Cl classification scheme id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ClClassificationSchemeId implements Serializable {
        @NonNull
        private String classificationSchemeUuid;
    }
}
