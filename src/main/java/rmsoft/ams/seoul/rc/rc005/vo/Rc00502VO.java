package rmsoft.ams.seoul.rc.rc005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00502VO extends BaseVO {
    private String itemComponentUuid;

    private String itemUuid;

    private String componentUuid;

    private String publicationStatusUuid;

    private String areaNm;

    private String title;

    private String openStatusNm;

    private String electronYn;

    private String sourceSystemUuid;

    private int contentsSize;

    private String fileFormatUuid;

    private Blob thumbnail;

    private String typeNm;

    private String checksumTypeUuid;

    private String checksum;
}
