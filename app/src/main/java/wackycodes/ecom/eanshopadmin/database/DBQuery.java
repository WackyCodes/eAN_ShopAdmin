package wackycodes.ecom.eanshopadmin.database;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import wackycodes.ecom.eanshopadmin.MainActivity;
import wackycodes.ecom.eanshopadmin.home.HomeCatListModel;
import wackycodes.ecom.eanshopadmin.home.HomeFragment;
import wackycodes.ecom.eanshopadmin.home.HomeListModel;
import wackycodes.ecom.eanshopadmin.home.HomePageAdaptor;
import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListFragment;
import wackycodes.ecom.eanshopadmin.main.orderlist.OrderListModel;
import wackycodes.ecom.eanshopadmin.main.orderlist.OrderProductItemModel;
import wackycodes.ecom.eanshopadmin.model.BannerModel;
import wackycodes.ecom.eanshopadmin.product.ProductModel;

import static wackycodes.ecom.eanshopadmin.MainActivity.mainActivity;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ADMIN_DATA_MODEL;
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
     // Order List...
    public static List <OrderListModel> orderListModelList = new ArrayList <>();
    private static OrderListModel orderListModel;
    private static List<OrderProductItemModel> orderSubList;

    // new Order List...
    public static List <OrderListModel> newOrderList = new ArrayList <>();
    public static List <OrderListModel> preparingOrderList = new ArrayList <>();
    public static List <OrderListModel> readyToDeliveredList = new ArrayList <>();


     private static CollectionReference getShopCollectionRef(String collectionName){
         return firebaseFirestore.collection( "SHOPS" ).document( SHOP_ID ).collection( collectionName );
     }

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

    // Get Shop Data...
    public static void getShopData(final String shopID){
        firebaseFirestore.collection( "SHOPS" ).document( shopID )
                .get().addOnCompleteListener( new OnCompleteListener <DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    Boolean available_service = documentSnapshot.getBoolean( "available_service" );
                    Boolean is_open = documentSnapshot.getBoolean( "is_open" );
                    String shop_address = documentSnapshot.get( "shop_address" ).toString();
                    String shop_area_code = documentSnapshot.get( "shop_area_code" ).toString();
//                    String shop_area_name = documentSnapshot.get( "shop_area_name" ).toString();
//                    String shop_cat_main = documentSnapshot.get( "shop_cat_main" ).toString();
                    String shop_category_name = documentSnapshot.get( "shop_category_name" ).toString();

                    // shop_categories

                    String shop_city_name = documentSnapshot.get( "shop_city_name" ).toString();
//                    String shop_close_msg = documentSnapshot.get( "shop_close_msg" ).toString();
//                    String shop_id = documentSnapshot.get( "shop_id" ).toString();
                    String shop_landmark = documentSnapshot.get( "shop_landmark" ).toString();
                    String shop_logo = documentSnapshot.get( "shop_logo" ).toString();
                    String shop_name = documentSnapshot.get( "shop_name" ).toString();

                    String shop_owner_address = documentSnapshot.get( "shop_owner_address" ).toString();
                    String shop_owner_name = documentSnapshot.get( "shop_owner_name" ).toString();
                    String shop_owner_mobile = documentSnapshot.get( "shop_owner_mobile" ).toString();
                    String shop_owner_email = documentSnapshot.get( "shop_owner_email" ).toString();
                    String shop_veg_non_type = documentSnapshot.get( "shop_veg_non_type" ).toString();
                    String shop_image = documentSnapshot.get( "shop_image" ).toString();
                    String shop_rating = documentSnapshot.get( "shop_rating" ).toString();
                    String shop_open_time;
                    String shop_close_time;

                    if ( documentSnapshot.get( "shop_open_time" )!=null && documentSnapshot.get( "shop_close_time" ) != null ){
                        shop_open_time = documentSnapshot.get( "shop_open_time" ).toString();
                        shop_close_time = documentSnapshot.get( "shop_close_time" ).toString();
                    }else{
                        shop_open_time = "AM";
                        shop_close_time = "PM";
                    }

                    // tags...

                    ADMIN_DATA_MODEL.setShopID( shopID );
                    ADMIN_DATA_MODEL.setShopName( shop_name );
                    ADMIN_DATA_MODEL.setServiceAvailable( available_service );
                    ADMIN_DATA_MODEL.setOpen( is_open );
                    ADMIN_DATA_MODEL.setShopAddress( shop_address );
                    ADMIN_DATA_MODEL.setShopAreaCode( shop_area_code );
                    ADMIN_DATA_MODEL.setShopCategory( shop_category_name );
                    ADMIN_DATA_MODEL.setShopCity( shop_city_name );
//                    shopHomeActivityModel.setShopCloseTime( shop_close_msg );
                    ADMIN_DATA_MODEL.setShopLandMark( shop_landmark );
                    ADMIN_DATA_MODEL.setShopLogo( shop_logo );

                    ADMIN_DATA_MODEL.setShopOwnerAddress( shop_owner_address );
                    ADMIN_DATA_MODEL.setShopOwnerName( shop_owner_name );
                    ADMIN_DATA_MODEL.setShopOwnerMobile( shop_owner_mobile );
                    ADMIN_DATA_MODEL.setShopOwnerEmail( shop_owner_email );
                    ADMIN_DATA_MODEL.setShopVegNonCode( Integer.parseInt( shop_veg_non_type ) );
                    ADMIN_DATA_MODEL.setShopImage( shop_image );
                    ADMIN_DATA_MODEL.setShopRatingStars( shop_rating );
                    ADMIN_DATA_MODEL.setShopOpenTime( shop_open_time );
                    ADMIN_DATA_MODEL.setShopCloseTime( shop_close_time );

                    if (mainActivity != null){
                        mainActivity.getSupportActionBar().setTitle( ADMIN_DATA_MODEL.getShopName() );
                    }

                }
                else{
                    // Failed...
                }
            }
        } );

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
                            HomeListModel homeListModel;
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
                                                Integer.parseInt( String.valueOf((long)documentSnapshot.get( "banner_click_type_" + i )) ),
                                                documentSnapshot.get( "banner_click_id_" + i ).toString(),
                                                documentSnapshot.get( "banner_" + i ).toString(),
                                                "Extra_Text",
                                                documentSnapshot.get( "delete_id_" + i ).toString() ));
                                    }
                                    // add the banners list in the home recycler list...
//                                    homeListModelList.add( new HomeListModel( SHOP_HOME_BANNER_SLIDER_CONTAINER, layout_id, visibility, bannerModelList ) );
                                    homeListModel = new HomeListModel( SHOP_HOME_BANNER_SLIDER_CONTAINER, layout_id, visibility, bannerModelList );
                                    if (homeCatListModelList.size() > 0){
                                        homeCatListModelList.get( index ).getHomeListModelList().add( homeListModel );
                                    }

                                } else
                                if ( viewType == SHOP_HOME_STRIP_AD_CONTAINER) {
                                    /**  for strip and banner ad */
                                    String layout_id = documentSnapshot.get( "layout_id" ).toString();
                                    BannerModel bannerModel = new BannerModel(
                                            Integer.parseInt( String.valueOf( (long) documentSnapshot.get( "banner_click_type" ) ) ),
                                            documentSnapshot.get( "banner_click_id" ).toString(),
                                            documentSnapshot.get( "banner_image" ).toString(),
                                            documentSnapshot.get( "extra_text" ).toString(),
                                            documentSnapshot.get( "delete_id" ).toString()
                                            );
//                                    homeListModelList.add( new HomeListModel( SHOP_HOME_STRIP_AD_CONTAINER, layout_id, bannerModel ) );
                                    homeListModel = new HomeListModel( SHOP_HOME_STRIP_AD_CONTAINER, layout_id, bannerModel );
                                    if (homeCatListModelList.size() > 0){
                                        homeCatListModelList.get( index ).getHomeListModelList().add( homeListModel );
                                    }
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
//                                    homeListModelList.add( new HomeListModel( viewType, layout_id, layout_title, hrAndGridProductIdList,
//                                            new ArrayList <ProductModel>() ) );
                                    homeListModel =  new HomeListModel( viewType, layout_id, layout_title, hrAndGridProductIdList,
                                            new ArrayList <ProductModel>() );
                                    if (homeCatListModelList.size() > 0){
                                        homeCatListModelList.get( index ).getHomeListModelList().add( homeListModel );
                                    }

                                } else
                                if ( viewType == SHOP_HOME_CAT_LIST_CONTAINER) { // TODO : Create New Type For SubCategory...
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
                                        if ( swipeRefreshLayout == null){
                                            homeCatListModelList.add( new HomeCatListModel( documentSnapshot.get( "cat_id_"+i ).toString()
                                                    , documentSnapshot.get( "cat_name_"+i ).toString(), new ArrayList <HomeListModel>()  ) );
                                        }
                                    }
                                    // add the banners list in the home recycler list...
//                                    homeListModelList.add( new HomeListModel( SHOP_HOME_CAT_LIST_CONTAINER, layout_id, visibility, bannerModelList ) );
                                    homeListModel = new HomeListModel( SHOP_HOME_CAT_LIST_CONTAINER, layout_id, visibility, bannerModelList );
                                    if (homeCatListModelList.size() > 0){
                                        homeCatListModelList.get( index ).getHomeListModelList().add( homeListModel );
                                    }
                                }
                                // Load data without waste of time...
//                                homeFragmentAdaptor.notifyDataSetChanged();
//                                if (homeCatListModelList.size() > 0){
//                                    homeCatListModelList.get( index ).setHomeListModelList( homeListModelList );
//                                }
                                if (HomeFragment.homePageAdaptor != null){
                                    HomeFragment.homePageAdaptor.notifyDataSetChanged();
                                }
                            }
                            // At the last... When We add All Data in homeListModelList...
//                            homeCatListModelList.add( index, new HomeCatListModel( categoryID, "Cat_Name",  homeListModelList ) );
                            // Or..
//                            if (homeCatListModelList.size() > 0){
//                                homeCatListModelList.get( index ).setHomeListModelList( homeListModelList );
//                            }
                            if (HomeFragment.homePageAdaptor != null){
                                HomeFragment.homePageAdaptor.notifyDataSetChanged();
                            }

                            // $ Changes...
                            if (dialog!=null){
                                dialog.dismiss();
                            }

                            //swipeRefreshLayout
                            if (swipeRefreshLayout!=null){
                                swipeRefreshLayout.setRefreshing( false );
                            }

                            if (HomeFragment.homePageAdaptor != null){
                                HomeFragment.homePageAdaptor.notifyDataSetChanged();
                            }

                        }
                        else{
                            // $ Changes...
                            if (dialog!=null){
                                dialog.dismiss();
                            }

                            //swipeRefreshLayout
                            if (swipeRefreshLayout!=null){
                                swipeRefreshLayout.setRefreshing( false );
                            }

                        }

                    }
                } );


    }

    public static void getOrderListQuery(@Nullable final Dialog dialog, String fromDate){

        getShopCollectionRef( "ORDERS" )
                .orderBy( "order_date" )
                .whereGreaterThanOrEqualTo( "order_date", fromDate )
                .get()
                .addOnCompleteListener( new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
                        // Task...
                        if (task.isSuccessful() && task.getResult() != null){
                            for ( DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                // Assign new OrderListModel...
                                orderListModel = new OrderListModel();

                                orderListModel.setOrderID( documentSnapshot.get( "order_id" ).toString() );
                                orderListModel.setDeliveryStatus( documentSnapshot.get( "delivery_status" ).toString() );
                                orderListModel.setPayMode( documentSnapshot.get( "pay_mode" ).toString() );

                                orderListModel.setDeliveryCharge( documentSnapshot.get( "delivery_charge" ).toString() );
                                orderListModel.setBillingAmounts( documentSnapshot.get( "billing_amounts" ).toString() );

                                orderListModel.setCustAuthID( documentSnapshot.get( "order_by_auth_id" ).toString() );
                                orderListModel.setCustName( documentSnapshot.get( "order_by_name" ).toString() );
                                orderListModel.setCustMobile( documentSnapshot.get( "order_by_mobile" ).toString() );

                                orderListModel.setShippingName( documentSnapshot.get( "order_accepted_by" ).toString() );
                                orderListModel.setShippingAddress( documentSnapshot.get( "order_delivery_address" ).toString() );
                                orderListModel.setShippingPinCode( documentSnapshot.get( "order_delivery_pin" ).toString() );

                                orderListModel.setOrderDate( documentSnapshot.get( "order_date" ).toString() );
                                orderListModel.setOrderDay( documentSnapshot.get( "order_day" ).toString() );
                                orderListModel.setOrderTime( documentSnapshot.get( "order_time" ).toString() );

                                orderListModel.setDeliverySchedule( documentSnapshot.get( "delivery_schedule_time" ).toString() );

                                long no_of_products = (long)documentSnapshot.get( "no_of_products" );

                                orderSubList = new ArrayList <>();

                                for (long ind = 0; ind < no_of_products; ind++){
                                    orderSubList.add( new OrderProductItemModel(
                                            documentSnapshot.getLong( "product_id_"+ind ).toString(),
                                            documentSnapshot.getLong( "product_image_"+ind ).toString(),
                                            documentSnapshot.getLong( "product_name_"+ind ).toString(),
                                            documentSnapshot.getLong( "product_price_"+ind ).toString(),
                                            documentSnapshot.getLong( "product_qty_"+ind ).toString()
                                    ) );
                                }

                                orderListModel.setOrderProductItemsList( orderSubList );

                                // add Model Item in the List...
                                orderListModelList.add( orderListModel );

                                // TODO: add data in the list...
                                if (OrderListFragment.orderListAdaptor != null){
                                    OrderListFragment.orderListAdaptor.notifyDataSetChanged();
                                }

                                if (dialog != null){
                                    dialog.dismiss();
                                }
                            }

                        }else{
                            // Failed...
                        }
                        if (dialog != null){
                            dialog.dismiss();
                        }
                    }
                } );

    }



}
