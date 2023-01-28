package com.Reservations.DTO;

import com.Reservations.Modeli.enums.TipRegistracije;

public class RegistracijaVlasnikaInstruktoraDTO {
	private Long id;

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;
	
	private String address;
	
	private String phone;
	
	private String city;
	
	private String country;
	
	private TipRegistracije registrationType;

	private String registerReason;

	public RegistracijaVlasnikaInstruktoraDTO() {
		super();
	}

	public RegistracijaVlasnikaInstruktoraDTO(Long id, String username, String password, String firstName,
			String lastName, String email, String address, String phone, String city, String country,
			TipRegistracije registrationType, String registerReason) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.city = city;
		this.country = country;
		this.registrationType = registrationType;
		this.registerReason = registerReason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public TipRegistracije getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(TipRegistracije registrationType) {
		this.registrationType = registrationType;
	}

	public String getRegisterReason() {
		return registerReason;
	}

	public void setRegisterReason(String registerReason) {
		this.registerReason = registerReason;
	}
	
	
}
