package rmsoft.ams.seoul.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CommonAPIInterceptor extends HandlerInterceptorAdapter{
    /*public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        boolean result = false;

        String webRoot = request.getContextPath();

        result = true;

        *//*try {

            //이동하는 url이 예외url 일경우
            if(excludeUrl(request)){
                log.info("======= call /sessionIntercepter url is no session check =======");
                result =  true;
            }else{
                //세션값이 널일경우
                if(request.getAttribute("sessionId") == null ){

                    //Ajax 콜인지 아닌지 판단
                    if(isAjaxRequest(request)){
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return false;
                    }else{ // Ajax 콜이 아닐경우 login페이지로 이동.

                        response.sendRedirect(webRoot + "/login.do");
                        result =  false;
                    }

                }else{
                    result =  true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*//*
        //널이 아니면 정상적으로 컨트롤러 호출
        return result;
    }*/

    /*private boolean excludeUrl(HttpServletRequest request) {
        String uri = request.getRequestURI().toString().trim();
        log.info("======= call /sessionIntercepter request Url = " + uri + "=======");
        if (uri.indexOf("/login.do") > -1 || uri.indexOf("/common") > -1 || uri.indexOf("/logout.do") > -1){
            return true;
        } else return false;
    }

    private boolean isAjaxRequest(HttpServletRequest req) {
        String ajaxHeader = "AJAX";
        log.info("======= call /sessionIntercepter req.getHeader(ajaxHeader) : " + req.getHeader(ajaxHeader) + "=======");
        return req.getHeader(ajaxHeader) != null && req.getHeader(ajaxHeader).equals(Boolean.TRUE.toString());
    }
*/


    /*public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String controllerName = "";
        String actionName = "";

        if( handler instanceof HandlerMethod) {
            // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //controllerName = handlerMethod.getBean().getClass().getSimpleName().replace("Controller", "");
            controllerName  = handlerMethod.getBeanType().getSimpleName().replace("Controller", "");
            actionName = handlerMethod.getMethod().getName();
        }

        modelAndView.addObject("controllerName", controllerName );
        modelAndView.addObject("actionName", actionName );
    }*/
}
