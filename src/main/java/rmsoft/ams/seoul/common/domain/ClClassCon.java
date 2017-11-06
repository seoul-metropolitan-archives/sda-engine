/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "CL_CLASS_CON")
@IdClass(ClClassCon.ClClassConId.class)
@Alias("ClClassCon")
public class ClClassCon extends BaseJpaModel<ClClassCon.ClClassConId> {
    @Id
    @Column(name = "CLASS_UUID", length = 40, nullable = false)
    @Comment(value = "분류UUID")
    private String classUuid;

    @Column(name = "RECORD_SCHEDULE_UUID", length = 40)
    @Comment(value = "처분일정UUID")
    private String recordScheduleUuid;

    @Column(name = "ADD_METADATA01", length = 100)
    @Comment(value = "추가데이터01")
    private String addMetadata01;

    @Column(name = "ADD_METADATA02", length = 100)
    @Comment(value = "추가데이터02")
    private String addMetadata02;

    @Column(name = "ADD_METADATA03", length = 100)
    @Comment(value = "추가데이터03")
    private String addMetadata03;

    @Column(name = "ADD_METADATA04", length = 100)
    @Comment(value = "추가데이터04")
    private String addMetadata04;

    @Column(name = "ADD_METADATA05", length = 100)
    @Comment(value = "추가데이터05")
    private String addMetadata05;

    @Column(name = "ADD_METADATA06", length = 100)
    @Comment(value = "추가데이터06")
    private String addMetadata06;

    @Column(name = "ADD_METADATA07", length = 100)
    @Comment(value = "추가데이터07")
    private String addMetadata07;

    @Column(name = "ADD_METADATA08", length = 100)
    @Comment(value = "추가데이터08")
    private String addMetadata08;

    @Column(name = "ADD_METADATA09", length = 100)
    @Comment(value = "추가데이터09")
    private String addMetadata09;

    @Column(name = "ADD_METADATA10", length = 100)
    @Comment(value = "추가데이터10")
    private String addMetadata10;

    @Override
    public ClClassConId getId() { return ClClassConId.of(classUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ClClassConId implements Serializable {
        @NonNull
        private String classUuid;
    }
}
