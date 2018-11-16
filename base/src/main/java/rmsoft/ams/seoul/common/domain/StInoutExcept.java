package rmsoft.ams.seoul.common.domain;

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
@Table(name = "ST_INOUT_EXCEPT")
@IdClass(StInoutExcept.StInoutExceptId.class)
@Alias("StInoutExcept")
public class StInoutExcept extends BaseJpaModel<StInoutExcept.StInoutExceptId> {


    @Id
    @Column(name = "INOUT_EXCEPT_UUID", length = 36, nullable = false)
    private String inoutExceptUuid;

    @Column(name = "REQUEST_NAME", length = 500, nullable = false)
    private String requestName;

    @Column(name = "REQUESTOR_UUID", length = 36, nullable = false)
    private String requestorUuid;

    @Column(name = "REQUEST_DATE", length = 36, nullable = false)
    private Timestamp requestDate;

    @Column(name = "EXCEPT_START_DATE", length = 36, nullable = false)
    private Timestamp exceptStartDate;

    @Column(name = "EXCEPT_END_DATE", length = 36, nullable = false)
    private Timestamp exceptEndDate;

    @Column(name = "EXCEPT_REASON", length = 4000)
    private String exceptReason;

    @Override
    public StInoutExceptId getId() {
        return StInoutExceptId.of(inoutExceptUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StInoutExceptId implements Serializable {
        @NonNull
        private String inoutExceptUuid;
    }
}
