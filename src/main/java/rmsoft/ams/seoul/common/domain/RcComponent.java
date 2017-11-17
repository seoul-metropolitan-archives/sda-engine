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
import java.sql.Blob;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_COMPONENT")
@IdClass(RcComponent.RcComponentId.class)
@Alias("RcComponent")

public class RcComponent extends BaseJpaModel<RcComponent.RcComponentId> {

    @Id
    @Column(name = "COMPONENT_UUID", length = 36, nullable = false)
    @Comment(value = "COMPONENT UUID")
    private String componentUuid;

    @Column(name = "PUBLICATION_STATUS_UUID", length = 36, nullable = true)
    @Comment(value = "PUBLICATION STATUS UUID")
    private String publicationStatusUuid;

    @Column(name = "AREA_UUID", length = 36, nullable = true)
    @Comment(value = "AREA UUID")
    private String areaUuid;

    @Column(name = "TITLE", length = 36, nullable = true)
    @Comment(value = "TITLE")
    private String title;

    @Column(name = "TYPE_UUID", length = 36, nullable = true)
    @Comment(value = "TYPE UUID")
    private String typeUuid;

    @Column(name = "OPEN_STATUS_UUID", length = 36, nullable = true)
    @Comment(value = "OPEN STATUS UUID")
    private String openStatusUuid;

    @Column(name = "ELECTRON_YN", length = 36, nullable = true)
    @Comment(value = "ELECTRON YN")
    private String electronYN;

    @Column(name = "SOURCE_SYSTEM_UUID", length = 36, nullable = true)
    @Comment(value = "SOURCE SYSTEM UUID")
    private String sourceSystemUuid;

    @Column(name = "CONTENTS_SIZE", length = 36, nullable = true)
    @Comment(value = "CONTENTS SIZE")
    private int contentsSize;

    @Column(name = "FILE_FORMAT_UUID", length = 36, nullable = true)
    @Comment(value = "FILE FORMAT UUID")
    private String fileFormatUuid;

    @Column(name = "THUMBNAIL", length = 36, nullable = true)
    @Comment(value = "THUMBNAIL")
    private Blob thumbnail;

    @Column(name = "CHECKSUM_TYPE_UUID", length = 36, nullable = true)
    @Comment(value = "CHECKSUM TYPE UUID")
    private String checksumTypeUuid;

    @Column(name = "CHECKSUM", length = 36, nullable = true)
    @Comment(value = "CHECKSUM")
    private String checksum;

    @Column(name = "ORIGINAL_FILE_NAME", length = 100, nullable = true)
    @Comment(value = "ORIGINAL FILE NAME")
    private String originalFileName;

    @Column(name = "FILE_NAME", length = 100, nullable = true)
    @Comment(value = "FILE NAME")
    private String fileName;

    @Column(name = "FILE_PATH", length = 200, nullable = true)
    @Comment(value = "FILE PATH")
    private String filePath;

    @Override
    public RcComponentId getId() { return RcComponentId.of(componentUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcComponentId implements Serializable {
        @NonNull
        private String componentUuid;
    }
}
