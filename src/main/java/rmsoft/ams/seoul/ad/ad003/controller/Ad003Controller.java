package rmsoft.ams.seoul.ad.ad003.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad003.vo.Ad003VO_03;
import rmsoft.ams.seoul.ad.ad003.service.Ad003Service;

@RestController
@RequestMapping("/ad/ad003")
public class Ad003Controller
{

    @Autowired
    private Ad003Service service;

    @RequestMapping("/getCode")
    @ResponseBody
    public Object getCode(@RequestBody Ad003VO_03 param)
    {
        return service.getCode(param);
    }
}
