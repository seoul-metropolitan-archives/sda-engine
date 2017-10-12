package rmsoft.ams.seoul.ad.ad001.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;

import java.util.HashMap;

@RestController
@RequestMapping("/ad/ad001")
public class Ad001Controller
{

    @Autowired
    @Qualifier("AD001ServiceImpl")
    private Ad001Service service;

    @RequestMapping("/getEnviromentList.do")
    @ResponseBody
    public Object getEnviromentList(Ad00101VO param)
    {
        return service.getEnviromentList(param);
    }
}
