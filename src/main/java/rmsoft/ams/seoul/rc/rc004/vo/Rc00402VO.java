package rmsoft.ams.seoul.rc.rc004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Blob;

/**
 * The type Rc 00402 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00402VO extends BaseVO {

    private String componentUuid;

    private String publicationStatusUuid;

    private String areaUuid;

    private String title;

    private String typeUuid;

    private String openStatusUuid;

    private String electronYN;

    private String sourceSystemUuid;

    private int contentsSize;

    private String fileFormatUuid;

    private Blob thumbnail;

    private String checksumTypeUuid;

    private String checksum;

    private String originalFileName;

    private String fileName;

    private String filePath;
}
