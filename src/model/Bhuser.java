package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class Bhuser implements Serializable {
	private static final long serialVersionUID = 1L;

	private long bhuserid;
	private Date joindate;
	private String motto;
	private String useremail;
	private String username;
	private String userpassword;
	//private List<Bhpost> bhposts;

	public Bhuser() {
	}

	public long getBhuserid() {
		return this.bhuserid;
	}

	public void setBhuserid(long bhuserid) {
		this.bhuserid = bhuserid;
	}

	public Date getJoindate() {
		return this.joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	public String getMotto() {
		return this.motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getUseremail() {
		return this.useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return this.userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
/*
	public List<Bhpost> getBhposts() {
		return this.bhposts;
	}

	public void setBhposts(List<Bhpost> bhposts) {
		this.bhposts = bhposts;
	}
*/

}
