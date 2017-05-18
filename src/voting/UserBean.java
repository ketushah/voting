package voting;

import java.io.Serializable;
import java.util.HashMap;

public class UserBean implements Serializable{
	
	private int member_id;
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	private String username;
	private HashMap<String, String> vote = new HashMap<String,String>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<String, String> getVote() {
		return vote;
	}
	public void setVote(HashMap<String, String> vote) {
		this.vote = vote;
	}	
}
