/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The type Ad entity type.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_NOTICE")
@IdClass(AdNotice.AdNoticeId.class)
@Alias("adNotice")
public class AdNotice extends SimpleJpaModel<AdNotice.AdNoticeId> {

    @Id
    @Column(name = "NOTICE_UUID", length = 36, nullable = false)
    private String noticeUuid;

    @Column(name = "TITLE", length = 4000, nullable = false)
    private String title;

    @Column(name = "REGISTER_UUID", length = 36)
    private String registerUuid;

    @Column(name = "REGISTER_DATE", length = 6)
    private Timestamp registerDate;

    @Column(name = "CONTENTS", length = 4000)
    private String contents;

    @Column(name = "FILE_NAME", length = 500)
    private String fileName;

    @Column(name = "FILE_PATH", length = 500)
    private String filePath;

    @Override
    public AdNoticeId getId() { return AdNoticeId.of(noticeUuid); }

    /**
     * The type Ad entity type id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdNoticeId implements Serializable {

        @NonNull
        private String noticeUuid;
    }
}