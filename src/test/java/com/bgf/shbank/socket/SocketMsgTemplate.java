package com.bgf.shbank.socket;

import lombok.Data;

import java.util.List;

/**
 * Created by tw.jang on 2017-01-23.
 */
@Data
public class SocketMsgTemplate {

    private String name;

    private List<SocketMsgNode> socketMsgNodes;

}
