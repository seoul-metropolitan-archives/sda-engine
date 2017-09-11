package @{PACKAGE_NAME};

import io.netty.buffer.ByteBuf;

/**
 * Created by tw.jang on 17. 1. 24.
 */
public class @{CLASS_NAME} {

    public static @{RECV_MSG} decode(ByteBuf in) throws Exception {

        @{RECV_MSG} message = new @{RECV_MSG}();
        byte[] byteArray;

@{CONTENTS}

        return message;
    }
}

