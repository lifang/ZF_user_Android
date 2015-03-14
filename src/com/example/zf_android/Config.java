package com.example.zf_android;

public class Config {
	
    public final static String PATHS = "http://114.215.149.242:18080/ZFMerchant/api/";
    public final static String IMAGE_PATH = "";
	public static String checkVersion=PATHS+"";
	public static int ROWS=10;
	public static String getmes=PATHS+"message/receiver/getAll";
	public static String getsysmes=PATHS+"web/message/getAll";
	 
	public final static String LOGIN = PATHS+"user/studentLogin";
	public static final int CITY_ID = 1;
	public static int UserID=1;
	public static String goodcomment=PATHS+"comment/list";
	//user/userRegistration
	public final static String UserRegistration = PATHS+"user/userRegistration";
	public static final String SHARED = "zfandroid";
	public static final String FINDPASS = null;
	public final static String RegistgetCode = PATHS+"user/sendPhoneVerificationCode/";
	public static final String GRTONE =PATHS+"customers/getOne/";
	// http://114.215.149.242:18080/ZFMerchant/api/message/receiver/getById
	public static final String getMSGById =PATHS+"message/receiver/getById";
	public static final String Car_edit =PATHS+"cart/update";
	public static final int CODE = 1;
	public static final String getMyOrderAll =PATHS+"order/getMyOrderAll";
	public static final String batchRead =PATHS+"message/receiver/batchRead";
	public static final String GOODDETAIL =PATHS+"good/goodinfo";
	public static final String ChooseAdress = PATHS+"customers/getAddressList/";
	public static final String goodadd=PATHS+"cart/add";
	//http://114.215.149.242:18080/ZFMerchant/api/order/shop
	public static final String SHOPORDER=PATHS+"order/shop";
	 
}
