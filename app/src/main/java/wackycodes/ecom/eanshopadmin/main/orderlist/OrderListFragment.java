package wackycodes.ecom.eanshopadmin.main.orderlist;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import wackycodes.ecom.eanshopadmin.R;

import static wackycodes.ecom.eanshopadmin.SetFragmentActivity.setFragmentActivity;

/**
 * Created by Shailendra (WackyCodes) on 30/07/2020 23:02
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class OrderListFragment extends Fragment {

    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_order_list, container, false );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull( setFragmentActivity.getSupportActionBar() ).setTitle( "Order List" );
        }



        return view;
    }




}
