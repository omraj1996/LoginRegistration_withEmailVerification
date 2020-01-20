package com.mvc.bean;

public class RegisterBean 
{
    private String firstname,lastname,username,password,myhash;
    
    public String getMyhash() {
		return myhash;
	}
	public void setMyhash(String myhash) {
		this.myhash = myhash;
	}
	public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname){
        this.firstname=firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname=lastname;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
}