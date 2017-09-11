package com.bgf.shbank.domain.mng.common;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchTerminalVO extends BaseVO {

    private String jisaCode;

    private String jisaName;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String terminalNo;
}