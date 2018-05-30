package com.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;

public class HelloZookeeper {
	
	public static void main(String[] args) {
		
		String hostPort = "slave1:2181";
		String zpath = "/";
		List<String> zooChildren = new ArrayList<>();
		try {
			ZooKeeper zKeeper = new ZooKeeper(hostPort,2000,null);
			if (zKeeper != null) {
				zooChildren = zKeeper.getChildren(zpath, false);
				zooChildren.forEach(s->System.out.println(s));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
