package wackycodes.ecom.eanshopadmin.database;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import wackycodes.ecom.eanshopadmin.MainActivity;
import wackycodes.ecom.eanshopadmin.home.HomeCatListModel;
import wackycodes.ecom.eanshopadmin.home.HomeListModel;
import wackycodes.ecom.eanshopadmin.model.BannerModel;
import wackycodes.ecom.eanshopadmin.product.ProductModel;

import static wackycodes.ecom.eanshopadmin.other.StaticValues.GRID_PRODUCTS_LAYOUT_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_BANNER_SLIDER_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_CAT_LIST_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_STRIP_AD_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_ID;

public class DBQuery {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    // get Current User Reference ...
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public  static StorageReference storageReference = FirebaseStorage.getInstance().getReference();

//    public static List<String>
    // Home List...
    public static List<HomeCatListModel> homeCatListModelList = new ArrayList <>();
    private static List<HomeListModel> homeListModelList;
    private static List<BannerModel> bannerModelList;
    // To Understand list inside list...
     /*

    < List>
        List<HomeCatListModel> homeCatListModelList
        catID

        < List>
            List<HomeListModel> homeListModelList
             < List>
                List<ProductModel> productModelList
                productLayId
                Product ID List
                layoutGridTitle

            </List>

        </List>

    </List>

     */

    // Get City List... // Not Required...
    public static void getCityListQuery(){ /**
        areaCodeCityModelList.clear();
        firebaseFirestore.collection( "AREA_CODE" ).orderBy( "area_code" )
                .get().addOnCompleteListener( new OnCompleteListener <QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task <QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String areaCode = String.valueOf( (long)documentSnapshot.get( "area_code" ) );
                        String areaName = documentSnapshot.get( "area_name" ).toString();
                        String cityName = documentSnapshot.get( "area_city" ).toString();
                        String cityCode = documentSnapshot.get( "area_city_code" ).toString();

                        areaCodeCityModelList.add( new AreaCodeCityModel( areaCode, areaName, cityName, cityCode ) );
                    }
                    if (MainActivity.selectAreaCityAdaptor != null){
                        MainActivity.selectAreaCityAdaptor.notifyDataSetChanged();
                    }

                }else{

                }
            }
        } ); */
    }


    // Query to Load Fragment Data like homepage items etc...
    public static void getHomeCatListQuery(final Context context
            , @Nullable final Dialog dialog,  @Nullable final SwipeRefreshLayout swipeRefreshLayout
            , final String categoryID, final int index ) {
        //------------------------------------------------------
        firebaseFirestore
                .collection( "SHOPS" ).document( SHOP_ID )
                .collection( categoryID ).orderBy( "index" ).get()
                .addOnCompleteListener( new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            homeListModelList = new ArrayList <>();
                            // add Data from snapshot...
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                int viewType = Integer.parseInt( String.valueOf( (long)documentSnapshot.get( "type" ) ) );
//                            showToast( context, "City : " + userCityName );
                                if ( viewType == SHOP_HOME_BANNER_SLIDER_CONTAINER) {
                                    /** add banners slider */
                                    String layout_id = documentSnapshot.get( "layout_id" ).toString();
                                    Boolean visibility = documentSnapshot.getBoolean( "visibility" );
                                    bannerModelList = new ArrayList <>();
                                    long no_of_banners = (long) documentSnapshot.get( "no_of_banners" );
                                    for (long i = 1; i <= no_of_banners; i++) {
                                        // access the banners from database...
//                                        int clickType, String clickID, String imageLink, String nameOrExtraText, String deleteID
                                        bannerModelList.add( new BannerModel(
                                                -1, "ClickID",
                                                documentSnapshot.get( "banner_" + i ).toString(),
                                                "Extra_Text",
                                                "delete ID" ));
                                    }
                                    // add the banners list in the home recycler list...
                                    homeListModelList.add( new HomeListModel( SHOP_HOME_BANNER_SLIDER_CONTAINER, layout_id, visibility, bannerModelList ) );

                                } else
                                if ( viewType == SHOP_HOME_STRIP_AD_CONTAINER) {
                                    /**  for strip and banner ad */
                                    String layout_id = documentSnapshot.get( "layout_id" ).toString();
                                    BannerModel bannerModel = new BannerModel(
                                            Integer.parseInt( String.valueOf( (long) documentSnapshot.get( "strip_ad_type" ) ) ),
                                            documentSnapshot.get( "strip_ad_click_id" ).toString(),
                                            documentSnapshot.get( "strip_ad_image" ).toString(),
                                            documentSnapshot.get( "extra_text" ).toString(),
                                            documentSnapshot.get( "strip_delete_id" ).toString()
                                            );
                                    homeListModelList.add( new HomeListModel( SHOP_HOME_STRIP_AD_CONTAINER, layout_id, bannerModel ) );
                                } else
                                if ( viewType == HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER ||
                                        viewType == GRID_PRODUCTS_LAYOUT_CONTAINER) {
                                    /** : for horizontal products */
                                    String layout_id = documentSnapshot.get( "layout_id" ).toString();
                                    String layout_title = documentSnapshot.get( "layout_title" ).toString();
//                                    productList = new ArrayList <>(); TODO:
                                    List<String> hrAndGridProductIdList = new ArrayList <>();
                                    long no_of_products = (long) documentSnapshot.get( "no_of_products" );
                                    for (long i = 1; i < no_of_products + 1; i++) {
                                        // Load Product Data List After set Adaptor... on View Time...
                                        // Below we load only product Id...
                                        hrAndGridProductIdList.add( documentSnapshot.get( "product_id_" + i ).toString() );
                                    }
                                    // add list in home fragment model
                                    homeListModelList.add( new HomeListModel( viewType, layout_id, layout_title, hrAndGridProductIdList,
                                            new ArrayList <ProductModel>() ) );

                                } else
                                if ( viewType == SHOP_HOME_CAT_LIST_CONTAINER) {
                                    String layout_id = documentSnapshot.get( "layout_id" ).toString();
                                    Boolean visibility = documentSnapshot.getBoolean( "visibility" );
                                    bannerModelList = new ArrayList <>();
                                    long no_of_cat = (long) documentSnapshot.get( "no_of_cat" );
                                    for (long i = 1; i <= no_of_cat; i++) {
                                        // access the banners from database...
                                        bannerModelList.add( new BannerModel(
                                                -1,
                                                documentSnapshot.get( "cat_id_"+i ).toString(),
                                                documentSnapshot.get( "cat_image_"+i ).toString(),
                                                documentSnapshot.get( "cat_name_"+i ).toString(),
                                                "delete ID" ));
                                        // To add Category id and Category Name in homeCatListModelList(Main List)...
                                        homeCatListModelList.add( new HomeCatListModel( documentSnapshot.get( "cat_id_"+i ).toString()
                                                , documentSnapshot.get( "cat_name_"+i ).toString(), new ArrayList <HomeListModel>()  ) );
                                    }
                                    // add the banners list in the home recycler list...
                                    homeListModelList.add( new HomeListModel( SHOP_HOME_CAT_LIST_CONTAINER, layout_id, visibility, bannerModelList ) );

                                }
                                // Load data without waste of time...
//                                homeFragmentAdaptor.notifyDataSetChanged();
                                if (homeCatListModelList.size() > 0){
                                    homeCatListModelList.get( index ).setHomeListModelList( homeListModelList );
                                }
                            }
                            // At the last... When We add All Data in homeListModelList...
//                            homeCatListModelList.add( index, new HomeCatListModel( categoryID, "Cat_Name",  homeListModelList ) );
                            // Or..
                            if (homeCatListModelList.size() > 0){
                                homeCatListModelList.get( index ).setHomeListModelList( homeListModelList );
                            }

                            // $ Changes...
                            if (dialog!=null){
                                dialog.dismiss();
                            }

                        }else{


                        }

                    }
                } );


    }



}
