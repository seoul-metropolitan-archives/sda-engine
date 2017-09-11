package @{PACKAGE_NAME};

import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBuf;

/**
 * Created by tw.jang on 17. 1. 24.
 */
public class @{CLASS_NAME} {

    public static ByteBuf encode(@{SEND_MSG} msg) throws Exception {

        ByteBuf out = Unpooled.buffer(400);

@{CONTENTS}
        return out;
    }
}

