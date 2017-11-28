package rmsoft.ams.seoul.ac.ac002.vo;

import lombok.Data;

@Data
public class Ac002VO
{
    private String startProgramUuid = "";
    private String menuId = "";
    private String id = "";
    private String progNm = "";
    private String menuNm = "";
    private String progPh = "";
    private String menuParams = "";
    private String url = "";
    private String status = "on";
    private boolean fixed = true;

    public void setProgPh(String progPh) {
        String[] urlPath = progPh.toLowerCase().split("/");
        if(urlPath.length > 0)
            progPh = "/" + urlPath[0] + "/" + urlPath[1] + "/" + urlPath[1];
        this.progPh = progPh;
    }

    public void setUrl(String url) {
        String[] urlPath = url.toLowerCase().split("/");
        if(urlPath.length > 0)
            url = "/" + urlPath[0] + "/" + urlPath[1] + "/" + urlPath[1];
        this.url = url;
    }
}
