package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
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
@Table(name = "RC_ITEM_CON")
@IdClass(RcItemCon.RcItemConId.class)
@Alias("RcItemCon")

public class RcItemCon extends SimpleJpaModel<RcItemCon.RcItemConId> {

    @Id
    @Column(name = "ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM UUID")
    private String itemUuid;

    @Column(name = "OPEN_STATUS_UUID", length = 36, nullable = true)
    @Comment(value = "OPEN STATUS UUID")
    private String openStatusUuid;

    @Column(name = "KEYWORD", length = 500, nullable = true)
    @Comment(value = "KEYWORD")
    private String keyword;

    @Column(name = "PROVENANCE", length = 500, nullable = true)
    @Comment(value = "PROVENANCE")
    private String provenance;

    @Column(name = "REFERENCE_CODE", length = 500, nullable = true)
    @Comment(value = "REFERENCE CODE")
    private String referenceCode;

    @Column(name = "CREATOR", length = 500, nullable = true)
    @Comment(value = "CREATOR")
    private String creator;

    @Column(name = "CREATION_START_DATE", length = 8, nullable = true)
    @Comment(value = "CREATION START DATE")
    private String creationStartDate;

    @Column(name = "CREATION_END_DATE", length = 100, nullable = true)
    @Comment(value = "CREATION END DATE")
    private String creationEndDate;

    @Column(name = "ADD_METADATA01", length = 100, nullable = true)
    @Comment(value = "ADD METADATA01")
    private String addMetadata01;

    @Column(name = "ADD_METADATA02", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata02;
    @Column(name = "ADD_METADATA03", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata03;
    @Column(name = "ADD_METADATA04", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata04;
    @Column(name = "ADD_METADATA05", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata05;
    @Column(name = "ADD_METADATA06", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata06;
    @Column(name = "ADD_METADATA07", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata07;
    @Column(name = "ADD_METADATA08", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata08;
    @Column(name = "ADD_METADATA09", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata09;
    @Column(name = "ADD_METADATA10", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata10;

    @Override
    public RcItemCon.RcItemConId getId() { return RcItemCon.RcItemConId.of(itemUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemConId implements Serializable {
        @NonNull
        private String itemUuid;
    }
}
