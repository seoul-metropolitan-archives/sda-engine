package com.bgf.shbank.core.message;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by manasobi on 2017-02-26.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@IdClass(MsgSeq.MsgSeqId.class)
@Table(name = "ATMS_STEXT_SEQ")
public class MsgSeq extends SimpleJpaModel<MsgSeq.MsgSeqId> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Column(name = "SEQ", nullable = false)
    private int seq;

    @Column(name = "TARGET", nullable = false)
    private int target;

    @Override
    public MsgSeqId getId() {
        return MsgSeqId.of(txDate, seq);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class MsgSeqId implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private int target;

    }
}
