package com.mapping.enums;

public enum Status {
	YES("Y"), NO("N");

	private String status;

	Status(String status) {
		this.status = status;
	}

	public String status() {
		return status;
	}

}
