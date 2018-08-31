package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Rc aggregation.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "IG_ACCESSION_RECORD_DETAIL")
@IdClass(IgAccessionRecordDetail.IgAccessionRecordDetailId.class)
@Alias("IgAccessionRecordDetail")
public class IgAccessionRecordDetail extends SimpleJpaModel<IgAccessionRecordDetail.IgAccessionRecordDetailId> {
    @Id
    @Column(name = "ACCESSION_RECORD_ETC_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String accessionRecordEtcUuid;

    @Column(name = "ACCESSION_RECORD_UUID", length = 36, nullable = true)
    @Comment(value = "ACCESSION_RECORD_UUID")
    private String accessionRecordUuid;

    @Column(name = "TYPE_UUID", length = 30, nullable = true)
    @Comment(value = "TYPE_UUID")
    private String typeUuid;

    @Column(name = "AUTHORITY_UUID", length = 500, nullable = true)
    @Comment(value = "AUTHORITY_UUID")
    private String authorityUuid;

    @Column(name = "NAME", length = 36, nullable = true)
    @Comment(value = "이름")
    private String name;

    @Column(name = "TEL", length = 50, nullable = true)
    @Comment(value = "연락처")
    private String tel;

    @Column(name = "ADDRESS", length = 500, nullable = true)
    @Comment(value = "주소")
    private String address;

    @Override
    public IgAccessionRecordDetailId getId() { return IgAccessionRecordDetailId.of(accessionRecordEtcUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class IgAccessionRecordDetailId implements Serializable {
        @NonNull
        private String accessionRecordEtcUuid;
    }
}
