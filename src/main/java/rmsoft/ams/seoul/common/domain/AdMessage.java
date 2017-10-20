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

/**
 * AdMessage
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-20 오후 3:14
 **/
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_MESSAGE")
@IdClass(AdMessage.AdMessageId.class)
@Alias("AdMessage")
public class AdMessage extends BaseJpaModel<AdMessage.AdMessageId> {

    @Id
    @Column(name = "MESSAGE_UUID", length = 36, nullable = false)
    @Comment(value = "메세지UUID")
    private String messageUuid;

    @Column(name = "MESSAGE_CODE", length = 30, nullable = false)
    @Comment(value = "메세지 코드")
    private String messageCode;

    @Column(name = "MESSAGE_NAME", length = 100, nullable = false)
    @Comment(value = "메세지명")
    private String messageName;

    @Column(name = "SERVICE_UUID", length = 36, nullable = false)
    @Comment(value = "서비스 UUID")
    private String serviceUuid;

    @Column(name = "DB_ERROR_CODE", length = 20)
    @Comment(value = "DB 오류코드")
    private String dbErrorCode;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AdMessageId getId() {
        return AdMessageId.of(messageUuid);
    }


    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdMessageId implements Serializable {

        @NonNull
        private String messageUuid;
    }

}
