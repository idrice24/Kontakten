package com.auel.kontakten.model;

public class SuchForm {
	String searchValue;
	String searchOption;
	public SuchForm() {
		super();
		//searchValue="";
	}
	
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public SuchForm(String searchValue, String searchOption) {
		super();
		this.searchValue = searchValue;
		this.searchOption = searchOption;
	}
	
	

}
