package com.auel.kontakten.model;

import java.io.Serializable;
import java.time.LocalDate;
//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Kontakt implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank( message="Please enter the name")
	private String name;
	
	@Size(min=2, max=20, message="Comment should be minimun 2 and maximum 20 characters")
	private String telefon;
	
	@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Sex sex; 
	
	//@Birthday //(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
	//@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	
	@Lob
	@Column(name = "photo", columnDefinition="BLOB")
	private byte[] photo;
    
	public Kontakt() {
		super();
	}
	
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
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
		return "Kontakt [id=" + id + ", name=" + name + ", telefon=" + telefon + ", email=" + email + ", sex=" + sex
				+ ", birthday=" + birthday + "]";
	}

		
	
}


