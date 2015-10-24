package com.bankhead.api.json;

public class ContactJson {
	private String email;
    private String zipCode;
    
    public ContactJson(){}
    
    public ContactJson(
    		String email,
    		String zipCode) 
    {
    	setEmail(email);
    	setZipCode(zipCode);
    	
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


    
}
