package com.hadoop.mysql;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

public class DBRecord implements Writable,DBWritable {
	
	private int id;
	private String name;
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void readFields(ResultSet set) throws SQLException {
		this.id = set.getInt("id");
		this.name = set.getString("name");
		this.type = set.getString("type");
	}

	@Override
	public void write(PreparedStatement ps) throws SQLException {
		ps.setInt(1, id);
		ps.setString(2, name);
		ps.setString(3, type);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.id = in.readInt();
		this.name = Text.readString(in);
		this.type = Text.readString(in);
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.id);
		Text.writeString(out, this.name);
		Text.writeString(out, this.type);
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.name + " " + this.type + " ";
	}

}
