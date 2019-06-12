package com.service;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ServerCall.Listener;

public class ServerInterceptorHandler
implements ServerInterceptor 
{
	private static int callNumber = 0;
	public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) 
	{
		// TODO Auto-generated method stub
		System.out.println((++callNumber)+": Intercepted a client request\n\t[Call] "+call.toString()+"\n\t[Headers] "+headers.toString()+"\n\t[Type] "+next.toString());
		return next.startCall(call, headers);
	}
}
