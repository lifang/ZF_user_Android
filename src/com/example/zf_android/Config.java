package com.example.zf_android;

import java.io.File;

public class Config {
	
    public final static String PATHS = "http://114.215.149.242:18080/ZFMerchant/api/";
    public final static String IMAGE_PATH = "";
	public static String checkVersion=PATHS+"";
	public static int ROWS=10;
	public static String getmes=PATHS+"message/receiver/getAll";
	public static String getsysmes=PATHS+"web/message/getAll";
	public final static String WANTBUY =PATHS+"paychannel/intention/add";
	public final static String LOGIN = PATHS+"user/studentLogin";
	public final static String CHANGEPASS = PATHS+"customers/updatePassword/";
	public static final int CITY_ID = 1;
	public static int UserID=1;
	public static String goodcomment=PATHS+"comment/list";
	//user/userRegistration  http://114.215.149.242:18080/ZFMerchant/api/user/updatePassword
	public final static String updatePassword = PATHS+"user/updatePassword";
	public final static String UserRegistration = PATHS+"user/userRegistration";
	public static final String SHARED = "zfandroid";
	public static final String FINDPASS = null; 
	public final static String RegistgetCode = PATHS+"user/sendPhoneVerificationCodeReg";//sendPhoneVerificationCodeReg
	public static final String GRTONE =PATHS+"customers/getOne/";
	// http://114.215.149.242:18080/ZFMerchant/api/message/receiver/deleteById
	public static final String DELETEMSG =PATHS+"message/receiver/deleteById";
	public static final String getMSGById =PATHS+"message/receiver/getById";
	public static final String webMSGById =PATHS+"web/message/getById";
	//http://114.215.149.242:18080/ZFMerchant/api/web/message/getById
	public static final String Car_edit =PATHS+"cart/update";
	public static final int CODE = 1;
	public static final String getMyOrderAll =PATHS+"order/getMyOrderAll";
	public static final String batchRead =PATHS+"message/receiver/batchRead";
	public static final String batchDelete =PATHS+"message/receiver/batchDelete";
	public static final String ADRESSDelete =PATHS+"customers/deleteAddress";
	public static final String paychannel_info =PATHS+"paychannel/info";
	public static final String GOODDETAIL =PATHS+"good/goodinfo";
	public static final String ChooseAdress = PATHS+"customers/getAddressList/";
	public static final String goodadd=PATHS+"cart/add";
	//http://114.215.149.242:18080/ZFMerchant/api/paychannel/info
	public static final String SHOPORDER=PATHS+"order/shop";
	 //"http://114.215.149.242:18080/ZFMerchant/api/order/cart"
	public static final String order_cart =PATHS+"order/cart";
	public static final String JWB =	"http://192.168.0.240:8080/ZFMerchant/api/order/shop"; 
}
