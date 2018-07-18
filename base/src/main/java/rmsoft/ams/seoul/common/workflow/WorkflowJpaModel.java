/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.db.type.LabelEnumType;
import io.onsemiro.core.db.type.MySQLJSONUserType;
import io.onsemiro.core.domain.base.AXBootCrudModel;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * SimpleJpaModel
 *
 * @param <PK> the type parameter
 * @author james
 * @version 1.0.0
 * @since 2017 -10-18 오전 10:49
 */
@TypeDefs({
        @TypeDef(name = "jsonNode", typeClass = MySQLJSONUserType.class, parameters = {@org.hibernate.annotations.Parameter(name = MySQLJSONUserType.CLASS, value = "com.fasterxml.jackson.databind.JsonNode")}),
        @TypeDef(name = "labelEnum", typeClass = LabelEnumType.class, parameters = {@org.hibernate.annotations.Parameter(name = MySQLJSONUserType.CLASS, value = "com.chequer.axboot.core.db.type.LabelEnumType")})
})
@Setter
@Getter
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
public abstract class WorkflowJpaModel<PK extends Serializable> extends AXBootCrudModel implements Persistable<PK>, Serializable {

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    /**
     * The Insert uuid.
     */
    @Column(name = "INSERT_UUID", length = 36, nullable = false)
    @Comment(value = "등록자UUID")
    protected String insertUuid;

    /**
     * The Insert date.
     */
    @Column(name = "INSERT_DATE", nullable = false)
    @Comment(value = "등록일시")
    protected Timestamp insertDate;

    /**
     * The Update uuid.
     */
    @Column(name = "UPDATE_UUID", length = 36, nullable = false)
    @Comment(value = "수정자UUID")
    protected String updateUuid;

    /**
     * The Update date.
     */
    @Column(name = "UPDATE_DATE", nullable = false)
    @Comment(value = "수정일시")
    protected Timestamp updateDate;

    /**
     * On persist.
     */
    @PrePersist
    protected void onPersist() {
        this.insertUuid = this.updateUuid = SessionUtils.getCurrentLoginUserUuid();
        this.insertDate = this.updateDate = Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
    }

    /**
     * On update.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updateDate = Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
    }

    /**
     * On post load.
     */
    @PostLoad
    protected void onPostLoad() {
    }

}
