package com.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;


public class ZookeeperGroup extends ConnectionWatcher {
	
	public void list(String groupName) throws Exception {
		String path = "/"+groupName;
		List<String> list = zKeeper.getChildren(path, false);
		list.forEach(x -> System.out.println(x));
	}
	
	private void delete(String path) throws Exception {
		List<String> children = zKeeper.getChildren(path, false);
		children.forEach(x -> {
			try {
				zKeeper.delete(path+"/"+x, -1);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});
		zKeeper.delete(path, -1);
	}
	
	private void set(String path) throws Exception {
		if (zKeeper.exists(path, this) != null) {
			zKeeper.setData(path, "gogleMap".getBytes(), 1);
		} else {
			zKeeper.create(path, "gogleMap".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			byte[] data = zKeeper.getData(path, this, null);
			System.out.println(data.toString());
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		ZookeeperGroup listGroup = new ZookeeperGroup();
		listGroup.connect("slave1:2181");
		listGroup.set("/gogle");
		listGroup.close();
	}
}
