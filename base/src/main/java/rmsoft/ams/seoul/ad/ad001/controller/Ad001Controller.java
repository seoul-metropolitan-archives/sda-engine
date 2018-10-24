package rmsoft.ams.seoul.ad.ad001.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.AppRunner;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.dip.DIPBatchExecutor;
import rmsoft.ams.seoul.utils.RuntimeJarLoader;

import java.util.List;


/**
 * The type Ad 001 controller.
 */
@RestController
@RequestMapping("/api/v1/ad/ad001")
public class Ad001Controller extends MessageBaseController {

    @Autowired
    @Qualifier("AD001ServiceImpl")
    private Ad001Service service;

    @Autowired
    private DIPBatchExecutor dipBatchExecutor;

    @Autowired
    private RestartEndpoint restartEndpoint;


    /**
     * Gets enviroment list.
     *
     * @param param the param
     * @return the enviroment list
     */
    @RequestMapping("/getEnviromentList.do")
    public Responses.ListResponse getEnviromentList(@RequestBody Ad00101VO param) {



        //dipBatchExecutor.runDipProcess()
        RuntimeJarLoader.loadJarIndDir("C:\\devSpace\\seoul-ams\\service-modules\\long-term-preservation\\build\\libs");
        //AppRunner.restart(restartEndpoint);
       // AppRunner.restart();



        //RuntimeJarLoader.unloadJarIndDir("/Users/jspark226/IdeaProjects/seoul-ams/service-modules/long-term-preservation/build/libs");

        ///Users/jspark226/IdeaProjects/seoul-ams/service-modules/long-term-preservation/build/libs

        /*String nativeLibrary = "/Users/jspark226/IdeaProjects/seoul-ams/service-modules/long-term-preservation/build/libs/";

        try {
            // Service Loader Test
            File[] jarFiles = new File(nativeLibrary).listFiles(
                    (dir, name) -> {
                        return name.toLowerCase().endsWith(".jar");
                    }
            );

            // Create a new JavaClassLoader
            URL[] classLoaderUrls = new URL[jarFiles.length];

            for (int i = 0; i < jarFiles.length; i++) {
                classLoaderUrls[i] = new URL("file:///" + jarFiles[i].getPath());
            }

            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
            Class loadedMyClass = urlClassLoader.loadClass(wf00302VO.getApi());

            System.out.println("Loaded class name: " + loadedMyClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
//        ClassPathResource resource = new ClassPathResource("pluginContext.xml", loader);
//        GenericApplicationContext ctx = new GenericApplicationContext(parentApplicationContext);
//        ctx.setClassLoader(loader);
//        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
//        xmlReader.setBeanClassLoader(loader);
//        xmlReader.loadBeanDefinitions(resource);


        return Responses.ListResponse.of(service.getEnviromentList(param));
    }

    /**
     * Gets enviroment list.
     *
     * @param param the param
     * @return the enviroment list
     */
    @RequestMapping("/getEnviromentList1.do")
    public Responses.ListResponse getEnviromentList1(@RequestBody Ad00101VO param) {

        // Batch 테스트
        dipBatchExecutor.runDipProcess();


        // Jar module unlading
        //RuntimeJarLoader.removeJarIndDir("/Users/jspark226/IdeaProjects/seoul-ams/service-modules/long-term-preservation/build/libs/long-term-preservation-0.0.1.jar");
        //AppRunner.restart(restartEndpoint);


        return Responses.ListResponse.of(service.getEnviromentList(param));
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Ad00101VO> list) {
        ApiResponse apiResponse = service.save(list);
        return apiResponse;
    }

}
