package com.example.zf_android.entity;

public class MerchantEntity {
	private int id;
	private String title;
	private String legal_person_name;

	private String card_id_front_photo_path;
	private String card_id_back_photo_path;
	private String legal_person_card_id;
	private String bank_open_account;
	private String body_photo_path;
	private String account_pic_path;
	private String tax_no_pic_path;
	private int city_id;
	private String tax_registered_no;
	private String organization_code_no;
	private String license_no_pic_path;
	private String business_license_no;
	private String org_code_no_pic_path;
	private String account_bank_name;

	private Boolean Ischeck;

	public Boolean getIscheck() {
		return Ischeck;
	}

	public void setIscheck(Boolean ischeck) {
		Ischeck = ischeck;
	}

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

	public String getLegal_person_name() {
		return legal_person_name;
	}

	public void setLegal_person_name(String legal_person_name) {
		this.legal_person_name = legal_person_name;
	}

	public String getCard_id_front_photo_path() {
		return card_id_front_photo_path;
	}

	public void setCard_id_front_photo_path(String card_id_front_photo_path) {
		this.card_id_front_photo_path = card_id_front_photo_path;
	}

	public String getCard_id_back_photo_path() {
		return card_id_back_photo_path;
	}

	public void setCard_id_back_photo_path(String card_id_back_photo_path) {
		this.card_id_back_photo_path = card_id_back_photo_path;
	}

	public String getLegal_person_card_id() {
		return legal_person_card_id;
	}

	public void setLegal_person_card_id(String legal_person_card_id) {
		this.legal_person_card_id = legal_person_card_id;
	}

	public String getBank_open_account() {
		return bank_open_account;
	}

	public void setBank_open_account(String bank_open_account) {
		this.bank_open_account = bank_open_account;
	}

	public String getBody_photo_path() {
		return body_photo_path;
	}

	public void setBody_photo_path(String body_photo_path) {
		this.body_photo_path = body_photo_path;
	}

	public String getAccount_pic_path() {
		return account_pic_path;
	}

	public void setAccount_pic_path(String account_pic_path) {
		this.account_pic_path = account_pic_path;
	}

	public String getTax_no_pic_path() {
		return tax_no_pic_path;
	}

	public void setTax_no_pic_path(String tax_no_pic_path) {
		this.tax_no_pic_path = tax_no_pic_path;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public String getTax_registered_no() {
		return tax_registered_no;
	}

	public void setTax_registered_no(String tax_registered_no) {
		this.tax_registered_no = tax_registered_no;
	}

	public String getOrganization_code_no() {
		return organization_code_no;
	}

	public void setOrganization_code_no(String organization_code_no) {
		this.organization_code_no = organization_code_no;
	}

	public String getLicense_no_pic_path() {
		return license_no_pic_path;
	}

	public void setLicense_no_pic_path(String license_no_pic_path) {
		this.license_no_pic_path = license_no_pic_path;
	}

	public String getBusiness_license_no() {
		return business_license_no;
	}

	public void setBusiness_license_no(String business_license_no) {
		this.business_license_no = business_license_no;
	}

	public String getOrg_code_no_pic_path() {
		return org_code_no_pic_path;
	}

	public void setOrg_code_no_pic_path(String org_code_no_pic_path) {
		this.org_code_no_pic_path = org_code_no_pic_path;
	}

	public String getAccount_bank_name() {
		return account_bank_name;
	}

	public void setAccount_bank_name(String account_bank_name) {
		this.account_bank_name = account_bank_name;
	}

}
