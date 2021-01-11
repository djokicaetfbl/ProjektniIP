package org.unibl.etf.dto;

public class PostGroup {
	
	private int id;
	private String name;
	private String key;
	private boolean active;
	
	public PostGroup(int id, String name, String key, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.active = active;
	}
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
