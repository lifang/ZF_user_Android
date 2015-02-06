package com.example.zf_android.trade;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zf_android.R;

/**
 * Created by Leo on 2015/2/6.
 */
public class TradeFlowFragment extends Fragment {

	public static final String TRADE_TYPE = "trade_type";
	public static final int TRADE_TRANSFER = 1;
	public static final int TRADE_CONSUME = 2;
	public static final int TRADE_REPAY = 3;
	public static final int TRADE_LIFE_CHARGE = 4;
	public static final int TRADE_PHONE_CHARGE = 5;

	private int mTradeType;

	private OnFragmentInteractionListener mListener;

	public static TradeFlowFragment newInstance(int tradeType) {
		TradeFlowFragment fragment = new TradeFlowFragment();
		Bundle args = new Bundle();
		args.putInt(TRADE_TYPE, tradeType);
		fragment.setArguments(args);
		return fragment;
	}

	public TradeFlowFragment() {
		// Required empty public constructor
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mTradeType = getArguments().getInt(TRADE_TYPE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_trade_flow, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		TextView tv = (TextView) view.findViewById(R.id.trade_text);
		tv.setText(""+mTradeType);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
