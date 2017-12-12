package rmsoft.ams.seoul.common.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.common.domain.RcComponent;
import rmsoft.ams.seoul.common.service.CommonService;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Common controller.
 */
@RestController
@RequestMapping(value = "/api/v1/common")
public class CommonController extends BaseController {

    @Value("${streaming.url}")
    private String streamingUrl;
    @Value("${streaming.port}")
    private String streamingPort;
    @Value("${streaming.context}")
    private String streamingContext;
    @Value("${streaming.param}")
    private String streamingParam;
    @Value("${streaming.view}")
    private String streamingView;

    @Autowired
    private CommonService commonService;

    @Autowired
    private Ad001Service ad001Service;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    @RequestMapping("/getStreamingUrl")
    @ResponseBody
    public Object getStreamingId(@RequestBody Map<String,String> param) {
        String responseSB = "";
        try
        {

            RcComponent rcComponent = commonService.getComponentData(param);

            Ad00101VO ad00101VO = ModelMapperUtils.map(param,Ad00101VO.class);

            ad00101VO.setConfigurationCode("SYS_STORAGE");
            List<Ad00101VO> ad00101VOList = ad001Service.getEnviromentList(ad00101VO);
            String prefix = ad00101VOList.get(0).getConfigurationValue();
            //System.out.println(streamingUrl+":"+streamingPort+streamingContext+streamingParam+prefix+"/" +path+rcComponent.getFileName());
            String path = rcComponent.getFilePath().replaceAll("\\\\\\\\","/");
            if(path.indexOf(path.length()) != '/')
                path += "/";


            URL url = new URL(
            streamingUrl+":"+streamingPort+streamingContext+streamingParam+prefix+"/" +rcComponent.getFilePath().replaceAll("\\\\\\\\","/")+"/"+rcComponent.getFileName()
            );

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            // Write data
            // Read response

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            String line;
            while ((line = br.readLine()) != null)
                responseSB += line;

            // Close streams
            br.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        JSONObject obj = new JSONObject(responseSB);

        Map<String,Object> response = new HashMap<String,Object>();
        response.put("url", streamingUrl+":"+streamingPort+streamingContext+streamingView+obj.getString("streamdocsId")+";currentPage=1");
        //System.out.println(streamingUrl+":"+streamingPort+streamingContext+streamingView+obj.getString("streamdocsId")+";currentPage=1");
        return response;
    }
}
