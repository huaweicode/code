package com.lambda;

import java.util.List;

public class Article {
	private String title;
	private String author;
	private String conuntry;
	private List<Works> tags;
	
	 public Article(String title,String author,String country,List<Works> tags) {
		 this.title = title;
		 this.author = author;
		 this.tags = tags;
		 this.conuntry = country;
	 }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Works> getTags() {
		return tags;
	}

	public void setTags(List<Works> tags) {
		this.tags = tags;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public String getConuntry() {
		return conuntry;
	}

	public void setConuntry(String conuntry) {
		this.conuntry = conuntry;
	}
}
