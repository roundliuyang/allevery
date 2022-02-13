package com.yly.springwebsocket02.message;

/**
 * 用户认证请求
 */
public class AuthRequest implements Message {

    /**
     * TYPE 静态属性，消息类型为 AUTH_REQUEST
     */
    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     * 在 WebSocket 协议中，我们也需要认证当前连接，用户身份是什么。一般情况下，我们采用用户调用 HTTP 登录接口，登录成功后返回的访问令牌 accessToken 。这里，我们先不拓展开讲，
     * 事后胖友可以看看 《基于 Token 认证的 WebSocket 连接》 文章。
     */
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

}
