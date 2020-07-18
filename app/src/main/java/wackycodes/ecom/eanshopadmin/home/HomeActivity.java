package wackycodes.ecom.eanshopadmin.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import wackycodes.ecom.eanshopadmin.R;

import static wackycodes.ecom.eanshopadmin.database.DBQuery.homeCatListModelList;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.FRAGMENT_HOME;

public class HomeActivity extends AppCompatActivity {
    public static AppCompatActivity homeActivity;
    private static FrameLayout homeActivityFrame;

    public static int homeCurrentFragment = FRAGMENT_HOME;
    public static int homeCurrentCatIndex = 0;
    public static String homeCurrentCatID = "HOME";

    private static FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        homeActivity = this;

        homeActivityFrame = findViewById( R.id.home_activity_frame_layout );
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

//        if (homeCatListModelList.size() == 0){
//            // DO Complete this process in MainActivity Background!
//        }

        // Set Frame...
        if (homeCurrentFragment == FRAGMENT_HOME){
            setForwardFragment( new HomeFragment( 0, "HOME", "Home" ) );
        }else{
            setForwardFragment( new HomeFragment( homeCurrentCatIndex, homeCurrentCatID, homeCatListModelList.get( homeCurrentCatIndex ).getCatName() ) );
        }
        //



    }

    @Override
    public void onBackPressed() {
        if (homeCurrentCatIndex == FRAGMENT_HOME){
            super.onBackPressed();
        }else{
            // Set Home Fragment...
            setForwardFragment( new HomeFragment( 0, "HOME", "Home" ) );
        }

    }

    // Fragment Transaction...
    public static void setForwardFragment( Fragment fragment){
        fragmentTransaction.add( homeActivityFrame.getId(),fragment );
        fragmentTransaction.commit();
    }
    // Fragment Transaction...
    public static void setBackwardFragment( Fragment fragment){
        fragmentTransaction.add( homeActivityFrame.getId(),fragment );
        fragmentTransaction.commit();
    }




}
