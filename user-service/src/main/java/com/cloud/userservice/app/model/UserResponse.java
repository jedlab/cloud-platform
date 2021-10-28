package com.cloud.userservice.app.model;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse implements Serializable {

	private Long id;
	private String username;
	
}
