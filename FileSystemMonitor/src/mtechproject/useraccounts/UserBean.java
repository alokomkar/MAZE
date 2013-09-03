package mtechproject.useraccounts;

public class UserBean {
	
	private String Username, Prim_password, Sec_password, Locked, Mobileno;

	public String getLocked() {
		return Locked;
	}

	public void setLocked(String locked) {
		Locked = locked;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPrim_password() {
		return Prim_password;
	}

	public void setPrim_password(String prim_password) {
		Prim_password = prim_password;
	}

	public String getSec_password() {
		return Sec_password;
	}

	public void setSec_password(String sec_password) {
		Sec_password = sec_password;
	}

	public void setMobileno(String mobileno) {
		Mobileno = mobileno;
	}

	public String getMobileno() {
		return Mobileno;
	}
	
	

}
