package com.hadoop.mysql;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

public class MyWritable implements Writable,DBWritable {
	
	private int counter;
	private long timetamp;
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(counter);
		out.writeLong(timetamp);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		counter = in.readInt();
		timetamp = in.readLong();
	}
	
	public static MyWritable read(DataInput in) throws Exception {
		MyWritable writable = new MyWritable();
		writable.readFields(in);
		return writable;
	}

	@Override
	public void write(PreparedStatement statement) throws SQLException {
		statement.setInt(1, counter);
		statement.setLong(2, timetamp);
	}
	
	@Override
	public void readFields(ResultSet resultSet) throws SQLException {
		counter = resultSet.getInt(1);
		timetamp = resultSet.getLong(2);
	}
}
