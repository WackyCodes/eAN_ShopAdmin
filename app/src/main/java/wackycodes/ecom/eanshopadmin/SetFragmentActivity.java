package wackycodes.ecom.eanshopadmin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListFragment;

import static wackycodes.ecom.eanshopadmin.other.StaticValues.REQUEST_TO_VIEW_INCOME_RECORDS;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.REQUEST_TO_VIEW_ORDER_LIST;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.REQUEST_TO_VIEW_SELLING_RECORDS;

/**
 * Created by Shailendra (WackyCodes) on 30/07/2020 21:02
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */

public class SetFragmentActivity extends AppCompatActivity {
    public static AppCompatActivity setFragmentActivity;

    private FrameLayout frameLayout;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_set_fragment );
        setFragmentActivity = this;

        int frgment_no = getIntent().getIntExtra( "FRAGMENT_NO", -1 );

        toolbar = findViewById( R.id.appToolbar );
        setSupportActionBar( toolbar );
        try {
            getSupportActionBar().setDisplayShowTitleEnabled( true );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }catch (NullPointerException ignored){ }

        // Assign Variables...
        frameLayout = findViewById( R.id.set_fragment_frame_layout );

        switch (frgment_no){
            case REQUEST_TO_VIEW_ORDER_LIST:
                setFragment(new OrderListFragment());
                break;
            case REQUEST_TO_VIEW_INCOME_RECORDS:
            case REQUEST_TO_VIEW_SELLING_RECORDS:
            default:
                break;
        }

//        public static final int REQUEST_TO_VIEW_ORDER_LIST = 5;
//        public static final int REQUEST_TO_VIEW_INCOME_RECORDS = 6;
//        public static final int REQUEST_TO_VIEW_SELLING_RECORDS = 7;
//        public static final int REQUEST_TO_VIEW_SHOP_RATING = 8;
//        public static final int REQUEST_TO_VIEW_SERVICE_AREA = 9;
//        public static final int REQUEST_TO_VIEW_FAVORITE_CUST = 10;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return true;
    }

    // Fragment Transaction...
    public void setFragment( Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( frameLayout.getId(),fragment );
        fragmentTransaction.commit();
    }

}
