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
@Table(name="addresses")
public class Address {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column(nullable = true, length = 30)
	private String street;

	@Column(nullable = true)
	private int houseNumber;

	/**
	 * text of blog entry
	 */
	@Column(nullable = true, length = 30)
	private String city;

	@Column(nullable = true)
	private int postCode;
	
	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<User> userOwners;
	
	/**
	 * @return the userOwners
	 */
	public List<User> getUserOwners() {
		return userOwners;
	}

	/**
	 * @param userOwners the userOwners to set
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
