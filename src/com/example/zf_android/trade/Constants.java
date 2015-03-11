package com.example.zf_android.trade;

public class Constants {

	public static class ShowWebImageIntent {
		public static final String IMAGE_URLS = "image_urls";
		public static final String IMAGE_NAMES = "image_names";
		public static final String POSITION = "position";
	}

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

	/**
	 * The status of terminal
	 */
	public static class TerminalStatus {
		public static final int OPENED = 1;
		public static final int PART_OPENED = 2;
		public static final int UNOPENED = 3;
		public static final int CANCELED = 4;
		public static final int STOPPED = 5;
	}

	/**
	 * The intent data among terminal activities
	 */
	public static class TerminalIntent {
		public static final int REQUEST_ADD = 0;
		public static final int REQUEST_DETAIL = 1;

		public static final String TERMINAL_ID = "terminal_id";
		public static final String TERMINAL_STATUS = "terminal_status";
		public static final String CHANNEL_ID = "channel_id";
		public static final String CHANNEL_NAME = "channel_name";
	}

	/**
	 * The intent data among apply activities
	 */
	public static class ApplyIntent {
		public static final int REQUEST_CHOOSE_MERCHANT = 0x1001;
		public static final int REQUEST_CHOOSE_CITY = 0x1002;
		public static final int REQUEST_CHOOSE_CHANNEL = 0x1003;
		public static final int REQUEST_UPLOAD_IMAGE = 0x1004;
		public static final int REQUEST_TAKE_PHOTO = 0x1005;

		public static final String CHOOSE_TITLE = "choose_title";
		public static final String CHOOSE_ITEMS = "choose_items";
		public static final String SELECTED_ID = "selected_id";
		public static final String SELECTED_TITLE = "selected_title";

	}

}
