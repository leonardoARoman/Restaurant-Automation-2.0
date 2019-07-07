package com.example.APIs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ChannelManager {

    private static ChannelManager channelManagerInstance;
    private ManagedChannel channel;
    private final String host = "10.0.0.107";
    private final int port = 8080;

    private ChannelManager() {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext(true)
                .build();
    }

    public static ChannelManager getChannelManagerInstance(){
        if (channelManagerInstance==null){
            channelManagerInstance = new ChannelManager();
        }
        return channelManagerInstance;
    }

    public ManagedChannel getChannel(){
        return channel;
    }
}
