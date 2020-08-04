package wackycodes.ecom.eanshopadmin.main.orderlist;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.database.DBQuery;
import wackycodes.ecom.eanshopadmin.other.DialogsClass;
import wackycodes.ecom.eanshopadmin.other.StaticMethods;

import static wackycodes.ecom.eanshopadmin.SetFragmentActivity.setFragmentActivity;
import static wackycodes.ecom.eanshopadmin.database.DBQuery.orderListModelList;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_CHECK;

/**
 * Created by Shailendra (WackyCodes) on 30/07/2020 23:02
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class OrderListFragment extends Fragment {

    public OrderListFragment() {
        // Required empty public constructor
    }

    private Dialog dialog;
    private RecyclerView orderListRecycler;
    public static TextView no_order_text;

    public static OrderListAdaptor orderListAdaptor;

    private Spinner orderFromSpinner;
    private Spinner sortBySpinner;

    private String orderFrom;
    private String sortBy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_order_list, container, false );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull( setFragmentActivity.getSupportActionBar() ).setTitle( "Order List" );
        }

        dialog = DialogsClass.getDialog( getContext() );
        // Assign ...
        orderListRecycler = view.findViewById( R.id.order_list_recycler );
        orderFromSpinner = view.findViewById( R.id.order_from_spinner );
        sortBySpinner = view.findViewById( R.id.sort_by_spinner );
        no_order_text = view.findViewById( R.id.no_order_text );

        // Set Layout Manager..
        LinearLayoutManager layoutManager = new LinearLayoutManager( view.getContext() );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        orderListRecycler.setLayoutManager( layoutManager );

        // set Adaptor...
        orderListAdaptor = new OrderListAdaptor( orderListModelList, ORDER_LIST_CHECK );
        orderListRecycler.setAdapter( orderListAdaptor );
        orderListAdaptor.notifyDataSetChanged();

        // Spinner Set...
        setSpinner();

        return view;
    }

    private void setSpinner(){

        // Select Order From...
        ArrayAdapter <String> dataAdapter = new ArrayAdapter <String>( getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray( R.array.order_from ));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderFromSpinner.setAdapter(dataAdapter);
        orderFromSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                switch (position){
                    case 0: // Today's Order
                        orderFrom = StaticMethods.getCrrDate(); // "yyyy/MM/dd"
                        break;
                    case 1: // Last 2 days
                        orderFrom = getDateUnder31( 1 ); // in return value we include from last date to current date i.e. 2 days
                        break;
                    case 2: // Last 5 days
                        orderFrom = getDateUnder31( 4 );
                        break;
                    case 3: // Last 7 days
                        orderFrom = getDateUnder31( 6 );
                        break;
                    case 4: // Last 30 days
                        orderFrom = getDateUnder31( 29 );
                        break;
                    default:
                        break;
                }
                dialog.show();
                DBQuery.getOrderListQuery( dialog, orderFrom );
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
//
            }
        } );
        // -----------

    }

    private String getDateUnder31( int situation)
    {
         /*
           <item>Today's Order</item>
        <item>Last 2 days</item>
        <item>Last 5 days</item>
        <item>Last 7 days</item>
        <item>Last 30 days</item>
        // Not use yet.!
        <item>Last 2 Months</item>
        <item>Last 6 Months</item>
         */

        String currDate = StaticMethods.getCrrDate(); // "yyyy/MM/dd"
        int yearVal = Integer.valueOf ( currDate.substring( 0, 4 ) );
        int monthVal = Integer.valueOf (  currDate.substring( 5, 7 ) );
        int dayVal = Integer.valueOf( currDate.substring( 8 ) );

        // Suppose .. situation is under 30 Days

        // Check dayVal < situation ( 3 < 4 )
        // dayVal = 29 - (situation - dayVal);

        if ( dayVal <= situation ){
            if (monthVal == 5 || monthVal == 7 || monthVal == 10 || monthVal == 12 ){
                monthVal = monthVal - 1;
                dayVal = 30 - (situation - dayVal);
            }
            else if (monthVal == 3){
                if (yearVal % 4 == 0){
                    dayVal = 29 - (situation - dayVal);
                }else{
                    dayVal = 28 - (situation - dayVal);
                }
                monthVal = monthVal - 1;
            }
            else {
                /*
                        if (monthVal == 1 || monthVal == 2 || monthVal == 4 || monthVal == 6
                     || monthVal == 8 || monthVal == 9 || monthVal == 11){...}
                         */
                if (monthVal == 1){
                    yearVal = yearVal - 1;
                    monthVal = 12;
                }else{
                    monthVal = monthVal - 1;
                }
                dayVal = 31 - (situation - dayVal); // it is included to get data...
            }

            currDate = yearVal + "/" + monthVal + "/" + dayVal;
            return currDate;
        }
        else{
            dayVal = dayVal - situation;
            currDate = yearVal + "/" + monthVal + "/" + dayVal;
            return currDate;
        }
        // Do Greater than...
    }

}
