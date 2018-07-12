package rmsoft.ams.seoul.common.vo;

        import lombok.Data;

/**
 * The type Page info vo.
 */
@Data
public class CommonServiceVO {
    private String serviceId;
    private String methodName;
    private String returnType; // Responses.ListResponse(List) , Responses.PageResponse(Page) , VO Object(Single)

    /**
     * Gets service id.
     *
     * @return the service id
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Sets service id.
     *
     * @param serviceId the service id
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Gets method name.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets method name.
     *
     * @param methodName the method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets return type.
     *
     * @return the return type
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Sets return type.
     *
     * @param returnType the return type
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
