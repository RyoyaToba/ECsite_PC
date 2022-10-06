package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserForm {
	@NotBlank(message = "名前を入力して下さい")
	private String name;
	@NotBlank(message = "メールアドレスを入力して下さい")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;
	@NotBlank(message = "パスワードを入力して下さい")
	@Size(min=8,max=16,message = "パスワードは８文字以上１６文字以内で設定して下さい")
	private String password;
	@NotBlank ( message = "もう一度パスワードを入力して下さい")
	private String checkPassword;
	@NotBlank(message = "郵便番号を入力して下さい")
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$",message = "郵便番号はXXX-XXXXの形式で入力して下さい")
	private String zipcode;
	@NotBlank(message = "住所を入力して下さい")
	private String address;
	@NotBlank(message = "電話番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{2,4}-[0-9]{2,4}-[0-9]{4}$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力して下さい")
	private String telephone;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckPassword() {
		return checkPassword;
	}
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", password=" + password + ", checkPassword="
				+ checkPassword + ", zipcode=" + zipcode + ", address=" + address + ", telephone=" + telephone + "]";
	}
}
