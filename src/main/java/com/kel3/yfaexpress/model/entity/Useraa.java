package com.kel3.yfaexpress.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name =  "users")
public class Useraa {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone")
	private String phone;

	private String email;

	private String password;

	@Column(name="roles")
	private Long roleId;

	@ManyToOne
	@JoinColumn(name = "roles", insertable = false, updatable = false)
	private Roles roles;

}
