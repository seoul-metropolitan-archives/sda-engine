package com.bgf.shbank.domain.mng.etc.sh04001190;

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
@Table(name = "ATMS_04001190")
@Comment("")
@Alias("sh04001190")
public class Sh04001190 extends SimpleJpaModel<Timestamp> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "NOTICE_CODE", length = 2, nullable = false)
	private String noticeCode;

	@Column(name = "NOTICE_CONTENT", length = 350)
	private String noticeContent;


    @Override
    public Timestamp getId() {
        return txId;
    }
}