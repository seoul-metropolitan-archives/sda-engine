package com.bgf.shbank.domain.mng.etc.sh04001120;

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
@Table(name = "ATMS_04001120")
@Comment("")
@Alias("sh04001120")
public class Sh04001120 extends SimpleJpaModel<Timestamp> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "EMP_NAME", length = 20)
	private String empName;

	@Column(name = "EMP_REGNO", length = 13)
	private String empRegno;

	@Column(name = "EMP_ENABLE", length = 1)
	private String empEnable;

	@Column(name = "REQ_SEQ_NO", length = 7)
	private String reqSeqNo;

	@Column(name = "COOP_ORG_NO", length = 2)
	private String coopOrgNo;


    @Override
    public Timestamp getId() {
        return txId;
    }
}