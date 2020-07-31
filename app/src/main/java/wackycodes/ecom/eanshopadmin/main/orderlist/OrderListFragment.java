package wackycodes.ecom.eanshopadmin.main.orderlist;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wackycodes.ecom.eanshopadmin.R;

import static wackycodes.ecom.eanshopadmin.SetFragmentActivity.setFragmentActivity;

/**
 * Created by Shailendra (WackyCodes) on 30/07/2020 23:02
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class OrderListFragment extends Fragment {

    private List <OrderListModel> orderListModelList = new ArrayList <>();

    public OrderListFragment() {
        // Required empty public constructor
    }
    public OrderListFragment(List <OrderListModel> orderListModelList) {
        this.orderListModelList = orderListModelList;
    }

    private RecyclerView orderListRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_order_list, container, false );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull( setFragmentActivity.getSupportActionBar() ).setTitle( "Order List" );
        }

        // Assign ...
        orderListRecycler = view.findViewById( R.id.order_list_recycler );

        // Set Layout Manager..
        LinearLayoutManager layoutManager = new LinearLayoutManager( view.getContext() );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        orderListRecycler.setLayoutManager( layoutManager );

        // set Adaptor...
        OrderListAdaptor orderListAdaptor = new OrderListAdaptor( orderListModelList );
        orderListRecycler.setAdapter( orderListAdaptor );
        orderListAdaptor.notifyDataSetChanged();

        return view;
    }


}
