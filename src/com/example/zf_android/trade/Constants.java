package com.example.zf_android.trade;

public class Constants {

	/**
	 * The intent data among city selection
	 */
	public static class CityIntent {
		public static final String SELECTED_PROVINCE = "selected_province";
		public static final String SELECTED_CITY = "selected_city";

		public static final String CITY_ID = "city_id";
		public static final String CITY_NAME = "city_name";
	}

	/**
	 * The type of trade
	 */
	public static class TradeType {
		public static final int TRANSFER = 1;
		public static final int CONSUME = 3;
		public static final int REPAYMENT = 2;
		public static final int LIFE_PAY = 4;
		public static final int PHONE_PAY = 5;
	}

	/**
	 * The intent data among trade activities
	 */
	public static class TradeIntent {
		public static final int REQUEST_TRADE_CLIENT = 0;

		public static final String TRADE_TYPE = "trade_type";
		public static final String TRADE_RECORD_ID = "trade_record_id";
		public static final String CLIENT_NUMBER = "client_number";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
	}

	/**
	 * The type of after-sale records
	 */
	public static class AfterSaleType {
		public static final int MAINTAIN = 0;
		public static final int RETURN = 1;
		public static final int CANCEL = 2;
		public static final int CHANGE = 3;
		public static final int UPDATE = 4;
		public static final int LEASE = 5;
	}

	/**
	 * The intent data among after-sale activities
	 */
	public static class AfterSaleIntent {
		public static final int REQUEST_DETAIL = 0;
		public static final int REQUEST_MARK = 1;

		public static final String RECORD_TYPE = "record_type";
		public static final String RECORD_ID = "record_id";
		public static final String RECORD_STATUS = "record_status";
		public static final String MATERIAL_URL = "material_url";
	}
}
