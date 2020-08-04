package wackycodes.ecom.eanshopadmin.main.orderlist.neworder;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListAdaptor;

/*
 * Copyright (c) 2020.
 * WackyCodes : Tech Services.
 * https://linktr.ee/wackycodes
 */

public class OrderViewPagerFragment extends Fragment {

    public RecyclerView orderViewPagerRecyclerView;
    public TextView noOrderText;

    public OrderViewPagerFragment() {
        // Required empty public constructor
    }

    public OrderListAdaptor orderViewPagerListAdaptor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_order_view_pager, container, false );

        orderViewPagerRecyclerView = view.findViewById( R.id.order_view_pager_recycler );
        noOrderText = view.findViewById( R.id.no_order_text );

        // Set Layout Manager..
        LinearLayoutManager layoutManager = new LinearLayoutManager( view.getContext() );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        orderViewPagerRecyclerView.setLayoutManager( layoutManager );

        return view;
    }

}
