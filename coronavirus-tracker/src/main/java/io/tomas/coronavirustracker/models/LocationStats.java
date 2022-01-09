package io.tomas.coronavirustracker.models;

public class LocationStats {
	private String state;
	private String country;
	private int latestConfirmedCases;
	private int diffPreviousDay;
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestConfirmedCases() {
		return latestConfirmedCases;
	}
	public void setLatestConfirmedCases(int latestConfirmedCases) {
		this.latestConfirmedCases = latestConfirmedCases;
	}
	
	public int getDiffPreviousDay() {
		return diffPreviousDay;
	}
	public void setDiffPreviousDay(int diffPreviousDay) {
		this.diffPreviousDay = diffPreviousDay;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestConfirmedCases="
				+ latestConfirmedCases + "]";
	}
	
	
	
}
