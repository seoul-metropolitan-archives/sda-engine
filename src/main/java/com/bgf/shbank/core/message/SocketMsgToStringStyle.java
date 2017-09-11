package com.bgf.shbank.core.message;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by tw.jang on 2017-02-16.
 */
public class SocketMsgToStringStyle extends ToStringStyle {

    private static final long serialVersionUID = 1L;

    public static final ToStringStyle STYLE = new SocketMsgToStringStyle();

    SocketMsgToStringStyle() {
        super();
        this.setUseClassName(false);
        this.setUseIdentityHashCode(false);
        this.setContentStart("[" + SystemUtils.LINE_SEPARATOR + "\t");
        this.setFieldNameValueSeparator(" :: ");
        this.setNullText("NULL");
        this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "\t");
        this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
    }

    @Override
    public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {

        if (fieldName.equals("filler")) return;

        fieldName = String.format("%-20s", fieldName);

        super.append(buffer, fieldName, value, fullDetail);
    }

    private Object readResolve() {
        return SocketMsgToStringStyle.STYLE;
    }
}
