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
 * The type Ad code detail.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@IdClass(AdCodeDetail.AdCodeDetailId.class)
@Alias("Ad003_D")
@Table(name = "AD_CODE_DETAIL")
public class AdCodeDetail extends BaseJpaModel<AdCodeDetail.AdCodeDetailId> {

    @Id
    @Column(name = "CODE_DETAIL_UUID")
    @Comment(value = "코드 디테일 UUID")
    private String codeDetailUuid;
    @Column(name = "CODE_HEADER_UUID")
    @Comment(value = "코드 해더 UUID")
    private String codeHeaderUuid;
    @Column(name = "CODE")
    @Comment(value = "코드")
    private String code;
    @Column(name = "CODE_NAME")
    @Comment(value = "코드 명")
    private String codeName;
    @Column(name = "DEFAULT_YN")
    @Comment(value = "기본 여부")
    private String defaultYN;
    @Column(name = "ORDER_NO")
    @Comment(value = "정렬순서")
    private String orderNO;
    @Column(name = "DESCRIPTION")
    @Comment(value = "설명")
    private String description;
    @Column(name = "NOTES")
    @Comment(value = "비고")
    private String notes;
    @Column(name = "USE_YN")
    @Comment(value = "사용여부")
    private String useYN;
    @Column(name = "ATTRIBUTE01")
    @Comment(value = "속성01")
    private String attribute01;
    @Column(name = "ATTRIBUTE02")
    @Comment(value = "속성02")
    private String attribute02;
    @Column(name = "ATTRIBUTE03")
    @Comment(value = "속성03")
    private String attribute03;
    @Column(name = "ATTRIBUTE04")
    @Comment(value = "속성04")
    private String attribute04;
    @Column(name = "ATTRIBUTE05")
    @Comment(value = "속성05")
    private String attribute05;
    @Column(name = "ATTRIBUTE06")
    @Comment(value = "속성06")
    private String attribute06;
    @Column(name = "ATTRIBUTE07")
    @Comment(value = "속성07")
    private String attribute07;
    @Column(name = "ATTRIBUTE08")
    @Comment(value = "속성08")
    private String attribute08;
    @Column(name = "ATTRIBUTE09")
    @Comment(value = "속성09")
    private String attribute09;
    @Column(name = "ATTRIBUTE10")
    @Comment(value = "속성10")
    private String attribute10;

    @Override
    public AdCodeDetailId getId() {
        return AdCodeDetailId.of(codeDetailUuid);
    }

    /**
     * The type Ad code detail id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdCodeDetailId implements Serializable {
        @NonNull
        private String codeDetailUuid;
    }
}
