package com.example.zf_android.trade;

import android.content.Context;

import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.HttpRequest;
import com.google.gson.Gson;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import static com.example.zf_android.trade.Constants.AfterSaleType.CANCEL;
import static com.example.zf_android.trade.Constants.AfterSaleType.CHANGE;
import static com.example.zf_android.trade.Constants.AfterSaleType.LEASE;
import static com.example.zf_android.trade.Constants.AfterSaleType.MAINTAIN;
import static com.example.zf_android.trade.Constants.AfterSaleType.RETURN;
import static com.example.zf_android.trade.Constants.AfterSaleType.UPDATE;

/**
 * Created by Leo on 2015/2/11.
 */
public class API {

    public static final String SCHEMA = "http://";
    public static final String HOST = "114.215.149.242:18080";
//    public static final String HOST = "192.168.1.101:8080";
    public static final String EDITADRESS = SCHEMA + HOST + "/ZFMerchant/api/customers/updateAddress";
 
    // selection terminal list
    public static final String TERMINAL_LIST = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTerminals/%d";
    // trade record list
    public static final String TRADE_RECORD_LIST = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecords/%d/%s/%s/%s/%d/%d";
    // trade record statistic
    public static final String TRADE_RECORD_STATISTIC = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecordTotal/%d/%s/%s/%s";
    // trade record detail
    public static final String TRADE_RECORD_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/trade/record/getTradeRecord/%d/%d";
    public static final String GETINFO = SCHEMA + HOST + "/ZFMerchant/api/customers/getOne/%d";
    // After sale record list//http://114.215.149.242:18080/ZFMerchant/api/customers/getOne/
    public static final String AFTER_SALE_MAINTAIN_LIST = SCHEMA + HOST + "/ZFMerchant/api/cs/repair/getAll";
    public static final String AFTER_SALE_RETURN_LIST = SCHEMA + HOST + "/ZFMerchant/api/return/getAll";
    public static final String AFTER_SALE_CANCEL_LIST = SCHEMA + HOST + "/ZFMerchant/api/cs/cancels/getAll";
    public static final String AFTER_SALE_CHANGE_LIST = SCHEMA + HOST + "/ZFMerchant/api/cs/change/getAll";
    public static final String AFTER_SALE_UPDATE_LIST = SCHEMA + HOST + "/ZFMerchant/api/update/info/getAll";
    public static final String AFTER_SALE_LEASE_LIST = SCHEMA + HOST + "/ZFMerchant/api/cs/lease/returns/getAll";

    // After sale record detail
    public static final String AFTER_SALE_MAINTAIN_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/cs/repair/getRepairById";
    public static final String AFTER_SALE_RETURN_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/return/getReturnById";
    public static final String AFTER_SALE_CANCEL_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/cs/cancels/getCanCelById";
    public static final String AFTER_SALE_CHANGE_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/cs/change/getChangeById";
    public static final String AFTER_SALE_UPDATE_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/update/info/getInfoById";
    public static final String AFTER_SALE_LEASE_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/cs/lease/returns/getById";

    // After sale record cancel apply
    public static final String AFTER_SALE_MAINTAIN_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/cs/repair/cancelApply";
    public static final String AFTER_SALE_RETURN_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/return/cancelApply";
    public static final String AFTER_SALE_CANCEL_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/cs/cancels/cancelApply";
    public static final String AFTER_SALE_CHANGE_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/cs/change/cancelApply";
    public static final String AFTER_SALE_UPDATE_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/update/info/cancelApply";
    public static final String AFTER_SALE_LEASE_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/cs/lease/returns/cancelApply";

    // After sale resubmit cancel
    public static final String AFTER_SALE_RESUBMIT_CANCEL = SCHEMA + HOST + "/ZFMerchant/api/cs/cancels/resubmitCancel";

    // After sale add mark
    public static final String AFTER_SALE_MAINTAIN_ADD_MARK = SCHEMA + HOST + "/ZFMerchant/api/cs/repair/addMark";
    public static final String AFTER_SALE_RETURN_ADD_MARK = SCHEMA + HOST + "/ZFMerchant/api/return/addMark";
    public static final String AFTER_SALE_CHANGE_ADD_MARK = SCHEMA + HOST + "/ZFMerchant/api/cs/change/addMark";
    public static final String AFTER_SALE_LEASE_ADD_MARK = SCHEMA + HOST + "/ZFMerchant/api/cs/lease/returns/addMark";

    // Terminal list
    public static final String TERMINAL_APPLY_LIST = SCHEMA + HOST + "/ZFMerchant/api/terminal/getApplyList";
    // Channel list
    public static final String CHANNEL_LIST = SCHEMA + HOST + "/ZFMerchant/api/terminal/getFactories";
    // Terminal Add
    public static final String TERMINAL_ADD = SCHEMA + HOST + "/ZFMerchant/api/terminal/addTerminal";
    // Terminal detail
    public static final String TERMINAL_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/terminal/getApplyDetails";

    // synchronise terminal
    public static final String TERMINAL_SYNC = SCHEMA + HOST + "/ZFMerchant/api/terminal/synchronous";
    // find pos password
    public static final String TERMINAL_FIND_POS = SCHEMA + HOST + "/ZFMerchant/api/terminal/Encryption";

    // Apply List
    public static final String APPLY_LIST = SCHEMA + HOST + "/ZFMerchant/api/apply/getApplyList";
    // Apply Detail
    public static final String APPLY_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/apply/getApplyDetails";
    // Get the Merchant Detail
    public static final String APPLY_MERCHANT_DETAIL = SCHEMA + HOST + "/ZFMerchant/api/apply/getMerchant";
    // Get the Channel List
    public static final String APPLY_CHANNEL_LIST = SCHEMA + HOST + "/ZFMerchant/api/apply/getChannels";
    // Get the Bank List
    public static final String APPLY_BANK_LIST = SCHEMA + HOST + "/ZFMerchant/api/apply/ChooseBank";

    // upload image url
    public static final String UPLOAD_IMAGE = SCHEMA + HOST + "/ZFMerchant/api/comment/upload/tempImage";
    // Apply Submit
    public static final String APPLY_SUBMIT = SCHEMA + HOST + "/ZFMerchant/api/apply/addOpeningApply";
    public static final String GETEMAILPASS = SCHEMA + HOST + "/ZFMerchant/api/user/sendEmailVerificationCode";
    // Apply Opening Progress Query  GETEMAILPASS
    public static final String APPLY_PROGRESS = SCHEMA + HOST + "/ZFMerchant/api/terminal/openStatus";
    public static final String WNATBUY = SCHEMA + HOST + "/ZFMerchant/api/paychannel/intention/add";
    public static final String Add_ress = SCHEMA + HOST + "/ZFMerchant/api/customers/insertAddress/";
    public static final String GETCODE4PHONE = SCHEMA + HOST + "/ZFMerchant/api/user/sendPhoneVerificationCodeReg";
    public static final String ZHUCHE = SCHEMA + HOST + "/ZFMerchant/api/user/userRegistration";
    public static final String GETPHONEPASS = SCHEMA + HOST + "/ZFMerchant/api/user/sendPhoneVerificationCodeFind";
    public static final String order_cart = SCHEMA + HOST + "/ZFMerchant/api/order/cart";
    //http://114.215.149.242:18080/ZFMerchant/api/order/cart
    
    
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
        params.put("customer_id", MyApplication.getInstance().getCustomerId());
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
        params.put("customer_id", MyApplication.getInstance().getCustomerId());
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
	public static void AddAdres1(
			Context context,
			String  codeNumber,
	 
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);
 
		new HttpRequest(context, callback).post(GETCODE4PHONE, params);
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
		new HttpRequest(context, callback).post("http://114.215.149.242:18080/ZFMerchant/api/customers/update/", params);
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
	
 

	
//	title
//	legalPersonName
//	legalPersonCardId
//	businessLicenseNo
//	taxRegisteredNo
//	organizationCodeNo	
//	cityId
//	accountBankName
//	bankOpenAccount
//	cardIdFrontPhotoPath
//	cardIdBackPhotoPath
//	bodyPhotoPath
//	licenseNoPicPath
//	taxNoPicPath
//	orgCodeNoPicPath
//	accountPicPath
//	customerId

	
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
			ArrayList<Integer> 	brands_id,
			ArrayList<Integer> 	category,
			ArrayList<Integer> 	pay_channel_id,
			ArrayList<Integer> pay_card_id,
			ArrayList<Integer> trade_type_id,
			ArrayList<Integer> sale_slip_id,
			ArrayList<Integer> tDate,
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
		String url = MessageFormat.format(Config.URL_MERCHANT_INFO, id);
		new HttpRequest(context, callback).post(url);
	}
    
	public static void editMerchant(
			Context context,
			MerchantEntity merchantEntity,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", merchantEntity.getTitle()==null?"":merchantEntity.getTitle());
		params.put("legalPersonName", merchantEntity.getLegal_person_name()==null?"":merchantEntity.getLegal_person_name());
		params.put("legalPersonCardId", merchantEntity.getLegal_person_card_id()==null?"":merchantEntity.getLegal_person_card_id());
		params.put("businessLicenseNo", merchantEntity.getBusiness_license_no()==null?"":merchantEntity.getBusiness_license_no());
		params.put("taxRegisteredNo", merchantEntity.getTax_registered_no()==null?"":merchantEntity.getTax_registered_no());
		params.put("organizationCodeNo", merchantEntity.getOrganization_code_no()==null?"":merchantEntity.getOrganization_code_no());
		params.put("cityId", merchantEntity.getCity_id());
		params.put("accountBankName", merchantEntity.getAccount_bank_name()==null?"":merchantEntity.getAccount_bank_name());
		params.put("bankOpenAccount", merchantEntity.getBank_open_account()==null?"":merchantEntity.getBank_open_account());
		params.put("cardIdFrontPhotoPath", merchantEntity.getCard_id_front_photo_path()==null?"":merchantEntity.getCard_id_front_photo_path());
		params.put("cardIdBackPhotoPath", merchantEntity.getCard_id_back_photo_path()==null?"":merchantEntity.getCard_id_back_photo_path());
		params.put("bodyPhotoPath", merchantEntity.getBody_photo_path()==null?"":merchantEntity.getBody_photo_path());
		params.put("licenseNoPicPath", merchantEntity.getLicense_no_pic_path()==null?"":merchantEntity.getLicense_no_pic_path());
		params.put("taxNoPicPath", merchantEntity.getTax_no_pic_path()==null?"":merchantEntity.getTax_no_pic_path());
		params.put("orgCodeNoPicPath", merchantEntity.getOrg_code_no_pic_path()==null?"":merchantEntity.getOrg_code_no_pic_path());
		params.put("accountPicPath", merchantEntity.getAccount_pic_path()==null?"":merchantEntity.getAccount_pic_path());
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
		params.put("legalPersonName", merchantEntity.getLegal_person_name()==null?"":merchantEntity.getLegal_person_name());
		params.put("legalPersonCardId", merchantEntity.getLegal_person_card_id()==null?"":merchantEntity.getLegal_person_card_id());
		params.put("businessLicenseNo", merchantEntity.getBusiness_license_no()==null?"":merchantEntity.getBusiness_license_no());
		params.put("taxRegisteredNo", merchantEntity.getTax_registered_no()==null?"":merchantEntity.getTax_registered_no());
		params.put("organizationCodeNo", merchantEntity.getOrganization_code_no()==null?"":merchantEntity.getOrganization_code_no());
		params.put("cityId", merchantEntity.getCity_id());
		params.put("accountBankName", merchantEntity.getAccount_bank_name()==null?"":merchantEntity.getAccount_bank_name());
		params.put("bankOpenAccount", merchantEntity.getBank_open_account()==null?"":merchantEntity.getBank_open_account());
		params.put("cardIdFrontPhotoPath", merchantEntity.getCard_id_front_photo_path()==null?"":merchantEntity.getCard_id_front_photo_path());
		params.put("cardIdBackPhotoPath", merchantEntity.getCard_id_back_photo_path()==null?"":merchantEntity.getCard_id_back_photo_path());
		params.put("bodyPhotoPath", merchantEntity.getBody_photo_path()==null?"":merchantEntity.getBody_photo_path());
		params.put("licenseNoPicPath", merchantEntity.getLicense_no_pic_path()==null?"":merchantEntity.getLicense_no_pic_path());
		params.put("taxNoPicPath", merchantEntity.getTax_no_pic_path()==null?"":merchantEntity.getTax_no_pic_path());
		params.put("orgCodeNoPicPath", merchantEntity.getOrg_code_no_pic_path()==null?"":merchantEntity.getOrg_code_no_pic_path());
		params.put("accountPicPath", merchantEntity.getAccount_pic_path()==null?"":merchantEntity.getAccount_pic_path());
		params.put("customerId",  customerId);
		new HttpRequest(context, callback).post(Config.URL_MERCHANT_CREATE, params);
	}

}

