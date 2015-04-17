package com.example.zf_android.entity;

public class MerchantEntity {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLegalPersonName() {
		return legalPersonName;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	public String getCardIdFrontPhotoPath() {
		return cardIdFrontPhotoPath;
	}
	public void setCardIdFrontPhotoPath(String cardIdFrontPhotoPath) {
		this.cardIdFrontPhotoPath = cardIdFrontPhotoPath;
	}
	public String getCardIdBackPhotoPath() {
		return cardIdBackPhotoPath;
	}
	public void setCardIdBackPhotoPath(String cardIdBackPhotoPath) {
		this.cardIdBackPhotoPath = cardIdBackPhotoPath;
	}
	public String getLegalPersonCardId() {
		return legalPersonCardId;
	}
	public void setLegalPersonCardId(String legalPersonCardId) {
		this.legalPersonCardId = legalPersonCardId;
	}
	public String getBankOpenAccount() {
		return bankOpenAccount;
	}
	public void setBankOpenAccount(String bankOpenAccount) {
		this.bankOpenAccount = bankOpenAccount;
	}
	public String getBodyPhotoPath() {
		return bodyPhotoPath;
	}
	public void setBodyPhotoPath(String bodyPhotoPath) {
		this.bodyPhotoPath = bodyPhotoPath;
	}
	public String getAccountPicPath() {
		return accountPicPath;
	}
	public void setAccountPicPath(String accountPicPath) {
		this.accountPicPath = accountPicPath;
	}
	public String getTaxNoPicPath() {
		return taxNoPicPath;
	}
	public void setTaxNoPicPath(String taxNoPicPath) {
		this.taxNoPicPath = taxNoPicPath;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getTaxRegisteredNo() {
		return taxRegisteredNo;
	}
	public void setTaxRegisteredNo(String taxRegisteredNo) {
		this.taxRegisteredNo = taxRegisteredNo;
	}
	public String getOrganizationCodeNo() {
		return organizationCodeNo;
	}
	public void setOrganizationCodeNo(String organizationCodeNo) {
		this.organizationCodeNo = organizationCodeNo;
	}
	public String getLicenseNoPicPath() {
		return licenseNoPicPath;
	}
	public void setLicenseNoPicPath(String licenseNoPicPath) {
		this.licenseNoPicPath = licenseNoPicPath;
	}
	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}
	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}
	public String getOrgCodeNoPicPath() {
		return orgCodeNoPicPath;
	}
	public void setOrgCodeNoPicPath(String orgCodeNoPicPath) {
		this.orgCodeNoPicPath = orgCodeNoPicPath;
	}
	public String getAccountBankName() {
		return accountBankName;
	}
	public void setAccountBankName(String accountBankName) {
		this.accountBankName = accountBankName;
	}
	private String title;
	private String legalPersonName;
	private String cardIdFrontPhotoPath;
	private String cardIdBackPhotoPath;
	private String legalPersonCardId;
	private String bankOpenAccount;
	private String bodyPhotoPath;
	private String accountPicPath;
	private String taxNoPicPath;
	private int cityId;
	private String taxRegisteredNo;
	private String organizationCodeNo;
	private String licenseNoPicPath;
	private String businessLicenseNo;
	private String orgCodeNoPicPath;
	private String accountBankName;
	private Boolean Ischeck;
	public Boolean getIscheck() {
		return Ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		Ischeck = ischeck;
	}

}