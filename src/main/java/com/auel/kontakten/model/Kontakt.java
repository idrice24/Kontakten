package com.auel.kontakten.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
public class Kontakt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name;
	
	private String telefon;
	
	@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Sex sex; 

	public Kontakt() {
		super();
	}
	
	public Kontakt(@NotNull String name, String telefon,
			@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern") String email, Sex sex) {
		super();
		this.name = name;
		this.telefon = telefon;
		this.email = email;
		this.sex = sex;
	}

	public Kontakt(int id, @NotNull String name, String telefon,
			@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern") String email, Sex sex) {
		super();
		this.id = id;
		this.name = name;
		this.telefon = telefon;
		this.email = email;
		this.sex = sex;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Kontakt [id=" + id + ", name=" + name + ", telefon=" + telefon + ", email=" + email + ", sex=" + sex.name()
				+ "]";
	}
	
	
	
}


