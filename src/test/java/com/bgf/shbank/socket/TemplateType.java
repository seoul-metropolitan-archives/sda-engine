package com.bgf.shbank.socket;

/**
 * Created by tw.jang on 2017-01-23.
 */
public interface TemplateType {

    String SEND_MSG = "send_msg";

    String RECV_MSG = "recv_msg";

    String SEND_MSG_ENCODER = "send_msg_encoder";

    String RECV_MSG_DECODER = "recv_msg_decoder";

    String TASK_HANDLER = "task_handler";
}
