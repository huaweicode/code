package com.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ConnectionWatcher implements Watcher {
	
	private static final int SESSION_TIMEOUT = 5000;
	
	protected ZooKeeper zKeeper;
	
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public void connect(String connect) throws Exception {
		zKeeper = new ZooKeeper(connect, SESSION_TIMEOUT, this);
	}
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
		
	}
	public void close() throws Exception {
		zKeeper.close();
	}

}
