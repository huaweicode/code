package com.hadoop.rpc;

import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class RPCilent {
	
	public static void main(String[] args) throws Exception {
		RPCServerInterface serverInterface = RPC.getProxy(RPCServerInterface.class, 10010, 
				new InetSocketAddress("10.247.182.21", 8989), new Configuration());
		String result = serverInterface.HiSay("张伟");
		System.out.println(result);
		RPC.stopProxy(serverInterface);
	}

}
