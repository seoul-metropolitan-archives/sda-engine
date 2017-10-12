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
@IdClass(AdCodeHeader.AdCodeHeaderId.class)
@Alias("Ad003_H")
@Table(name = "AD_CODE_HEADER")
public class AdCodeHeader extends BaseJpaModel<AdCodeHeader.AdCodeHeaderId>
{
    @Id
    @Column(name="CODE_HEADER_UUID")
    @Comment(value="코드 해더 UUID")
    private String codeHeaderUUID;
    @Column(name="CATEGORY_CODE")
    @Comment(value="목록 코드")
    private String categoryCode;
    @Column(name="CATEGORY_NAME")
    @Comment(value="목록 명")
    private String categoryName;
    @Column(name="SERVICE_UUID")
    @Comment(value="서비스 UUID")
    private String serviceUUID;
    @Column(name="CODE_TYPE_UUID")
    @Comment(value="코드 유형 UUID")
    private String codeTypeUUID;
    @Column(name="ORDER_METHOD_UUID")
    @Comment(value="정렬방법 UUID")
    private String orderMethodUUID;
    @Column(name="DESCRIPTION")
    @Comment(value="설명")
    private String description;
    @Column(name="NOTES")
    @Comment(value="비고")
    private String notes;
    @Column(name="USE_YN")
    @Comment(value="사용여부")
    private String useYN;
    @Column(name="ATTRIBUTE01")
    @Comment(value="속성01")
    private String attribute01;
    @Column(name="ATTRIBUTE02")
    @Comment(value="속성02")
    private String attribute02;
    @Column(name="ATTRIBUTE03")
    @Comment(value="속성03")
    private String attribute03;
    @Column(name="ATTRIBUTE04")
    @Comment(value="속성04")
    private String attribute04;
    @Column(name="ATTRIBUTE05")
    @Comment(value="속성05")
    private String attribute05;
    @Column(name="ATTRIBUTE06")
    @Comment(value="속성06")
    private String attribute06;
    @Column(name="ATTRIBUTE07")
    @Comment(value="속성07")
    private String attribute07;
    @Column(name="ATTRIBUTE08")
    @Comment(value="속성08")
    private String attribute08;
    @Column(name="ATTRIBUTE09")
    @Comment(value="속성09")
    private String attribute09;
    @Column(name="ATTRIBUTE10")
    @Comment(value="속성10")
    private String attribute10;

    @Override
    public AdCodeHeaderId getId() {
        return AdCodeHeaderId.of(codeHeaderUUID);
    }


    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdCodeHeaderId implements Serializable
    {
        @NonNull
        private String codeHeaderUUID;
    }

}
