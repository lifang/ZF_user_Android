package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.AfterSaleType.CANCEL;
import static com.example.zf_android.trade.Constants.AfterSaleType.CHANGE;
import static com.example.zf_android.trade.Constants.AfterSaleType.LEASE;
import static com.example.zf_android.trade.Constants.AfterSaleType.MAINTAIN;
import static com.example.zf_android.trade.Constants.AfterSaleType.RETURN;
import static com.example.zf_android.trade.Constants.AfterSaleType.UPDATE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

import com.example.zf_android.Config;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.HttpRequest;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

/**
 * Created by Leo on 2015/2/11.
 */
@SuppressWarnings("rawtypes")
public class API {

	public static final String SCHEMA = "http://";

	//	public static final String HOST = "114.215.149.242:18080";
	//sit   
	//	public final static String HOST = "www.ebank007.com/api/";
		public static final String HOST = "121.40.84.2:8080/ZFMerchant/api/";
//	public final static String HOST = "121.40.64.167:8080/api/";
	public static final String EDITADRESS = SCHEMA + HOST + "customers/updateAddress";

	// selection terminal list
	public static final String TERMINAL_LIST = SCHEMA + HOST + "trade/record/getTerminals/%d";
	// trade record list
	public static final String TRADE_RECORD_LIST = SCHEMA + HOST + "trade/record/getTradeRecords/%d/%s/%s/%s/%d/%d";
	// trade record statistic
	public static final String TRADE_RECORD_STATISTIC = SCHEMA + HOST + "trade/record/getTradeRecordTotal/%d/%s/%s/%s";
	// trade record detail
	public static final String TRADE_RECORD_DETAIL = SCHEMA + HOST + "trade/record/getTradeRecord/%d/%d";
	public static final String GETINFO = SCHEMA + HOST + "customers/getOne/%d";

	public static final String AFTER_SALE_MAINTAIN_LIST = SCHEMA + HOST + "cs/repair/getAll";
	public static final String AFTER_SALE_RETURN_LIST = SCHEMA + HOST + "return/getAll";
	public static final String AFTER_SALE_CANCEL_LIST = SCHEMA + HOST + "cs/cancels/getAll";
	public static final String AFTER_SALE_CHANGE_LIST = SCHEMA + HOST + "cs/change/getAll";
	public static final String AFTER_SALE_UPDATE_LIST = SCHEMA + HOST + "update/info/getAll";
	public static final String AFTER_SALE_LEASE_LIST = SCHEMA + HOST + "cs/lease/returns/getAll";

	// After sale record detail
	public static final String AFTER_SALE_MAINTAIN_DETAIL = SCHEMA + HOST + "cs/repair/getRepairById";
	public static final String AFTER_SALE_RETURN_DETAIL = SCHEMA + HOST + "return/getReturnById";
	public static final String AFTER_SALE_CANCEL_DETAIL = SCHEMA + HOST + "cs/cancels/getCanCelById";
	public static final String AFTER_SALE_CHANGE_DETAIL = SCHEMA + HOST + "cs/change/getChangeById";
	public static final String AFTER_SALE_UPDATE_DETAIL = SCHEMA + HOST + "update/info/getInfoById";
	public static final String AFTER_SALE_LEASE_DETAIL = SCHEMA + HOST + "cs/lease/returns/getById";

	// After sale record cancel apply
	public static final String AFTER_SALE_MAINTAIN_CANCEL = SCHEMA + HOST + "cs/repair/cancelApply";
	public static final String AFTER_SALE_RETURN_CANCEL = SCHEMA + HOST + "return/cancelApply";
	public static final String AFTER_SALE_CANCEL_CANCEL = SCHEMA + HOST + "cs/cancels/cancelApply";
	public static final String AFTER_SALE_CHANGE_CANCEL = SCHEMA + HOST + "cs/change/cancelApply";
	public static final String AFTER_SALE_UPDATE_CANCEL = SCHEMA + HOST + "update/info/cancelApply";
	public static final String AFTER_SALE_LEASE_CANCEL = SCHEMA + HOST + "cs/lease/returns/cancelApply";

	// After sale resubmit cancel
	public static final String AFTER_SALE_RESUBMIT_CANCEL = SCHEMA + HOST + "cs/cancels/resubmitCancel";

	// After sale add mark
	public static final String AFTER_SALE_MAINTAIN_ADD_MARK = SCHEMA + HOST + "cs/repair/addMark";
	public static final String AFTER_SALE_RETURN_ADD_MARK = SCHEMA + HOST + "return/addMark";
	public static final String AFTER_SALE_CHANGE_ADD_MARK = SCHEMA + HOST + "cs/change/addMark";
	public static final String AFTER_SALE_LEASE_ADD_MARK = SCHEMA + HOST + "cs/lease/returns/addMark";

	// Terminal list
	public static final String TERMINAL_APPLY_LIST = SCHEMA + HOST + "terminal/getApplyList";
	// Channel list
	public static final String CHANNEL_LIST = SCHEMA + HOST + "terminal/getFactories";
	// Terminal Add
	public static final String TERMINAL_ADD = SCHEMA + HOST + "terminal/addTerminal";
	// Terminal detail
	public static final String TERMINAL_DETAIL = SCHEMA + HOST + "terminal/getApplyDetails";

	// synchronise terminal
	public static final String TERMINAL_SYNC = SCHEMA + HOST + "terminal/synchronous";
	// find pos password
	public static final String TERMINAL_FIND_POS = SCHEMA + HOST + "terminal/Encryption";

	// Apply List
	public static final String APPLY_LIST = SCHEMA + HOST + "apply/getApplyList";
	// Apply Detail
	public static final String APPLY_DETAIL = SCHEMA + HOST + "apply/getApplyDetails";
	// Get the Merchant Detail
	public static final String APPLY_MERCHANT_DETAIL = SCHEMA + HOST + "apply/getMerchant";
	// Get the Channel List
	public static final String APPLY_CHANNEL_LIST = SCHEMA + HOST + "apply/getChannels";
	// Get the Bank List
	public static final String APPLY_BANK_LIST = SCHEMA + HOST + "apply/ChooseBank";

	// upload image url
	public static final String UPLOAD_IMAGE = SCHEMA + HOST + "comment/upload/tempImage";
	// Apply Submit
	public static final String APPLY_SUBMIT = SCHEMA + HOST + "apply/addOpeningApply";
	public static final String GETEMAILPASS = SCHEMA + HOST + "user/sendEmailVerificationCode";
	// Apply Opening Progress Query  GETEMAILPASS
	public static final String APPLY_PROGRESS = SCHEMA + HOST + "terminal/openStatus";
	public static final String WNATBUY = SCHEMA + HOST + "paychannel/intention/add";
	public static final String Add_ress = SCHEMA + HOST + "customers/insertAddress/";
	public static final String REG_PHONECODE = SCHEMA + HOST + "user/sendPhoneVerificationCodeReg";
	public static final String ZHUCHE = SCHEMA + HOST + "user/userRegistration";
	public static final String GETPHONEPASS = SCHEMA + HOST + "user/sendPhoneVerificationCodeFind";
	public static final String order_cart = SCHEMA + HOST + "order/cart";
	public static final String GET_PHONECODE = SCHEMA + HOST + "index/getPhoneCode";
	public static final String GET_UPDATEEMAILDENTCODE = SCHEMA + HOST + "customers/getUpdateEmailDentcode";
	//http://114.215.149.242:18080order/cart


	public static void getTerminalList(
			Context context,
			int customerId,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(String.format(TERMINAL_LIST, customerId));
	}

	public static void getTradeRecordList(
			Context context,
			int tradeTypeId,
			String terminalNumber,
			String startTime,
			String endTime,
			int page,
			int rows,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(String.format(TRADE_RECORD_LIST,
				tradeTypeId, terminalNumber, startTime, endTime, page, rows));
	}

	public static void getTradeRecordStatistic(
			Context context,
			int tradeTypeId,
			String terminalNumber,
			String startTime,
			String endTime,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(String.format(TRADE_RECORD_STATISTIC,
				tradeTypeId, terminalNumber, startTime, endTime));
	}

	public static void getTradeRecordDetail(
			Context context,
			int typeId,
			int recordId,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(String.format(TRADE_RECORD_DETAIL, typeId, recordId));
	}

	public static void getAfterSaleRecordList(
			Context context,
			int recordType,
			int customId,
			int page,
			int rows,
			HttpCallback callback) {
		String url;
		switch (recordType) {
		case MAINTAIN:
			url = AFTER_SALE_MAINTAIN_LIST;
			break;
		case RETURN:
			url = AFTER_SALE_RETURN_LIST;
			break;
		case CANCEL:
			url = AFTER_SALE_CANCEL_LIST;
			break;
		case CHANGE:
			url = AFTER_SALE_CHANGE_LIST;
			break;
		case UPDATE:
			url = AFTER_SALE_UPDATE_LIST;
			break;
		case LEASE:
			url = AFTER_SALE_LEASE_LIST;
			break;
		default:
			throw new IllegalArgumentException("illegal argument [recordType], within [0-5]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void getAfterSaleRecordDetail(
			Context context,
			int recordType,
			int recordId,
			HttpCallback callback) {
		String url;
		switch (recordType) {
		case MAINTAIN:
			url = AFTER_SALE_MAINTAIN_DETAIL;
			break;
		case RETURN:
			url = AFTER_SALE_RETURN_DETAIL;
			break;
		case CANCEL:
			url = AFTER_SALE_CANCEL_DETAIL;
			break;
		case CHANGE:
			url = AFTER_SALE_CHANGE_DETAIL;
			break;
		case UPDATE:
			url = AFTER_SALE_UPDATE_DETAIL;
			break;
		case LEASE:
			url = AFTER_SALE_LEASE_DETAIL;
			break;
		default:
			throw new IllegalArgumentException("illegal argument [recordType], within [0-5]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void cancelAfterSaleApply(
			Context context,
			int recordType,
			int recordId,
			HttpCallback callback) {
		String url;
		switch (recordType) {
		case MAINTAIN:
			url = AFTER_SALE_MAINTAIN_CANCEL;
			break;
		case RETURN:
			url = AFTER_SALE_RETURN_CANCEL;
			break;
		case CANCEL:
			url = AFTER_SALE_CANCEL_CANCEL;
			break;
		case CHANGE:
			url = AFTER_SALE_CHANGE_CANCEL;
			break;
		case UPDATE:
			url = AFTER_SALE_UPDATE_CANCEL;
			break;
		case LEASE:
			url = AFTER_SALE_LEASE_CANCEL;
			break;
		default:
			throw new IllegalArgumentException("illegal argument [recordType], within [0-5]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void resubmitCancel(
			Context context,
			int recordId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(AFTER_SALE_RESUBMIT_CANCEL, params);
	}

	public static void addMark(
			Context context,
			int recordType,
			int recordId,
			int customerId,
			String company,
			String number,
			HttpCallback callback) {
		String url;
		switch (recordType) {
		case MAINTAIN:
			url = AFTER_SALE_MAINTAIN_ADD_MARK;
			break;
		case RETURN:
			url = AFTER_SALE_RETURN_ADD_MARK;
			break;
		case CHANGE:
			url = AFTER_SALE_CHANGE_ADD_MARK;
			break;
		case LEASE:
			url = AFTER_SALE_LEASE_ADD_MARK;
			break;
		default:
			throw new IllegalArgumentException("illegal argument [recordType], within [0,1,3,5]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		params.put("customer_id", customerId);
		params.put("computer_name", company);
		params.put("track_number", number);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void getTerminalApplyList(
			Context context,
			int customerId,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customersId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(TERMINAL_APPLY_LIST, params);
	}
	public static void synchronous(
			Context context,
			int terminalId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalId", terminalId);
		new HttpRequest(context, callback).post(TERMINAL_SYNC, params);
	}

	public static void getChannelList(
			Context context,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(CHANNEL_LIST);
	}

	public static void addTerminal(
			Context context,
			int customerId,
			int payChannelId,
			String terminalNumber,
			String merchantName,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("payChannelId", payChannelId);
		params.put("serialNum", terminalNumber);
		params.put("title", merchantName);
		new HttpRequest(context, callback).post(TERMINAL_ADD, params);
	}

	public static void getTerminalDetail(
			Context context,
			int terminalId,
			int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalsId", terminalId);
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(TERMINAL_DETAIL, params);
	}

	public static void findPosPassword(
			Context context,
			int terminalId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalid", terminalId);
		new HttpRequest(context, callback).post(TERMINAL_FIND_POS, params);
	}

	public static void getApplyList(
			Context context,
			int customerId,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customersId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(APPLY_LIST, params);
	}

	public static void getApplyDetail(
			Context context,
			int customerId,
			int terminalId,
			int status,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("terminalsId", terminalId);
		params.put("status", status);
		new HttpRequest(context, callback).post(APPLY_DETAIL, params);
	}

	public static void getApplyMerchantDetail(
			Context context,
			int merchantId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantId", merchantId);
		new HttpRequest(context, callback).post(APPLY_MERCHANT_DETAIL, params);
	}

	public static void getApplyChannelList(
			Context context,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(APPLY_CHANNEL_LIST);
	}

	public static void getApplyBankList(
			Context context,
			String terminalNumber,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serial_num", terminalNumber);
		new HttpRequest(context, callback).post(APPLY_BANK_LIST, params);
	}

	public static void submitApply(
			Context context,
			Map<String, Object> params,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(APPLY_SUBMIT, params);
	}

	public static void queryApplyProgress(
			Context context,
			int customerId,
			String phone,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", customerId);
		params.put("phone", phone);
		new HttpRequest(context, callback).post(APPLY_PROGRESS, params);
	}
	public static void ApiWantBug(
			Context context,
			String  name,
			String phone,
			String content,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("phone", phone);
		params.put("content", content);
		new HttpRequest(context, callback).post(WNATBUY, params);
	}

	public static void AddAdres(
			Context context,
			String  cityId,
			String  receiver,
			String 	moblephone,
			String 	zipCode,
			String 	address,
			int 	isDefault,
			int 	customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		params.put("receiver", receiver);
		params.put("moblephone", moblephone);
		params.put("zipCode", zipCode);
		params.put("address", address);
		params.put("isDefault", isDefault);
		params.put("customerId", customerId);
		System.out.println("--ccc----"+params);
		new HttpRequest(context, callback).post(Add_ress, params);
	}
	public static void EditAdres(
			Context context,
			String  cityId,
			String  receiver,
			String 	moblephone,
			String 	zipCode,
			String 	address,
			int 	isDefault,
			int 	customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		params.put("receiver", receiver);
		params.put("moblephone", moblephone);
		params.put("zipCode", zipCode);
		params.put("address", address);
		params.put("isDefault", isDefault);
		params.put("id", customerId);
		System.out.println("--ccc----"+params);
		new HttpRequest(context, callback).post(EDITADRESS, params);
	}
	public static void reg_phoneCode(
			Context context,
			String  codeNumber,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);

		new HttpRequest(context, callback).post(REG_PHONECODE, params);
	}
	public static void getUpdateEmailDentcode(
			Context context,
			int  id,
			String  email,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("email", email);
		new HttpRequest(context, callback).post(GET_UPDATEEMAILDENTCODE, params);
	}
	public static void getPhoneCode(
			Context context,
			String  phone,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		new HttpRequest(context, callback).post(GET_PHONECODE, params);
	}
	public static void getEmailPass(
			Context context,
			String  codeNumber,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);

		new HttpRequest(context, callback).post(GETEMAILPASS, params);
	}
	//	params.put("password",pass);
	//	params.put("code",vcode); 
	//	params.put("username", email); 
	public static void PhonefindPass(
			Context context,
			String  password,
			String code,
			String username,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", password);
		params.put("code", code);
		params.put("username", username);
		new HttpRequest(context, callback).post(Config.updatePassword, params);
	}
	public static void Login1(
			Context context,
			String  username,
			String  passsword,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", passsword);
		new HttpRequest(context, callback).post(Config.URL_LOGIN, params);
	}
	public static void ChangePass(
			Context context,
			int id,
			String  passwordOld,
			String  passsword,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("passwordOld", passwordOld);
		params.put("password", passsword);
		new HttpRequest(context, callback).post(Config.CHANGEPASS, params);
	}
	public static void ChangeMyInfo(
			Context context,
			int id,
			String  name,
			String  phone,
			String  email,
			int  cityId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		params.put("name", name);
		params.put("phone", phone);
		params.put("email", email);
		params.put("cityId", cityId);


		System.out.println("鍏ュ弬---"+params);
		new HttpRequest(context, callback).post(Config.URL_CUSTOMERS_UPDATE, params);
	}

	public static void zhuche(
			Context context,
			String  username,

			String  password,
			String  code,
			int  cityId,
			Boolean accountType,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);

		params.put("password", password);
		params.put("code", code);
		params.put("cityId", cityId);
		params.put("accountType", accountType);



		new HttpRequest(context, callback).post(ZHUCHE, params);
	}


	public static void goodConfirm(
			Context context,
			int customerId,
			int goodId,
			int paychannelId,
			int quantity,
			int addressId,
			String  comment,
			int is_need_invoice,
			int invoice_type,
			String  invoice_info,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId); 
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("quantity", quantity);
		params.put("addressId", addressId);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", invoice_info);
		new HttpRequest(context, callback).post(Config.URL_ORDER_SHOP, params);
	}

	public static void getinfo(
			Context context,
			int id,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(String.format(GETINFO, id));
	}


	public static void CARTFIRM(
			Context context,
			int customerId,
			int[]  inn,
			int addressId,
			String  comment,
			int is_need_invoice,
			int invoice_type,
			String  invoice_info,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId); 
		Gson gson = new Gson();
		try {
			params.put("cartid", new JSONArray(gson.toJson(inn)));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		params.put("addressId", addressId);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", invoice_info);
		new HttpRequest(context, callback).post(Config.URL_ORDER_CART, params);
	}

	public static void NewMerchant(
			Context context,
			String  title,
			String  legalPersonName,
			String 	legalPersonCardId,
			String 	businessLicenseNo,
			String 	taxRegisteredNo,
			String organizationCodeNo,
			int cityId,
			String accountBankName,
			String bankOpenAccount,
			String cardIdFrontPhotoPath,
			String cardIdBackPhotoPath,
			String bodyPhotoPath,
			String licenseNoPicPath,
			String taxNoPicPath,
			String orgCodeNoPicPath,
			String accountPicPath,
			int 	customerId,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("legalPersonName", legalPersonName);
		params.put("legalPersonCardId", legalPersonCardId);
		params.put("businessLicenseNo", businessLicenseNo);
		params.put("taxRegisteredNo", taxRegisteredNo);
		params.put("organizationCodeNo", organizationCodeNo);
		params.put("cityId", cityId);
		params.put("accountBankName", accountBankName);
		params.put("bankOpenAccount", bankOpenAccount);

		params.put("cardIdFrontPhotoPath", cardIdFrontPhotoPath);
		params.put("cardIdBackPhotoPath", cardIdBackPhotoPath);
		params.put("bodyPhotoPath", bodyPhotoPath);
		params.put("licenseNoPicPath", licenseNoPicPath);
		params.put("taxNoPicPath", taxNoPicPath);
		params.put("orgCodeNoPicPath", orgCodeNoPicPath);
		params.put("accountPicPath", accountPicPath);
		params.put("customerId", customerId);

		new HttpRequest(context, callback).post(EDITADRESS, params);
	}





	public static void CARTFIRM1(

			Context context,
			int customerId,
			ArrayList<Integer>   cartid,
			int addressId,
			String  comment,
			int is_need_invoice,
			int invoice_type,
			String  invoice_info,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("cartid",  cartid);
		params.put("addressId", addressId);
		params.put("comment", comment);
		params.put("is_need_invoice",is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", invoice_info);

		System.out.println("CCC--"+params.toString());
		new HttpRequest(context, callback).post(Config.URL_ORDER_SHOP, params);
	}

	public static void postList(
			Context context,
			int  city_id,
			int  orderType,
			int[] brands_id,
			int[] category,
			int[] pay_channel_id,
			int[] pay_card_id,
			int[] trade_type_id,
			int[] sale_slip_id,
			int[] tDate,
			int has_purchase,
			double minPrice,
			double maxPrice,
			String keys,
			int page,
			int rows,

			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city_id", city_id);
		params.put("orderType", orderType);
		params.put("has_purchase", has_purchase);
		params.put("minPrice", minPrice);
		params.put("maxPrice", maxPrice);
		params.put("keys", keys);
		params.put("page", page);
		params.put("rows", rows);
		Gson gson = new Gson();
		try {
			if (brands_id != null) 
				params.put("brands_id", new JSONArray(gson.toJson(brands_id)));
			if (category != null){ 
				params.put("category", category[0]);
			}
			if (pay_channel_id != null) 
				params.put("pay_channel_id", new JSONArray(gson.toJson(pay_channel_id)));
			if (pay_card_id != null) 
				params.put("pay_card_id", new JSONArray(gson.toJson(pay_card_id)));
			if (trade_type_id != null) 
				params.put("trade_type_id", new JSONArray(gson.toJson(trade_type_id)));
			if (sale_slip_id != null) 
				params.put("sale_slip_id", new JSONArray(gson.toJson(sale_slip_id)));
			if (tDate != null) 
				params.put("tDate", new JSONArray(gson.toJson(tDate)));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("--ccc----"+params);
		new HttpRequest(context, callback).post(Config.URL_GOOD_LIST, params);
	}




	public static void checkVersion(Context context, HttpCallback callback){
		new HttpRequest(context, callback).post(Config.URL_CHECK_VERSION);
	}


	public static void merchantInfo(
			Context context,
			int  id,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		String url = MessageFormat.format(Config.URL_MERCHANT_INFO, id+"");
		new HttpRequest(context, callback).post(url);
	}

	public static void editMerchant(
			Context context,
			MerchantEntity merchantEntity,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", merchantEntity.getTitle()==null?"":merchantEntity.getTitle());
		params.put("legalPersonName", merchantEntity.getLegalPersonName()==null?"":merchantEntity.getLegalPersonName());
		params.put("legalPersonCardId", merchantEntity.getLegalPersonCardId()==null?"":merchantEntity.getLegalPersonCardId());
		params.put("businessLicenseNo", merchantEntity.getBusinessLicenseNo()==null?"":merchantEntity.getBusinessLicenseNo());
		params.put("taxRegisteredNo", merchantEntity.getTaxRegisteredNo()==null?"":merchantEntity.getTaxRegisteredNo());
		params.put("organizationCodeNo", merchantEntity.getOrganizationCodeNo()==null?"":merchantEntity.getOrganizationCodeNo());
		params.put("cityId", merchantEntity.getCityId());
		params.put("accountBankName", merchantEntity.getAccountBankName()==null?"":merchantEntity.getAccountBankName());
		params.put("bankOpenAccount", merchantEntity.getBankOpenAccount()==null?"":merchantEntity.getBankOpenAccount());
		params.put("cardIdFrontPhotoPath", merchantEntity.getCardIdFrontPhotoPath()==null?"":merchantEntity.getCardIdFrontPhotoPath());
		params.put("cardIdBackPhotoPath", merchantEntity.getCardIdBackPhotoPath()==null?"":merchantEntity.getCardIdBackPhotoPath());
		params.put("bodyPhotoPath", merchantEntity.getBodyPhotoPath()==null?"":merchantEntity.getBodyPhotoPath());
		params.put("licenseNoPicPath", merchantEntity.getLicenseNoPicPath()==null?"":merchantEntity.getLicenseNoPicPath());
		params.put("taxNoPicPath", merchantEntity.getTaxNoPicPath()==null?"":merchantEntity.getTaxNoPicPath());
		params.put("orgCodeNoPicPath", merchantEntity.getOrgCodeNoPicPath()==null?"":merchantEntity.getOrgCodeNoPicPath());
		params.put("accountPicPath", merchantEntity.getAccountPicPath()==null?"":merchantEntity.getAccountPicPath());
		params.put("id", merchantEntity.getId());
		new HttpRequest(context, callback).post(Config.URL_MERCHANT_EDIT, params);
	}

	public static void createMerchant(
			Context context,
			int customerId,
			MerchantEntity merchantEntity,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", merchantEntity.getTitle()==null?"":merchantEntity.getTitle());
		params.put("legalPersonName", merchantEntity.getLegalPersonName()==null?"":merchantEntity.getLegalPersonName());
		params.put("legalPersonCardId", merchantEntity.getLegalPersonCardId()==null?"":merchantEntity.getLegalPersonCardId());
		params.put("businessLicenseNo", merchantEntity.getBusinessLicenseNo()==null?"":merchantEntity.getBusinessLicenseNo());
		params.put("taxRegisteredNo", merchantEntity.getTaxRegisteredNo()==null?"":merchantEntity.getTaxRegisteredNo());
		params.put("organizationCodeNo", merchantEntity.getOrganizationCodeNo()==null?"":merchantEntity.getOrganizationCodeNo());
		params.put("cityId", merchantEntity.getCityId());
		params.put("accountBankName", merchantEntity.getAccountBankName()==null?"":merchantEntity.getAccountBankName());
		params.put("bankOpenAccount", merchantEntity.getBankOpenAccount()==null?"":merchantEntity.getBankOpenAccount());
		params.put("cardIdFrontPhotoPath", merchantEntity.getCardIdFrontPhotoPath()==null?"":merchantEntity.getCardIdFrontPhotoPath());
		params.put("cardIdBackPhotoPath", merchantEntity.getCardIdBackPhotoPath()==null?"":merchantEntity.getCardIdBackPhotoPath());
		params.put("bodyPhotoPath", merchantEntity.getBodyPhotoPath()==null?"":merchantEntity.getBodyPhotoPath());
		params.put("licenseNoPicPath", merchantEntity.getLicenseNoPicPath()==null?"":merchantEntity.getLicenseNoPicPath());
		params.put("taxNoPicPath", merchantEntity.getTaxNoPicPath()==null?"":merchantEntity.getTaxNoPicPath());
		params.put("orgCodeNoPicPath", merchantEntity.getOrgCodeNoPicPath()==null?"":merchantEntity.getOrgCodeNoPicPath());
		params.put("accountPicPath", merchantEntity.getAccountPicPath()==null?"":merchantEntity.getAccountPicPath());
		params.put("customerId",  customerId);
		new HttpRequest(context, callback).post(Config.URL_MERCHANT_CREATE, params);
	}
	/*
	 * niemin
	 */
	//我的订单--订单列表
	public static void getMyOrderAll(
			Context context,
			int customerId,
			String p,
			int page,
			int pageSize,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customerId); 
		params.put("p", p);
		params.put("page", page);
		params.put("pageSize", pageSize);
		new HttpRequest(context, callback).post(Config.URL_ORDER_LIST, params);
	}
	//我的订单--取消订单
	public static void cancelMyOrder(
			Context context,
			int id,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id); 
		new HttpRequest(context, callback).post(Config.URL_CANCEL_MYORDER, params);
	}
	//我的订单--订单详情
	public static void getMyOrderById(
			Context context,
			int id,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id); 
		new HttpRequest(context, callback).post(Config.URL_GET_MYORDERBYID, params);
	}
	//我的订单--评论订单--批量保存
	public static void batchSaveComment(
			Context context,
			int id,
			JSONArray json,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id); 
		params.put("json", json);
		new HttpRequest(context, callback).post(Config.URL_BATCH_SAVECOMMENT, params);
	}
	//我的信息--我的积分列表
	public static void getIntegralList(
			Context context,
			int customerId,
			int page,
			int rows,
			HttpCallback callback) {
		String url = MessageFormat.format(Config.URL_GET_INTEGRALLIST,customerId+"", page, rows);
		new HttpRequest(context, callback).post(url);
	}
	//我的信息--我的积分总计
	public static void getIntegralTotal(
			Context context,
			int customerId,
			HttpCallback callback) {
		String url = MessageFormat.format(Config.URL_GET_INTEGRALTOTAL,customerId+"");
		new HttpRequest(context, callback).post(url);
	}
	//我的信息--提交积分兑换
	public static void insertIntegralConvert(
			Context context,
			int customerId,
			String name,
			String phone,
			String price,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("name", name);
		params.put("phone", phone);
		params.put("price", price);
		new HttpRequest(context, callback).post(Config.URL_INSERT_INTEGRALCONVERT, params);
	}
	//我的信息--地址列表
	public static void getAddressList(
			Context context,
			int customerId,
			HttpCallback callback) {
		String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId+"");
		new HttpRequest(context, callback).post(url);
	}
	//我的信息--删除地址
	public static void deleteAddress(
			Context context,
			JSONArray ids,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		new HttpRequest(context, callback).post(Config.ADRESSDelete, params);
	}
	//我的商户--商户列表
	public static void merchantGetList(
			Context context,
			int customerId,
			int page,
			int rows,
			HttpCallback callback) {
		String url = MessageFormat.format(Config.URL_MERCHANT_LIST,customerId+"", page, rows);
		new HttpRequest(context, callback).post(url);
	}
	//我的商户--删除商户
	public static void merchantDelete(
			Context context,
			JSONArray ids,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		new HttpRequest(context, callback).post(Config.URL_MERCHANT_DELETE, params);
	}
	//我的消息---消息列表
	public static void receiverGetAll(
			Context context,
			int customer_id,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(Config.getmes, params);
	}
	//我的消息---批量已读
	public static void batchRead(
			Context context,
			int customer_id,
			JSONArray ids,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("ids", ids);
		new HttpRequest(context, callback).post(Config.batchRead, params);
	}
	//我的消息---批量删除
	public static void batchDelete(
			Context context,
			int customer_id,
			JSONArray ids,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("ids", ids);
		new HttpRequest(context, callback).post(Config.batchDelete, params);
	}
	//我的消息---消息详情
	public static void receiverGetById(
			Context context,
			int customer_id,
			int id,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("id", id);
		new HttpRequest(context, callback).post(Config.getMSGById, params);
	}
	//我的消息---单个删除
	public static void receiverDeleteById(
			Context context,
			int customer_id,
			int id,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("id", id);
		new HttpRequest(context, callback).post(Config.DELETEMSG, params);
	}
	//找回密码--获得手机验证码
	public static void sendPhoneCode(
			Context context,
			String  codeNumber,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);
		new HttpRequest(context, callback).post(Config.URL_SEND_PHONECODE, params);
	}
	//购物车列表
	public static void cartList(
			Context context,
			int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(Config.URL_CART_LIST, params);
	}

	public static void noticeVideo(
			Context context,
			int terminalId) {
		RequestParams params = new RequestParams();
		params.put("terminalId", terminalId);
		new HttpRequest(context, null).post(Config.URL_NOTICE_VIDEO, params);
	}
	public static void getRepairPay(
			Context context,
			int id,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id); 
		new HttpRequest(context, callback).post(Config.URL_REPAIRPAY, params);
	}
}
