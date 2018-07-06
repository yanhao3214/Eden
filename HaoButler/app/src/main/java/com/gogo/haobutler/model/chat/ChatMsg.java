package com.gogo.haobutler.model.chat;

/**
 * @author: 闫昊
 * @date: 2018/6/14 0014
 * @function:
 */
public class ChatMsg {
    public static final int CHAT_ITEM_LEFT = 0;
    public static final int CHAT_ITEM_RIGHT = 1;
    private int type;
    private String msg;

    public ChatMsg(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
