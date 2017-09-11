package com.bgf.shbank.domain.mng.equip.sh02001190;

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
@Table(name = "ATMS_02001190")
@Comment("")
@Alias("sh02001190")
public class Sh02001190 extends SimpleJpaModel<Timestamp> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "CHANGE_BEFORE_FAC_GUBUN_CODE", length = 2)
	private String changeBeforeFacGubunCode;

	@Column(name = "CHANGE_BEFORE_FAC_CODE", length = 4)
	private String changeBeforeFacCode;

	@Column(name = "CHANGE_AFTER_FAC_GUBUN_CODE", length = 2)
	private String changeAfterFacGubunCode;

	@Column(name = "CHANGE_AFTER_FAC_CODE", length = 4)
	private String changeAfterFacCode;

	@Column(name = "CHANGE_AFTER_FAC_NAME", length = 50)
	private String changeAfterFacName;

	@Column(name = "CHANGE_AFTER_CORP_NAME", length = 20)
	private String changeAfterCorpName;

	@Column(name = "CHANGE_AFTER_HIRE_FEE", length = 10)
	private String changeAfterHireFee;


    @Override
    public Timestamp getId() {
        return txId;
    }
}