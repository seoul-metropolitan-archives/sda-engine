package com.bgf.shbank.domain.mng.etc.sh04001110;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ATMS_04001110")
@Comment("")
@Alias("sh04001110")
public class Sh04001110 extends SimpleJpaModel<Timestamp> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "REQ_STEXT_DATE", nullable = false)
	private Timestamp reqStextDate;

	@Column(name = "REQ_STEXT_SEQ_NO", length = 7)
	private String reqStextSeqNo;

	@Column(name = "MESSAGE", length = 100)
	private String message;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

    @Override
    public Timestamp getId() {
        return txId;
    }
}