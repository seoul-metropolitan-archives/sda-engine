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
 * The type Ad popup header.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@IdClass(AdPopupHeader.AdPopupHeaderId.class)
@Alias("Ad004_H")
@Table(name = "AD_POPUP_HEADER")
public class AdPopupHeader extends BaseJpaModel<AdPopupHeader.AdPopupHeaderId>
{
    @Id
    @Column(name="POPUP_HEADER_UUID")
    @Comment(value="팝업 UUID")
    private String popupHeaderUuid;

    @Column(name="POPUP_CODE")
    @Comment(value="팝업 코드")
    private String popupCode;

    @Column(name="POPUP_NAME")
    @Comment(value="팝업 명")
    private String popupName;

    @Column(name="SERVICE_UUID")
    @Comment(value="서비스 UUID")
    private String serviceUuid;

    @Column(name="MULTISELECT_YN")
    @Comment(value="멀티 셀렉트 YN")
    private String multiselectYN;

    @Column(name="POPUP_SQL")
    @Comment(value="팝업 SQL")
    private String popupSQL;

    @Column(name="USE_YN")
    @Comment(value="사용여부")
    private String useYN;

    @Column(name="TREE_YN")
    @Comment(value="트리여부")
    private String treeYN;

    @Override
    public AdPopupHeaderId getId() {
        return AdPopupHeaderId.of(popupHeaderUuid);
    }


    /**
     * The type Ad popup header id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdPopupHeaderId implements Serializable
    {
        @NonNull
        private String popupHeaderUuid;
    }

}
