package com.goeuro.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {


	private long _id;
	private String key;
	private String name;
	private String fullName;
	private String iata_airport_code;
	private String type;
	private String country;
	
	@JsonProperty("geo_position")
	private GeoPosition geoPosition;
	
	private long location_id;
	private boolean inEurope;
	private String countryCode;
	private String coreCountry;
	private String distance;
	
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIata_airport_code() {
		return iata_airport_code;
	}
	public void setIata_airport_code(String iata_airport_code) {
		this.iata_airport_code = iata_airport_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public GeoPosition getGeoPosition() {
		return geoPosition;
	}
	public void setGeoPosition(GeoPosition geoPosition) {
		this.geoPosition = geoPosition;
	}
	public long getLocation_id() {
		return location_id;
	}
	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}
	public boolean isInEurope() {
		return inEurope;
	}
	public void setInEurope(boolean inEurope) {
		this.inEurope = inEurope;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCoreCountry() {
		return coreCountry;
	}
	public void setCoreCountry(String coreCountry) {
		this.coreCountry = coreCountry;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public class GeoPosition {

		private double latitude;
		private double longitude;
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}	

		@Override
		public String toString() {
			return "GeoPosition [latitude=" + latitude + ", longitude=" + longitude + "]";
		}
		
	}

	@Override
	public String toString() {
		return "City [_id=" + _id + ", key=" + key + ", name=" + name + ", fullName=" + fullName
				+ ", iata_airport_code=" + iata_airport_code + ", type=" + type + ", country=" + country
				+ ", geoPosition=" + geoPosition + ", location_id=" + location_id + ", inEurope=" + inEurope
				+ ", countryCode=" + countryCode + ", coreCountry=" + coreCountry + ", distance=" + distance + "]";
	}
	

	
	
}
