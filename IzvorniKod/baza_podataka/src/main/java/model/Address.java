package model;

import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Address {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String street;

	@Column(nullable = false)
	private int houseNumber;

	/**
	 * text of blog entry
	 */
	@Column(nullable = false, length = 30)
	private String city;

	@Column(nullable = false)
	private int postCode;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
