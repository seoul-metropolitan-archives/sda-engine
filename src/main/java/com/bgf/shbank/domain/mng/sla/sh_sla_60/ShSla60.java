package com.bgf.shbank.domain.mng.sla.sh_sla_60;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
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
@Table(name = "ATMS_SLA_60")
@Comment(value = "")
@IdClass(ShSla60.ShSla60Id.class)
@Alias("shSla60")
public class ShSla60 extends SimpleJpaModel<ShSla60.ShSla60Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "CORP_CODE", length = 2, nullable = false)
    private String corpCode;

    @Id
    @Column(name = "REG_DATETIME", nullable = false)
    private Timestamp regDatetime;

    @Column(name = "EQUALS", length = 10)
    private String equals;

    @Column(name = "ETC", length = 400)
    private String etc;


    @Override
    public ShSla60Id getId() {
        return ShSla60Id.of(txDate, corpCode, regDatetime);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSla60Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private String corpCode;

        @NonNull
        private Timestamp regDatetime;

    }
}