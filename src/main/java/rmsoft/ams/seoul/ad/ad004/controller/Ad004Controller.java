package rmsoft.ams.seoul.ad.ad004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad004.service.Ad004_DService;
import rmsoft.ams.seoul.ad.ad004.service.Ad004_HService;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;

import java.util.HashMap;

@RestController
@RequestMapping("/ad/ad004/ad004")
public class Ad004Controller
{

    @Autowired
    private Ad004_HService service_h;

    @Autowired
    private Ad004_DService service_d;


    @RequestMapping("/searchPopupHeader")
    @ResponseBody
    public Object searchPopupHeader(@RequestBody Ad00401VO param)
    {
        return service_h.searchPopupHeader(param);
    }
}
