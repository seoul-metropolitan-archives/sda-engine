package rmsoft.ams.seoul.ad.ad004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad004.service.Ad004Service;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;

import java.util.List;

@RestController
@RequestMapping("/ad/ad004/ad004")
public class Ad004Controller
{

    @Autowired
    private Ad004Service service;

    @RequestMapping("/searchPopupHeader")
    @ResponseBody
    public Object searchPopupHeader(@RequestBody Ad00401VO param)
    {
        return service.searchPopupHeader(param);
    }
    @RequestMapping("/getPopupDetail")
    @ResponseBody
    public Object getPopupDetail(@RequestBody Ad00402VO param)
    {
        return service.getPopupDetail(param);
    }

    @RequestMapping("/insertPopupHeader")
    @ResponseBody
    public Object insertPopupHeader(@RequestBody List<Ad00401VO> data)
    {
        return service.insertPopupHeader(data);
    }
    @RequestMapping("/insertPopupSQL")
    @ResponseBody
    public Object insertPopupSQL(@RequestBody Ad00401VO data)
    {
        return service.insertPopupSQL(data);
    }
    @RequestMapping("/insertPopupDetail")
    @ResponseBody
    public Object insertPopupDetail(@RequestBody List<Ad00402VO> list)
    {
        return service.insertPopupDetail(list);
    }
}
