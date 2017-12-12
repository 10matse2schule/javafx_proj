package address.model;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

public class PersonRep {
	@Expose(serialize = true, deserialize = true)
	private String firstname;
	@Expose(serialize = true, deserialize = true)
	private String lastname;
	@Expose(serialize = true, deserialize = true)
	private String city;
	@Expose(serialize = true, deserialize = true)
	private String postalCode;
	@Expose(serialize = true, deserialize = true)
	private String street;
	@Expose(serialize = true, deserialize = true)
	private LocalDate birthday;
	
	public PersonRep(String firstname, String lastname, String city, String postalCode, String street,
			LocalDate birthday) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.postalCode = postalCode;
		this.street = street;
		this.birthday = birthday;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
}
