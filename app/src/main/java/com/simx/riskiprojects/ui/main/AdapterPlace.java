package com.simx.riskiprojects.ui.main;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.data.model.ResponseSample;
import com.simx.riskiprojects.ui.main.AdapterPlace.Holder;
import com.simx.riskiprojects.ui.main.places.PlacesPresenter;
import io.grpc.internal.LogExceptionRunnable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * User: simx Date: 09/08/18 10:44
 */
public class AdapterPlace extends Adapter<Holder> {



	private List<ResponseSample> resultsItems;
	private List<ResponseSample> resultsItemsFiltered;

	private Context context;
	private PlacesPresenter presenter;

	public AdapterPlace(Context context, PlacesPresenter placesPresenter) {
		this.context = context;
		this.presenter = placesPresenter;
		this.resultsItems = new ArrayList<>();
		this.resultsItemsFiltered = new ArrayList<>();
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
		holder.tvPimpinan.setText("Pimpinan : "+resultsItem.getPimpinan());
		holder.tvAlamat.setText("Alamat : "+resultsItem.getAlamat());
		holder.tvTelp.setText("Phone : "+resultsItem.getTelpon()+" / "+String.valueOf(resultsItem.getDisctance()).substring(0,4)+" Meter");
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
		Collections.sort(this.resultsItems,
				(responseSample, t1) -> responseSample.getDisctance().compareTo(t1.getDisctance()));
		notifyDataSetChanged();
	}

	public Filter getFilter(){
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				String charString = charSequence.toString();
				if (charString.isEmpty()) {
					resultsItemsFiltered = resultsItems;
					Log.e("AdapterPlace", "performFiltering: Empety");
					presenter.getData();
				} else {
					List<ResponseSample> filteredList = new ArrayList<>();
					for (ResponseSample row : resultsItems) {
						// name match condition. this might differ depending on your requirement
						// here we are looking for name or phone number match
						if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getNama().contains(charSequence)) {
							Log.e("AdapterPlace", "performFiltering: " + row.toString());
							filteredList.add(row);
						}
					}

					resultsItemsFiltered = filteredList;
				}

				FilterResults filterResults = new FilterResults();
				filterResults.values = resultsItemsFiltered;
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
				resultsItems = (ArrayList<ResponseSample>) filterResults.values;
				notifyDataSetChanged();
			}
		};
	}

	public class Holder extends ViewHolder {

		@BindView(R.id.tv_name)
		TextView tvName;
		@BindView(R.id.tv_pimpinan)
		TextView tvPimpinan;
		@BindView(R.id.tv_alamat)
		TextView tvAlamat;
		@BindView(R.id.tv_telp)
		TextView tvTelp;

		public Holder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
