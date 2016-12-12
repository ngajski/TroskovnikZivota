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

	/**
	 * text of blog entry
	 */
	@Column(nullable = true, length = 30)
	private String town;

	@Column(nullable = true)
	private int postCode;

	@Column(nullable = true, length = 30)
	private String street;

	@Column(nullable = true)
	private int houseNumber;

	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<User> userOwners;

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
		return "Address [town=" + town + ", postCode=" + postCode + ", street=" + street
				+ ", houseNumber=" + houseNumber + ", userOwners=" + userOwners + "]";
	}

	
}
