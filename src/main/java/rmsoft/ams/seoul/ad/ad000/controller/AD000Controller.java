package rmsoft.ams.seoul.ad.ad000.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad000.service.Ad000Service;

import java.util.HashMap;

@RestController
@RequestMapping("/ad/ad000")
public class AD000Controller
{

    @Autowired
    private Ad000Service service;

    @RequestMapping("/getServiceList.do")
    @ResponseBody
    public Object searchPopupHeader()
    {
        HashMap<String,Object> response = new HashMap<String,Object>();
        HashMap<String,Object> header = new HashMap<String,Object>();
        HashMap<String,Object> body = new HashMap<String,Object>();
        response.put("header",header);
        response.put("body",body);

        try
        {
            header.put("result",true);
            body.put("list",service.getServiceList());
        }catch(Exception ex)
        {
            header.put("result",false);
        }

        return response;
    }
}
