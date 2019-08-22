package com.example.APIs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Channel manager API used to set IP address and port number.
 * The channel is configured once as a singleton and shared by
 * the different views in the application in a sequential manner.
 */
public class ChannelManager {

    private static ChannelManager channelManagerInstance;
    private ManagedChannel channel;
    private final String host = "192.168.1.11";
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
