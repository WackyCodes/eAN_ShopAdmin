package wackycodes.ecom.eanshopadmin.main.orderlist.neworder;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.other.DialogsClass;
import wackycodes.ecom.eanshopadmin.product.productview.ProductDetailsDescriptionAdaptor;

/*
 * Copyright (c) 2020.
 * WackyCodes : Tech Services.
 * https://linktr.ee/wackycodes
 */

public class NewOrderFragment extends Fragment {

    public NewOrderFragment() {
        // Required empty public constructor
    }

    private Dialog dialog;
    public static TextView no_order_text;


    private ViewPager newOrderViewPager;
    private TabLayout newOrderTabLayoutIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_new_order, container, false );

        dialog = DialogsClass.getDialog( getContext() );
        // Assign ...
        no_order_text = view.findViewById( R.id.no_order_text );

        // Set Layout Manager..
//        LinearLayoutManager layoutManager = new LinearLayoutManager( view.getContext() );
//        layoutManager.setOrientation( RecyclerView.VERTICAL );
//        newOrderRecycler.setLayoutManager( layoutManager );

        // ----------
        newOrderViewPager = view.findViewById( R.id.new_order_view_pager );
        newOrderTabLayoutIndicator = view.findViewById( R.id.new_order_tab_layout );

        //----------- Product Description ---
        newOrderViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener( newOrderTabLayoutIndicator ) );
        newOrderTabLayoutIndicator.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newOrderViewPager.setCurrentItem( tab.getPosition() );
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );


//        ProductDetailsDescriptionAdaptor productDetailsDescriptionAdaptor
//                                    = new ProductDetailsDescriptionAdaptor( );
//        newOrderViewPager.setAdapter( productDetailsDescriptionAdaptor );
//        productDetailsDescriptionAdaptor.notifyDataSetChanged();

        return view;
    }

}
