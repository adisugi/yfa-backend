package com.kel3.yfaexpress.model.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;

}
