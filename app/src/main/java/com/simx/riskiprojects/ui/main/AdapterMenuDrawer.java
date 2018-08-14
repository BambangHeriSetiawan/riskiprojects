package com.simx.riskiprojects.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.simx.riskiprojects.R;
import com.simx.riskiprojects.di.module.GlideApp;
import com.simx.riskiprojects.ui.main.AdapterMenuDrawer.Holder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: simx Date: 15/08/18 0:17
 */
public class AdapterMenuDrawer extends Adapter<Holder> {


	private List<MenuDrawer> menus;
	private Context context;
	private MainPresenter presenter;

	public AdapterMenuDrawer(Context context, MainPresenter presenter) {
		this.context = context;
		this.presenter = presenter;
		this.menus = new ArrayList<>();
	}

	@NonNull
	@Override
	public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new Holder(
				LayoutInflater.from(context).inflate(R.layout.item_menu_drawer, viewGroup, false));
	}

	@SuppressLint("CheckResult")
	@Override
	public void onBindViewHolder(@NonNull Holder holder, int i) {
		MenuDrawer menuDrawer = getMenus(i);
		holder.titleMenu.setText(menuDrawer.getTitle());
		GlideApp.with(context).asBitmap().error(R.drawable.home).load(menuDrawer.getIcon()).into(holder.imgIcon);
		holder.itemView.setOnClickListener(v -> presenter.onAdapterMenuClicked(menuDrawer));
	}

	@Override
	public int getItemCount() {
		return menus.size();
	}

	public MenuDrawer getMenus(int pos) {
		return menus.get(pos);
	}

	public void setMenus(List<MenuDrawer> menus) {
		this.menus = menus;
		notifyDataSetChanged();
	}

	public class Holder extends ViewHolder {
		@BindView(R.id.img_icon)
		ImageView imgIcon;
		@BindView(R.id.title_menu)
		TextView titleMenu;
		public Holder(@NonNull View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
