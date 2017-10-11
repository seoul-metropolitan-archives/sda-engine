package rmsoft.ams.seoul.ad.ad001.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.domain.Ad001;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;

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
    public Object getEnviromentList()
    {
        Ad001 param = new Ad001();

        HashMap<String,Object> response = new HashMap<String,Object>();
        HashMap<String,Object> header = new HashMap<String,Object>();
        HashMap<String,Object> body = new HashMap<String,Object>();
        response.put("header",header);
        response.put("body",body);

        try
        {
            header.put("result",true);
            body.put("list",service.getEnviromentList(param));
        }catch(Exception ex)
        {
            header.put("result",false);
        }

        return response;
    }
}
