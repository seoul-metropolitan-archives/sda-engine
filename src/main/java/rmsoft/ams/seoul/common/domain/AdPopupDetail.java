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

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@IdClass(AdPopupDetail.AdPopupDetailId.class)
@Alias("Ad004_D")
@Table(name = "AD_POPUP_DETAIL")
public class AdPopupDetail extends SimpleJpaModel<AdPopupDetail.AdPopupDetailId>
{
    @Id
    @Column(name="POPUP_DETAIL_UUID")
    @Comment(value="팝업 디테일 UUID")
    private String popupDetailUUID = "";

    @Column(name="POPUP_HEADER_UUID")
    @Comment(value="팝업 헤더 UUID")
    private String popupHeaderUUID = "";

    @Column(name="SQL_COLUMN")
    @Comment(value="SQL 컬럼 명")
    private String sqlColumn = "";

    @Column(name="TITLE")
    @Comment(value="제목")
    private String title = "";

    @Column(name="WIDTH")
    @Comment(value="넓이")
    private String width  = "";

    @Column(name="INPUT_METHOD_UUID")
    @Comment(value="입력 메소드 UUID")
    private String inputMethodUUID  = "";

    @Column(name="ALIGN_UUID")
    @Comment(value="텍스트 정렬 UUID")
    private String alignUUID  = "";

    @Column(name="TREE_COLUMN_YN")
    @Comment(value="트리 여부")
    private String treeColumnYN = "";

    @Column(name="TREE_RELATION_UUID")
    @Comment(value="트리 관계 UUID")
    private String treeRelationUUID = "";

    @Column(name="ORDER_NO")
    @Comment(value="순번")
    private String orderNO = "";


    @Override
    public AdPopupDetailId getId() {
        return AdPopupDetailId.of(popupDetailUUID);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdPopupDetailId implements Serializable
    {
        @NonNull
        private String popupDetailUUID;
    }
}
