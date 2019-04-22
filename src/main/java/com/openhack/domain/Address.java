package com.openhack.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class Address.
 */
@Embeddable
public class Address {

	/** The street. */
	@Column(nullable=true)
	private String street; // e.g., 100 Main ST
	
	/** The city. */
	@Column(nullable=true)
	private String city;
	
	/** The state. */
	@Column(nullable=true)
	private String state;
	
	/** The zip. */
	@Column(nullable=true)
	private String zip;
	
	/**
	 * Instantiates a new address.
	 */
	public Address() {}
	
	/**
	 * Instantiates a new address.
	 *
	 * @param street the street
	 * @param city the city
	 * @param state the state
	 * @param zip the zip
	 */
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip.
	 *
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}
