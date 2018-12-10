package rmsoft.ams.seoul.cl.cl001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00702VO;

import java.util.List;

/**
 * The type Cl 00102 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cl00102VO extends BaseVO {

    private String classificationSchemeUuid ;

    private String managerOrganization;

    private String manager;

    private String basedOn;

    private String useYes;

    private String useNo;

    private String aggregationCnt;

    private String itemCnt;

    private String addMetadata01;

    private String addMetadata02;

    private String addMetadata03;

    private String addMetadata04;

    private String addMetadata05;

    private String addMetadata06;

    private String addMetadata07;

    private String addMetadata08;

    private String addMetadata09;

    private String addMetadata10;

    private String addMetaTemplateSetUuid;
}
