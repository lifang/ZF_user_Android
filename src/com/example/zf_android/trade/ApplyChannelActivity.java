package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_BILLING;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_CHANNEL;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_CHANNEL_ID;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.ApplyChannel;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/3/16.
 */
public class ApplyChannelActivity extends BaseActivity {

	private List<ApplyChannel> channels = new ArrayList<ApplyChannel>();
	private List<ApplyChannel.Billing> billings = new ArrayList<ApplyChannel.Billing>();
	private ListView channelList;
	private ListView billingList;
	private ChannelListAdapter channelAdapter;
	private BillingListAdapter billingAdapter;
	private ApplyChannel chosenChannel;
	private ApplyChannel.Billing chosenBilling;

	private int mPayChannelID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_channel);
		new TitleMenuUtil(this, getString(R.string.title_apply_choose_channel))
				.show();
		chosenChannel = (ApplyChannel) getIntent().getSerializableExtra(
				SELECTED_CHANNEL);
		chosenBilling = (ApplyChannel.Billing) getIntent()
				.getSerializableExtra(SELECTED_BILLING);
		mPayChannelID = getIntent().getIntExtra(SELECTED_CHANNEL_ID, 0);
		channelList = (ListView) findViewById(R.id.apply_channel_list);
		channelList.addFooterView(new View(this));
		channelAdapter = new ChannelListAdapter();
		channelList.setAdapter(channelAdapter);

		// if (chosenChannel != null && chosenChannel.getId() > 0) {
		// channelList.setSelection(chosenChannel.getId());
		// }
		channelList.setSelection(0);
		billingList = (ListView) findViewById(R.id.apply_billing_list);
		billingList.addFooterView(new View(this));
		billingAdapter = new BillingListAdapter();
		billingList.setAdapter(billingAdapter);

		channelList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						chosenChannel = (ApplyChannel) view
								.getTag(R.id.item_id);
						billings.clear();
						if (null != chosenChannel.getBillings()) {
							for (ApplyChannel.Billing billing : chosenChannel
									.getBillings()) {
								if (null != billing) {
									billings.add(billing);
								}
							}
						} else {
							Intent intent = new Intent();
							intent.putExtra(SELECTED_CHANNEL, chosenChannel);
							intent.putExtra(SELECTED_BILLING, chosenBilling);
							setResult(RESULT_OK, intent);
							finish();
						}

						channelAdapter.notifyDataSetChanged();
						billingAdapter.notifyDataSetChanged();

						if (chosenBilling != null && chosenBilling.id > 0) {
							billingList.setSelection(chosenBilling.id);
						}
					}
				});
		billingList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						chosenBilling = (ApplyChannel.Billing) view
								.getTag(R.id.item_id);
//						chosenChannel = channels.get(0);
						Intent intent = new Intent();
						intent.putExtra(SELECTED_CHANNEL, chosenChannel);
						intent.putExtra(SELECTED_BILLING, chosenBilling);
						setResult(RESULT_OK, intent);
						finish();
					}
				});

		API.getApplyChannelList(this,
				new HttpCallback<List<ApplyChannel>>(this) {
					@Override
					public void onSuccess(List<ApplyChannel> data) {
						channels.clear();
						billings.clear();

						if (null != data && data.size() > 0) {
							for (int i = 0; i < data.size(); i++) {

								if (data.get(i).getId() == mPayChannelID)
									channels.add(data.get(i));
							}

							// if (null == chosenBilling) {
							chosenChannel = channels.get(0);
							if (null != chosenChannel.getBillings()
									&& chosenChannel.getBillings().size() > 0) {
								chosenBilling = chosenChannel.getBillings()
										.get(0);
							}
							// }

							for (ApplyChannel channel : channels) {
								if (channel.getId() == chosenChannel.getId()
										&& null != chosenChannel.getBillings()) {
									billings.addAll(chosenChannel.getBillings());
									break;
								}
							}
							channelAdapter.notifyDataSetChanged();
							billingAdapter.notifyDataSetChanged();

							// if (chosenChannel != null
							// && chosenChannel.getId() > 0) {
							// billings.clear();
							// ApplyChannel channel = channels
							// .get(chosenChannel.getId() - 1);
							// if (null != channel) {
							// for (ApplyChannel.Billing billing : channel
							// .getBillings()) {
							// if (null != billing) {
							// billings.add(billing);
							// }
							// }
							// }
							if (chosenBilling != null && chosenBilling.id > 0) {
								billingList.setSelection(chosenBilling.id);
							}
							// }
						}

					}

					@Override
					public TypeToken<List<ApplyChannel>> getTypeToken() {
						return new TypeToken<List<ApplyChannel>>() {
						};
					}
				});
	}

	private class BillingListAdapter extends BaseAdapter {
		private BillingListAdapter() {
		}

		@Override
		public int getCount() {
			return billings.size();
		}

		@Override
		public ApplyChannel.Billing getItem(int i) {
			return billings.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(ApplyChannelActivity.this)
						.inflate(R.layout.simple_list_item, null);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.item_selected);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ApplyChannel.Billing billing = getItem(i);

			if (null != billing) {
				holder.id.setText(billing.id + "");
				holder.name.setText(billing.name);
				if (null != chosenBilling && billing.id == chosenBilling.id) {
					holder.icon.setVisibility(View.VISIBLE);
					holder.icon.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_selected));
				} else {
					holder.icon.setVisibility(View.INVISIBLE);
				}
				convertView.setTag(R.id.item_id, billing);
			}

			return convertView;
		}
	}

	private class ChannelListAdapter extends BaseAdapter {
		private ChannelListAdapter() {
		}

		@Override
		public int getCount() {
			return channels.size();
		}

		@Override
		public ApplyChannel getItem(int i) {
			return channels.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(ApplyChannelActivity.this)
						.inflate(R.layout.simple_list_item, null);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.item_selected);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ApplyChannel channel = getItem(i);

			if (null != channel) {
				holder.icon.setVisibility(View.GONE);
				holder.id.setText(channel.getId() + "");
				holder.name.setText(channel.getName());
				if (null != chosenChannel
						&& channel.getId() == chosenChannel.getId()) {
					holder.name.setTextColor(getResources().getColor(
							R.color.bgtitle));
					convertView.setBackgroundColor(getResources().getColor(
							R.color.white));
				} else {
					holder.name.setTextColor(getResources().getColor(
							R.color.text292929));
					convertView.setBackgroundColor(getResources().getColor(
							R.color.F3F2F2));
				}
				convertView.setTag(R.id.item_id, channel);
			}
			return convertView;
		}
	}

	private static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView id;
	}
}
