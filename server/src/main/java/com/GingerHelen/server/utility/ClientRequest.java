package com.GingerHelen.server.utility;

import lombok.Getter;

import java.net.SocketAddress;

/**
 * класс, моделирующмий запрос клиента (включающий сам запрос и адрес клиента)
 */
@Getter
public class ClientRequest {
    private final Object request;
    private final SocketAddress clientAddress;

    public ClientRequest(Object request, SocketAddress clientAddress) {
        this.request = request;
        this.clientAddress = clientAddress;
    }

}
