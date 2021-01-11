package org.unibl.etf.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PostHasDangerPostGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer postID;
	private ArrayList<Integer> dangerPostGroupIdArrayList = new ArrayList<Integer>();
	public PostHasDangerPostGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PostHasDangerPostGroup(Integer postID, ArrayList<Integer> dangerPostGroupIdArrayList) {
		super();
		this.postID = postID;
		this.dangerPostGroupIdArrayList = dangerPostGroupIdArrayList;
	}

	public Integer getPostID() {
		return postID;
	}

	public void setPostID(Integer postID) {
		this.postID = postID;
	}

	public ArrayList<Integer> getDangerPostGroupIdArrayList() {
		return dangerPostGroupIdArrayList;
	}

	public void setDangerPostGroupIdArrayList(ArrayList<Integer> dangerPostGroupIdArrayList) {
		this.dangerPostGroupIdArrayList = dangerPostGroupIdArrayList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
