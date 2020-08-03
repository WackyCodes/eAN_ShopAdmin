package wackycodes.ecom.eanshopadmin.main.orderlist.neworder;

/*
 * Copyright (c) 2020.
 * WackyCodes : Tech Services.
 * https://linktr.ee/wackycodes
 */

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListModel;
import wackycodes.ecom.eanshopadmin.product.description.ProductDetailsDescriptionFragment;

/**
 * Created by Shailendra (WackyCodes) on 04/08/2020 00:58
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class NewOrderTabAdaptor extends FragmentPagerAdapter {

    private int totalTabs;
    private List<OrderListModel> orderListModelList;

    public NewOrderTabAdaptor(@NonNull FragmentManager fm, int totalTabs, List<OrderListModel> orderListModelList) {
        super( fm );
        this.totalTabs = totalTabs;
        this.orderListModelList = orderListModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                // Case for New Order List...
                /*
                 So Here we have to filter our list first..! and Set it in adaptor.../ Fragment..
                 */
//                ProductDetailsDescriptionFragment productDetailsDescriptionFragment = new ProductDetailsDescriptionFragment();
//                ProductDetailsDescriptionFragment.productDescription = productSpecificationText;
//                return productDetailsDescriptionFragment;
            case 1:
                // Case for Preparing Order List...
            case 2:
                // Case for Ready to delivery Order List...
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
