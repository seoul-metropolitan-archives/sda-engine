package com.bgf.shbank.domain.mng.etc.agent_mng;

import com.bgf.shbank.core.upload.AX5File;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class AgentMngVO extends BaseVO {

    private String txId;

    private String jisaCode;

    private String empRegno;

    private String empName;

    private String empEnable;

    private String empGubun;

    private String corpGubun;

    private String empPhoneNo;

    private String digitalSealUrl;
    private AX5File digitalSealUrlFile;

    private String digitalSignUrl;
    private AX5File digitalSignUrlFile;


    public static AgentMngVO of(AgentMng agentMng) {
        BoundMapperFacade<AgentMng, AgentMngVO> mapper =
                ModelMapperUtils.getMapper("AgentMng", AgentMngVO.class.getPackage().getName());

        return mapper.map(agentMng);
    }

    public static List<AgentMngVO> of(List<AgentMng> agentMngList) {
        return agentMngList.stream().map(agentMng -> of(agentMng)).collect(toList());
    }

    public static List<AgentMngVO> of(Page<AgentMng> agentMngPage) {
        return agentMngPage.getContent().stream().map(agentMng -> of(agentMng)).collect(toList());
    }
}