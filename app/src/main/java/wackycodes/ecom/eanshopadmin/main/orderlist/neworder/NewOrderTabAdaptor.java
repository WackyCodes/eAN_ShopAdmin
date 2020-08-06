package wackycodes.ecom.eanshopadmin.main.orderlist.neworder;

/*
 * Copyright (c) 2020.
 * WackyCodes : Tech Services.
 * https://linktr.ee/wackycodes
 */

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListAdaptor;
import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListModel;
import wackycodes.ecom.eanshopadmin.product.description.ProductDetailsDescriptionFragment;

import static wackycodes.ecom.eanshopadmin.database.DBQuery.newOrderList;
import static wackycodes.ecom.eanshopadmin.database.DBQuery.preparingOrderList;
import static wackycodes.ecom.eanshopadmin.database.DBQuery.readyToDeliveredList;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_CHECK;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_NEW_ORDER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_PREPARING;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_READY_TO_DELIVER;

/**
 * Created by Shailendra (WackyCodes) on 04/08/2020 00:58
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class NewOrderTabAdaptor extends FragmentPagerAdapter {
    // New Order Fragment...
    public static OrderViewPagerFragment newOrderFragment;
    private  OrderViewPagerFragment preparingFragment;
    private OrderViewPagerFragment readyToDeliverFragment;

    private int totalTabs;

    public NewOrderTabAdaptor(@NonNull FragmentManager fm, int totalTabs ) {
        super( fm );
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                // Case for New Order List... ORDER_LIST_NEW_ORDER
                /* So Here we have to filter our list first..! and Set it in adaptor.../ Fragment.. */
                if (newOrderFragment == null){
                    newOrderFragment = new OrderViewPagerFragment(newOrderList, ORDER_LIST_NEW_ORDER );
                }
                if (newOrderFragment.orderViewPagerListAdaptor!=null){
                    newOrderFragment.orderViewPagerListAdaptor.notifyDataSetChanged();
                }

                return newOrderFragment;
            case 1:
                if (preparingFragment == null){
                    preparingFragment = new OrderViewPagerFragment(preparingOrderList, ORDER_LIST_PREPARING );
                }
                if (preparingFragment.orderViewPagerListAdaptor!=null){
                    preparingFragment.orderViewPagerListAdaptor.notifyDataSetChanged();
                }

                return preparingFragment;
                // Case for Preparing Order List... ORDER_LIST_PREPARING
//                if (preparingFragment == null){
//                    preparingFragment = new OrderViewPagerFragment();
//                }
//                preparingFragment.orderViewPagerListAdaptor = new OrderListAdaptor( preparingOrderList, ORDER_LIST_PREPARING );
//                preparingFragment.orderViewPagerRecyclerView.setAdapter( preparingFragment.orderViewPagerListAdaptor );
//                preparingFragment.orderViewPagerListAdaptor.notifyDataSetChanged();
//                if (preparingOrderList.size() == 0){
//                    preparingFragment.noOrderText.setVisibility( View.VISIBLE );
//                }else{
//                    preparingFragment.noOrderText.setVisibility( View.GONE );
//                }
//                return preparingFragment;
            case 2:
                if (readyToDeliverFragment == null){
                    readyToDeliverFragment = new OrderViewPagerFragment(readyToDeliveredList, ORDER_LIST_READY_TO_DELIVER );
                }
                if (readyToDeliverFragment.orderViewPagerListAdaptor!=null){
                    readyToDeliverFragment.orderViewPagerListAdaptor.notifyDataSetChanged();
                }

                return readyToDeliverFragment;
                // Case for Ready to delivery Order List... ORDER_LIST_READY_TO_DELIVER
//                if (readyToDeliverFragment == null){
//                    readyToDeliverFragment = new OrderViewPagerFragment();
//                }
//                readyToDeliverFragment.orderViewPagerListAdaptor = new OrderListAdaptor( readyToDeliveredList, ORDER_LIST_READY_TO_DELIVER );
//                readyToDeliverFragment.orderViewPagerRecyclerView.setAdapter( readyToDeliverFragment.orderViewPagerListAdaptor );
//                readyToDeliverFragment.orderViewPagerListAdaptor.notifyDataSetChanged();
//                if (readyToDeliveredList.size() == 0){
//                    readyToDeliverFragment.noOrderText.setVisibility( View.VISIBLE );
//                }else{
//                    readyToDeliverFragment.noOrderText.setVisibility( View.GONE );
//                }
//                return readyToDeliverFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    /**  Order Status
     *          1. WAITING - ( For Accept )
     *          2. ACCEPTED - ( Preparing )
     *          3. PACKED - ( Waiting for Delivery ) READY_TO_DELIVERY
     *          4. PROCESS  - ( On Delivery ) OUT_FOR_DELIVERY
     *          5. SUCCESS - Success Full Delivered..!
     *          6. CANCELLED -  When Order has been cancelled by user...
     *          7. FAILED -  when PayMode Online and payment has been failed...
     *          8. PENDING - when Payment is Pending...
     *
     */
}
