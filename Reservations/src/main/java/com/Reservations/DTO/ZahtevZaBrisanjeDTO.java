package com.Reservations.DTO;

import com.Reservations.Modeli.ZahtevZaBrisanje;

public class ZahtevZaBrisanjeDTO {
	
	private long id;
	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;
	
	private String address;
	
	private String phone;
	
	private String city;
	
	private String country;
	
	private String razlog;

	@Override
	public String toString() {
		return "ZahtevZaBrisanjeDTO [id=" + id + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", address=" + address + ", phone="
				+ phone + ", city=" + city + ", country=" + country + ", razlog=" + razlog + "]";
	}






	public ZahtevZaBrisanjeDTO() {
		super();
	}
	




	public long getId() {
		return id;
	}






	public void setId(long id) {
		this.id = id;
	}




	public ZahtevZaBrisanjeDTO(long id, String username, String password, String firstName, String lastName,
			String email, String address, String phone, String city, String country, String razlog) {
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
		this.razlog = razlog;
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



	public String getRazlog() {
		return razlog;
	}



	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}


	
	
}
