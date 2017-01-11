package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "addresses")
public class Address {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(nullable = true, length = 30)
	private String town;

	@Column(nullable = true)
	private int postCode;

	@Column(nullable = true, length = 30)
	private String street;

	@Column(nullable = true)
	private int houseNumber;

	@JsonIgnore
	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<User> userOwners;

	public Address() {
		super();
	}

	public Address(Long id, String town, int postCode, String street, int houseNumber, List<User> userOwners) {
		super();
		this.id = id;
		this.town = town;
		this.postCode = postCode;
		this.street = street;
		this.houseNumber = houseNumber;
		this.userOwners = userOwners;
	}

	/**
	 * @return the userOwners
	 */
	public List<User> getUserOwners() {
		return userOwners;
	}

	/**
	 * @param userOwners
	 *            the userOwners to set
	 */
	public void setUserOwners(List<User> userOwners) {
		this.userOwners = userOwners;
	}

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

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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

	@Override
	public String toString() {
		return "Address [town=" + town + ", postCode=" + postCode + ", street=" + street + ", houseNumber="
				+ houseNumber + "]";
	}
	
}
