package com.i4jsfdemo.enums;

public enum StatusUser {
	ACTIVE("active"), INACTIVE("inactive");

	private String status;

	StatusUser(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		
		return status;
	}

	public String getStatus() {
		return status;
	}
}
