package com.hadoop.rpc;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

public class RPCServer implements RPCServerInterface {
	@Override
	public String HiSay(String name) {
		return "Hi ~"+name;
	}
	
	public void rpcServer() throws Exception {
		Configuration conf = new Configuration();
		Server server = new RPC.Builder(conf).setProtocol(RPCServerInterface.class)
				.setInstance(new RPCServer()).setBindAddress("10.247.182.21")
				.setPort(8989)
				.build();
		server.start();
	}
	
	public static void main(String[] args) throws Exception {
		RPCServer rpcServer = new RPCServer();
		rpcServer.rpcServer();
	}
}
