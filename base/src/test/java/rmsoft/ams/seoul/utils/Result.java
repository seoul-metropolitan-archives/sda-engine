package rmsoft.ams.seoul.utils;

/**
 * Created by twjang on 2015-09-13.
 */
public enum Result {

    /**
     * Success result.
     */
    SUCCESS     ("Success", "RESULT_SUCCESS"),
    /**
     * Fail result.
     */
    FAIL        ("Fail",    "RESULT_FAIL"),
    /**
     * True result.
     */
    TRUE        ("True",    "RESULT_TRUE"),
    /**
     * False result.
     */
    FALSE       ("False",   "RESULT_FALSE"),
    /**
     * Empty result.
     */
    EMPTY       ("Empty",   "RESULT_EMPTY");

    private String code;

    private String message;

    Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
