package com.simx.riskiprojects.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.data.model.ResultsItem;
import com.simx.riskiprojects.di.module.GlideApp;
import com.simx.riskiprojects.helper.AppConst;
import com.simx.riskiprojects.ui.main.AdapterPlace.Holder;
import com.simx.riskiprojects.ui.main.places.PlacesPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * User: simx Date: 09/08/18 10:44
 */
public class AdapterPlace extends Adapter<Holder> {


	private List<ResponseSample> resultsItems;
	private Context context;
	private PlacesPresenter presenter;

	public AdapterPlace(Context context, PlacesPresenter placesPresenter) {
		this.context = context;
		this.presenter = placesPresenter;
		this.resultsItems = new ArrayList<>();
	}

	@NonNull
	@Override
	public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new Holder(LayoutInflater.from(context).inflate(R.layout.item_place, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull Holder holder, int position) {
		ResponseSample resultsItem = getResuls(position);

		holder.tvName.setText(resultsItem.getNama());
		holder.tvAlamat.setText(resultsItem.getAlamat());
		holder.itemView.setOnClickListener(v -> presenter.showDetail(resultsItem));
	}

	@Override
	public int getItemCount() {
		return resultsItems.size();
	}

	private ResponseSample getResuls(int pos) {
		return resultsItems.get(pos);
	}

	public void setResultsItems(List<ResponseSample> resultsItems) {
		this.resultsItems = resultsItems;
		notifyDataSetChanged();
	}

	public class Holder extends ViewHolder {
		@BindView(R.id.img)
		ImageView img;
		@BindView(R.id.tv_name)
		TextView tvName;
		@BindView(R.id.tv_alamat)
		TextView tvAlamat;
		public Holder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
