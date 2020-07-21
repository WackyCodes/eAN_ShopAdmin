package wackycodes.ecom.eanshopadmin.home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.addnew.AddNewLayoutActivity;
import wackycodes.ecom.eanshopadmin.other.UpdateImages;

import static android.app.Activity.RESULT_OK;
import static wackycodes.ecom.eanshopadmin.database.DBQuery.homeCatListModelList;
import static wackycodes.ecom.eanshopadmin.other.StaticMethods.getTwoDigitRandom;
import static wackycodes.ecom.eanshopadmin.other.StaticMethods.showToast;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.BANNER_SLIDER_CONTAINER_ITEM;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.GALLERY_CODE;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.GRID_PRODUCTS_LAYOUT_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_BANNER_SLIDER_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_STRIP_AD_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_ID;

public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment( int catIndex, String catID, String catName) {
        // Required empty public constructor
        this.catIndex = catIndex;
        this.catID = catID;
        this.catName = catName;
    }
    public static HomePageAdaptor homePageAdaptor;

    private FrameLayout homeFragmentFrame;
    private SwipeRefreshLayout homeFragmentSwipeRefresh;
    private RecyclerView homeFragmentRecycler;

    private int catIndex;
    private String catID;
    private String catName;

    // Add New Layout...
    private LinearLayout addNewLayoutBtn;
    private LinearLayout dialogAddLayout;
//    private ConstraintLayout newBannerSliderContainer; // add_banner_slider_layout
//    private ConstraintLayout newGridLayoutContainer; // add_grid_layout
//    private ConstraintLayout newStripAdLayout; // add_strip_ad_layout
    private LinearLayout addNewBannerSliderBtn; // add_banner_slider_layout_LinearLay
    private LinearLayout addNewProductHrLayBtn; //add_horizontal_layout_LinearLay
    private LinearLayout addNewGirdLayoutBtn; // add_grid_layout_LinearLay
    private LinearLayout addNewStripAdLayoutBtn; // add_strip_ad_layout_LinearLay
    private ImageView closeAddLayout; //close_add_layout

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        context = view.getContext();

        homeFragmentFrame = view.findViewById( R.id.home_fragment_frame_layout );
        homeFragmentSwipeRefresh = view.findViewById( R.id.home_fragment_swipe_refresh_layout );
        homeFragmentRecycler = view.findViewById( R.id.home_fragment_recycler );

        // Set home Activity name...
        HomeActivity.homeCurrentCatID = catID;
        HomeActivity.homeCurrentCatIndex = catIndex;
//        catID = homeCatListModelList.get( catIndex ).getCatID();

        // Layout... Set...
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        homeFragmentRecycler.setLayoutManager( layoutManager );
        // TODO: Set Adaptor...

        if (homeCatListModelList.get(catIndex).getHomeListModelList().size() == 0){
            // Load Our List First...

        }else{

        }

        homePageAdaptor = new HomePageAdaptor( catIndex, homeCatListModelList.get( catIndex ));
        homeFragmentRecycler.setAdapter( homePageAdaptor );
        homePageAdaptor.notifyDataSetChanged();
        // Refreshing the List...
        homeFragmentSwipeRefresh.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refreshing...

            }
        } );

        // Add Layout...
        addNewLayoutBtn =  view.findViewById( R.id.add_new_layout );
        dialogAddLayout =  view.findViewById( R.id.dialog_add_layout );
//        newBannerSliderContainer =  view.findViewById( R.id.add_banner_slider_layout );
//        newGridLayoutContainer =  view.findViewById( R.id.add_grid_layout );
//        newStripAdLayout = view.findViewById( R.id.add_strip_ad_layout );
        addNewBannerSliderBtn =  view.findViewById( R.id.add_banner_slider_layout_LinearLay );
        addNewProductHrLayBtn =  view.findViewById( R.id.add_horizontal_layout_LinearLay );
        addNewGirdLayoutBtn =  view.findViewById( R.id.add_grid_layout_LinearLay );
        addNewStripAdLayoutBtn =  view.findViewById( R.id.add_strip_ad_layout_LinearLay );
        closeAddLayout =  view.findViewById( R.id.close_add_layout );

        addNewLayoutBtn.setOnClickListener( this );
        closeAddLayout.setOnClickListener( this );

        addNewBannerSliderBtn.setOnClickListener( this );
        addNewProductHrLayBtn.setOnClickListener( this );
        addNewGirdLayoutBtn.setOnClickListener( this );
        addNewStripAdLayoutBtn.setOnClickListener( this );

        return view;
    }

    @Override
    public void onClick(View view) {

        // Add New Layout Button Click...
         if (view == addNewLayoutBtn){
             // TODO:
             setDialogVisibility(true);
         }else  if (view == closeAddLayout){
             // TODO:
             setDialogVisibility(false);
         }else  if (view == addNewBannerSliderBtn){
             // TODO:
             setDialogVisibility(false);
//             AddNewLayoutActivity.isHomePage = true;
             Intent intent = new Intent( getActivity(), AddNewLayoutActivity.class );
             intent.putExtra( "LAY_TYPE", SHOP_HOME_BANNER_SLIDER_CONTAINER );
             intent.putExtra( "LAY_INDEX", homeCatListModelList.get( catIndex ).getHomeListModelList().size() );
             intent.putExtra( "CAT_INDEX", catIndex );
//             intent.putExtra( "CAT_ID", catID );
//             intent.putExtra( "TASK_UPDATE", false );
             startActivity( intent );

         }else  if (view == addNewProductHrLayBtn){
             // TODO:
             setDialogVisibility(false);
             Intent intent = new Intent( getActivity(), AddNewLayoutActivity.class );
             intent.putExtra( "LAY_TYPE", HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER );
             intent.putExtra( "LAY_INDEX", homeCatListModelList.get( catIndex ).getHomeListModelList().size() );
             intent.putExtra( "CAT_INDEX", catIndex );
//             intent.putExtra( "CAT_ID", catID );
//             intent.putExtra( "TASK_UPDATE", false );
             startActivity( intent );

         }else  if (view == addNewGirdLayoutBtn){
             // TODO:
             setDialogVisibility(false);
             Intent intent = new Intent( getActivity(), AddNewLayoutActivity.class );
             intent.putExtra( "LAY_TYPE", GRID_PRODUCTS_LAYOUT_CONTAINER );
             intent.putExtra( "LAY_INDEX", homeCatListModelList.get( catIndex ).getHomeListModelList().size() );
             intent.putExtra( "CAT_INDEX", catIndex );
//             intent.putExtra( "CAT_ID", catID );
//             intent.putExtra( "TASK_UPDATE", false );
             startActivity( intent );

         }else  if (view == addNewStripAdLayoutBtn){
             // TODO:
             setDialogVisibility(false);
             Intent intent = new Intent( getActivity(), AddNewLayoutActivity.class );
             intent.putExtra( "LAY_TYPE", SHOP_HOME_STRIP_AD_CONTAINER );
             intent.putExtra( "LAY_INDEX", homeCatListModelList.get( catIndex ).getHomeListModelList().size() );
             intent.putExtra( "CAT_INDEX", catIndex );
//             intent.putExtra( "CAT_ID", catID );
//             intent.putExtra( "TASK_UPDATE", false );
             startActivity( intent );
         }

    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogVisibility( false );
    }

    private void setDialogVisibility(boolean isVisible){
        if(!isVisible){
            homeFragmentSwipeRefresh.setVisibility( View.VISIBLE );
            addNewLayoutBtn.setVisibility( View.VISIBLE );
            dialogAddLayout.setVisibility( View.GONE );
        }else{
            homeFragmentSwipeRefresh.setVisibility( View.GONE );
            addNewLayoutBtn.setVisibility( View.GONE );
            dialogAddLayout.setVisibility( View.VISIBLE );
        }
    }



}
