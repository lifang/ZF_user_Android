package com.example.zf_android;


public class Config {
	
	//视频通话
	public static final String VIDEO_SERVER_IP = "121.40.84.2";
	public static final int VIDEO_SERVER_PORT = 8906;

	
	
	public static final String FILE_PATH = "http://114.215.149.242:18080/ZFMerchant/";
	public static final String POS_PIC_URL = FILE_PATH+"{0}/b.jpg";
	public static final String FILE_URL = FILE_PATH+"{0}";

//	public final static String PATHS = "http://114.215.149.242:18080/ZFMerchant/api/";
	//sit
//	public final static String PATHS = "http://192.168.10.109/ZFMerchant/api/";
//	public final static String PATHS = "http://121.40.84.2:8080/ZFMerchant/api/";
//	public final static String PATHS = "http://192.168.10.138:8080/ZFMerchant/api/";
	public final static String PATHS = "http://121.40.84.2:8080/ZFMerchant/api/";
	
	public final static String IMAGE_PATH = "";
	public static String URL_CHECK_VERSION=PATHS+"comment/appVersion";
	public static int ROWS=10;
	public static String getmes=PATHS+"message/receiver/getAll";
	public static String getsysmes=PATHS+"web/message/getAll";
	public final static String WANTBUY =PATHS+"paychannel/intention/add";
	public final static String URL_LOGIN = PATHS+"user/studentLogin";
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
	//http://114.215.149.242:18080/ZFMerchant/api/paychannel/info

	public static final String URL_MERCHANT_DELETE = PATHS+"merchant/delete";
	public static final String URL_MERCHANT_LIST = PATHS+"merchant/getList/{0}/{1}/{2}";
	public static final String URL_MERCHANT_INFO = PATHS+"merchant/getOne/{0}";
	public static final String URL_MERCHANT_EDIT = PATHS + "merchant/update";
	public static final String URL_MERCHANT_CREATE = PATHS + "merchant/insert";
	public static final String URL_CART_LIST = PATHS + "cart/list";
	public static final String URL_GOOD_INFO = PATHS + "good/goodinfo";
	public static final String URL_CART_ADD = PATHS + "cart/add";
	public static final String URL_ADDRESS_LIST = PATHS+"customers/getAddressList/{0}";
	public static final String URL_CART_DELETE = PATHS+"cart/delete";
	public static final String URL_PAYCHANNEL_INFO =PATHS+"paychannel/info";
	public static final String URL_ORDER_SHOP=PATHS+"order/shop";
	public static final String URL_ORDER_LEASE=PATHS+"order/lease";
	public static final String URL_ORDER_CART =PATHS+"order/cart";
	public static final String URL_GOOD_LIST = PATHS+"good/list";
	//niemin
	public static String find_phoneCode = "";//找回密码的手机验证码
	public static String reg_phoneCode = "";//注册的手机验证码
	public static int countShopCar = 0;//新添加购物车的数量
	public static final String URL_ORDER_LIST =PATHS+"order/getMyOrderAll";
	public static final String URL_CANCEL_MYORDER =PATHS+"order/cancelMyOrder";
	public static final String URL_GET_MYORDERBYID =PATHS+"order/getMyOrderById";
	public static final String URL_BATCH_SAVECOMMENT =PATHS+"order/batchSaveComment";
	public static final String URL_CUSTOMERS_UPDATE =PATHS+"customers/update/";
	public static final String URL_GET_INTEGRALLIST =PATHS+"customers/getIntegralList/{0}/{1}/{2}";
	public static final String URL_GET_INTEGRALTOTAL =PATHS+"customers/getIntegralTotal/{0}";
	public static final String URL_INSERT_INTEGRALCONVERT =PATHS+"customers/insertIntegralConvert";
	public static final String URL_SEND_PHONECODE =PATHS+"user/sendPhoneVerificationCodeFind";
	public static final String URL_FIGURE_GETLIST =PATHS+"index/sysshufflingfigure/getList";
	public static final String URL_GOOD_SEARCH =PATHS+"good/search";
	public static final String URL_TERMINAL_OPENSTATUS =PATHS+"terminal/openStatus";

	//商户PID
	public static final String PARTNER = "2088811347108355";
	//商户收款账号
	public static final String SELLER = "ebank007@epalmpay.cn";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALjI06X8hEw9LiLqTsqmjZAqwSq/VIGJKNQgIeCCr/oReR4OePe5i2u+89PpcFe6kF2v6gWulb4WNdHYw3Iiux56sm7jUQPC1hVYXG8tiaVEb3YhX2y0YGQUS18drBBGzHnlOQlrrmlBh9ugQFzLio2NwUWo0yfcXlLoKYyteDBVAgMBAAECgYBpjW441rHLyvbbwvQXFmSvAX0uKfTfubW01lYDpSNYuTpyTNoUx8w4U+98EVC3DD8DBUWs0TmAR7eeky+xtt0jZ1O8bpHUzRi02NOw2p1ZyAHN28rvUpultfInBpbqgJDvMoWIX4AeqWQcs4gbAbPyEaWvgYM53uW7eo9CtcFMgQJBAOHGVL8Xe9agkiGwYT8e9068+xjXiloAKgQjps8fxLfMCd34sI1tEjyz0jIZ+AK4pGvU1JJdtx7s70INnubqoY0CQQDRhbFcxqaz2c+S2WUQNduFah5EZt/vdWxo4+6EHrXNdAjT7nVyA8CzreRXcPEKQZ+RhuXyXGqSLDJGKYPGQIPpAkBSmqfjCoqKqlEM9mV+HKxLKKWOHz5FU44L2adsXKkyvfpWNmkSNXfYscoT/qBZDolJ0qK7soIPVIztU+JxhiL5AkAC037U9YkCHAoEvRHz6gYQAqJt4cVbgYX41Do/Zfqlzs7frPPAmfRbeBkAZPGbZc81M1CeuEhnuFjlQWIZpn0hAkEAu1Q+fNm01qqVJ0YCMeyUoLqin/rmRAsY93cDNk82ZxY+gc3YDlcvF5qqQqcqiSSHBZkAtQqFTzx3taybP2MKjw==";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//异步通知接口  
	public static final String NOTIFT_URL = "http://121.40.84.2:8080/ZFMerchant/app_notify_url.jsp"; 
	//支付成功跳转页面
	public static final String RETURN_URL = "http://121.40.84.2:8080/ZFMerchant/return_url.jsp"; 

}
