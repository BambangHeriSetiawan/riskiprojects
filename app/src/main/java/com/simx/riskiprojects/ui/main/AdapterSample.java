package com.simx.riskiprojects.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.ui.main.AdapterSample.Holder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: simx Date: 09/08/18 15:50
 */
public class AdapterSample extends Adapter<Holder> {


	private List<ResponseSample> responseSamples;
	private onAdapterClik onAdapterClik;
	public AdapterSample(onAdapterClik onAdapterClik) {
		this.onAdapterClik = onAdapterClik;
		this.responseSamples = new ArrayList<>();
	}

	@NonNull
	@Override
	public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new Holder(LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_sample, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull Holder holder, int position) {
		ResponseSample responseSample =  getResponseSamples(position);
		holder.tvSample.setText(responseSample.toString());
		holder.itemView.setOnClickListener(v -> onAdapterClik.pushToFireStore(responseSample));
	}

	@Override
	public int getItemCount() {
		return responseSamples.size();
	}

	public ResponseSample getResponseSamples(int pos) {
		return responseSamples.get(pos);
	}

	public void setResponseSamples(List<ResponseSample> responseSamples) {
		this.responseSamples = responseSamples;
		notifyDataSetChanged();
	}

	public class Holder extends ViewHolder {
		@BindView(R.id.tv_sample)
		TextView tvSample;
		public Holder(View itemView) {
			super(itemView);
			ButterKnife.bind(this,itemView);
		}
	}
	public interface onAdapterClik{
		void pushToFireStore(ResponseSample responseSample);
	}
}
