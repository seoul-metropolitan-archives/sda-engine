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
 * The type Rc aggregation.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "IG_ACCESSION_RECORD")
@IdClass(IgAccessionRecord.IgAccessionRecordId.class)
@Alias("IgAccessionRecord")
public class IgAccessionRecord extends BaseJpaModel<IgAccessionRecord.IgAccessionRecordId> {
    @Id
    @Column(name = "ACCESSION_RECORD_UUID", length = 36, nullable = false)
    @Comment(value = "ACCESSION_RECORD_UUID")
    private String accessionRecordUuid;

    @Column(name = "ACCESSION_NO", length = 36, nullable = true)
    @Comment(value = "ACCESSION_NO")
    private String accessionNo;

    @Column(name = "ACQUISITION_DATE", length = 50, nullable = true)
    @Comment(value = "ACQUISITION_DATE")
    private String acquisitionDate;

    @Column(name = "ACQUISITION_SOURCE", length = 4000, nullable = true)
    @Comment(value = "ACQUISITION_SOURCE")
    private String acquisitionSource;

    @Column(name = "ACQUISITION_TYPE_UUID", length = 36, nullable = true)
    @Comment(value = "ACQUISITION_TYPE_UUID")
    private String acquisitionTypeUuid;

    @Column(name = "INPUT_CODE_UUID", length = 50, nullable = true)
    @Comment(value = "INPUT_CODE_UUID")
    private String inputCodeUuid;

    @Column(name = "TITLE", length = 4000, nullable = true)
    @Comment(value = "TITLE")
    private String title;

    @Override
    public IgAccessionRecord.IgAccessionRecordId getId() { return IgAccessionRecord.IgAccessionRecordId.of(accessionRecordUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class IgAccessionRecordId implements Serializable {
        @NonNull
        private String accessionRecordUuid;
    }
}
