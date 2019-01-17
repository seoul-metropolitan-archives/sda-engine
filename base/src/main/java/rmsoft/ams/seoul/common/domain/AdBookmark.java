/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Ad menu.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_BOOKMARK")
@IdClass(AdBookmark.AdBookMarkId.class)
@Alias("AdBookmark")
public class AdBookmark extends SimpleJpaModel<AdBookmark.AdBookMarkId> {

    @Id
    @Column(name = "BOOKMARK_UUID", length = 36, nullable = false)
    @Comment(value = "Bookmark UUID")
    private String bookmarkUuid;

    @Column(name = "USER_UUID", length = 36, nullable = false)
    @Comment(value = "User UUID")
    private String userUuid;

    @Column(name = "MENU_UUID", length = 36, nullable = false)
    @Comment(value = "Menu UUID")
    private String menuUuid;

    @Column(name = "MENU_NAME", length = 50, nullable = false)
    @Comment(value = "Menu Name")
    private String menuName;

    @Column(name = "URL", length = 500, nullable = false)
    @Comment(value = "URL")
    private String url;


    @Override
    public AdBookMarkId getId() {
        return AdBookMarkId.of(bookmarkUuid);
    }

    /**
     * The type Ad menu id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdBookMarkId implements Serializable {

        @NonNull
        private String bookmarkUuid;
    }
}