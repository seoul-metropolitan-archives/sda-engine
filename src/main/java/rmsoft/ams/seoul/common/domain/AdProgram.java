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
@Table(name = "AD_PROGRAM")
@IdClass(AdProgram.AdProgramId.class)
@Alias("AdProgram")
public class AdProgram extends BaseJpaModel<AdProgram.AdProgramId> {

    @Id
    @Column(name = "PROGRAM_UUID", length = 36, nullable = false)
    @Comment(value = "프로그램UUID")
    private String programUuid;

    @Column(name = "PROGRAM_ID", length = 20, nullable = false)
    @Comment(value = "프로그램ID")
    private String programId;

    @Column(name = "PROGRAM_NAME", length = 50, nullable = false)
    @Comment(value = "프로그램명")
    private String programName;

    @Column(name = "SERVICE_UUID", length = 36, nullable = false)
    @Comment(value = "서비스UUID")
    private String serviceUuid;

    @Column(name = "URL", length = 500, nullable = false)
    @Comment(value = "URL")
    private String url;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AdProgramId getId() {
        return AdProgramId.of(programUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdProgramId implements Serializable {

        @NonNull
        private String programUuid;
    }
}