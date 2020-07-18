package wackycodes.ecom.eanshopadmin.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wackycodes.ecom.eanshopadmin.MainActivity;
import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.database.DBQuery;
import wackycodes.ecom.eanshopadmin.home.bannerslider.BannerSliderAdaptor;
import wackycodes.ecom.eanshopadmin.model.BannerModel;
import wackycodes.ecom.eanshopadmin.other.DialogsClass;
import wackycodes.ecom.eanshopadmin.other.MyGridView;
import wackycodes.ecom.eanshopadmin.other.MyImageView;
import wackycodes.ecom.eanshopadmin.other.StaticMethods;
import wackycodes.ecom.eanshopadmin.product.ProductModel;
import wackycodes.ecom.eanshopadmin.product.ProductSubModel;
import wackycodes.ecom.eanshopadmin.product.horizontal.ProductHrGridAdaptor;

import static wackycodes.ecom.eanshopadmin.database.DBQuery.firebaseFirestore;
import static wackycodes.ecom.eanshopadmin.database.DBQuery.homeCatListModelList;
import static wackycodes.ecom.eanshopadmin.other.StaticMethods.showToast;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.CURRENT_CITY_CODE;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.GRID_PRODUCTS_LAYOUT_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_BANNER_SLIDER_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_CAT_LIST_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_HOME_STRIP_AD_CONTAINER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_ID;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.VIEW_HORIZONTAL_LAYOUT;

public class HomePageAdaptor extends RecyclerView.Adapter {

    private int catIndex; // category index
    private String catTitle; // category name or title
    private String catID;
    private List <HomeListModel> homePageList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdaptor(int catIndex, HomeCatListModel homeCatListModel) {
        this.catIndex = catIndex;
        this.catTitle = homeCatListModel.getCatName();
        this.catID = homeCatListModel.getCatID();
        this.homePageList = homeCatListModel.getHomeListModelList();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageList.get( position ).getLayoutType()) {
            case SHOP_HOME_BANNER_SLIDER_CONTAINER:           //-- 2
                return SHOP_HOME_BANNER_SLIDER_CONTAINER;
            case SHOP_HOME_STRIP_AD_CONTAINER:         //-- 1
                return SHOP_HOME_STRIP_AD_CONTAINER;
            case SHOP_HOME_CAT_LIST_CONTAINER:            //-- 5
                return SHOP_HOME_CAT_LIST_CONTAINER;
            case HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER:            //-- 3
                return HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER;
            case GRID_PRODUCTS_LAYOUT_CONTAINER:            //-- 4
                return GRID_PRODUCTS_LAYOUT_CONTAINER;
            // Add New Items...
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
                // Banner Slider...
            case SHOP_HOME_BANNER_SLIDER_CONTAINER:
                View bannerSliderView = LayoutInflater.from( parent.getContext() ).inflate(
                    R.layout.horizontal_recycler_layout, parent, false );
                return new BannerSliderViewHolder( bannerSliderView );
                //  Strip ad Layout
            case SHOP_HOME_STRIP_AD_CONTAINER:
                View stripAdView = LayoutInflater.from( parent.getContext() ).inflate(
                        R.layout.strip_ad_item_layout, parent, false );
                return new StripAdViewHolder( stripAdView );
                // Category Layout...
            case SHOP_HOME_CAT_LIST_CONTAINER:
                View catView = LayoutInflater.from( parent.getContext() ).inflate(
                        R.layout.category_item_layout, parent, false );
                return new CategoryViewHolder( catView );
            case HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER:
                View productsHrView = LayoutInflater.from( parent.getContext() ).inflate(
                        R.layout.horizontal_recycler_layout, parent, false );
                return new ProductHorizontalViewHolder( productsHrView );
            case GRID_PRODUCTS_LAYOUT_CONTAINER:
                View productsGridView = LayoutInflater.from( parent.getContext() ).inflate(
                        R.layout.products_grid_layout, parent, false );
                return new ProductGridViewHolder( productsGridView );
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (homePageList.get( position ).getLayoutType()){
            case SHOP_HOME_BANNER_SLIDER_CONTAINER:
                List<BannerModel> bannerList = homePageList.get( position ).getBannerModelList();
                String bannerLayoutId = homePageList.get( position ).getLayoutID();
                ((BannerSliderViewHolder)holder).setData( bannerLayoutId, bannerList, position );
                break;
            case SHOP_HOME_STRIP_AD_CONTAINER:
                BannerModel stripAdModel = homePageList.get( position ).getBannerModel();
                String layoutID = homePageList.get( position ).getLayoutID();
                String stripAdImg = stripAdModel.getImageLink();
                String clickID = stripAdModel.getClickID();
                int clickType = stripAdModel.getClickType();
                String deleteID = stripAdModel.getDeleteID();
                String extraText = stripAdModel.getNameOrExtraText();
                ((StripAdViewHolder)holder).setStripAdData( stripAdImg, layoutID, clickID, clickType, deleteID, extraText, position );
                break;
            case SHOP_HOME_CAT_LIST_CONTAINER:
                List<BannerModel> catList = homePageList.get( position ).getBannerModelList();
                String catLayoutId = homePageList.get( position ).getLayoutID();
                ((CategoryViewHolder)holder).setData( catLayoutId, catList, position );
                break;
            case HORIZONTAL_PRODUCTS_LAYOUT_CONTAINER:
                List<ProductModel> productModelList = homePageList.get( position ).getProductModelList();
                List<String> productIDList = homePageList.get( position ).getProductIdList();
                String productLayId = homePageList.get( position ).getLayoutID();
                String layoutTitle = homePageList.get( position ).getProductLayoutTitle();
                ((ProductHorizontalViewHolder)holder).setData( productLayId, layoutTitle, productIDList, productModelList, position );
                break;
            case GRID_PRODUCTS_LAYOUT_CONTAINER:
                List<ProductModel> productModelGridList = homePageList.get( position ).getProductModelList();
                List<String> productIDGridList = homePageList.get( position ).getProductIdList();
                String productGridLayId = homePageList.get( position ).getLayoutID();
                String layoutGridTitle = homePageList.get( position ).getProductLayoutTitle();
                ((ProductGridViewHolder)holder).setData( productGridLayId, layoutGridTitle, productIDGridList, productModelGridList, position );
                break;
        }

    }

    @Override
    public int getItemCount() {
        return homePageList.size();
    }

    //============  Strip ad  View Holder ============
    public class StripAdViewHolder extends RecyclerView.ViewHolder{
        private MyImageView stripAdImage;
        private TextView indexNo;
        private ImageView editLayoutBtn;
        private int defaultColor;
        private int layoutPosition;
        private ImageView indexUpBtn;
        private ImageView indexDownBtn;
        private Switch visibleBtn;
        private Dialog dialog;

        public StripAdViewHolder(@NonNull View itemView) {
            super( itemView );
            stripAdImage = itemView.findViewById( R.id.strip_ad_image );
            indexNo = itemView.findViewById( R.id.strip_ad_index );
            editLayoutBtn = itemView.findViewById( R.id.edit_layout_imgView );
            indexUpBtn = itemView.findViewById( R.id.hrViewUpImgView );
            indexDownBtn = itemView.findViewById( R.id.hrViewDownImgView );
            visibleBtn = itemView.findViewById( R.id.hrViewVisibilitySwitch );
            defaultColor = ContextCompat.getColor( itemView.getContext(), R.color.colorGray);
            dialog = DialogsClass.getDialog( itemView.getContext() );
            visibleBtn.setVisibility( View.INVISIBLE );
        }
        private void setStripAdData(String imgLink, final String layoutID, final String clickID, final int clickType, final String deleteID, String extraText, final int index){
            layoutPosition = 1 + index;
            indexNo.setText( "Ad Banner position : " + layoutPosition );
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                stripAdImage.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( colorCode ) ));
//            }
            // set Image Resource from database..
            Glide.with( itemView.getContext() ).load( imgLink )
                    .apply( new RequestOptions().placeholder( R.drawable.ic_panorama_black_24dp ) ).into( stripAdImage );

            editLayoutBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete...
                    alertDialog( itemView.getContext(), index, layoutID, "SHOPS/" + SHOP_ID +"/ads",  deleteID );
                }
            } );

            stripAdImage.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast( "Code Not Found!", itemView.getContext() );
//                   Click Type
                    // 1. Product Click...
                    // 2. ...
                }
            } );

            // -------  Update Layout...
            setIndexUpDownVisibility( index, indexUpBtn, indexDownBtn ); // set Up and Down Btn Visibility...
            indexUpBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(true, index, dialog );
                }
            } );
            indexDownBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(false, index, dialog );
                }
            } );
            visibleBtn.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ){
                        // Code to Show TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", true );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }else{
                        // Hide the TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", false );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }
                }
            } );

        }


    }
    //============  Strip ad  View Holder ============

    //==============  GridProduct Grid Layout View Holder =================
    public class CategoryViewHolder extends  RecyclerView.ViewHolder{
        private MyGridView gridLayout;
        private TextView gridLayoutTitle;
        private TextView indexNo;
        private Button gridLayoutViewAllBtn;
        private TextView warningText;
        private ImageView indexUpBtn;
        private ImageView indexDownBtn;
        private ImageView editLayoutBtn;
        private Switch visibleBtn;
        private int temp = 0;
        private Dialog dialog;
        private int layoutPosition;
        private String layoutID;

        public CategoryViewHolder(@NonNull View itemView) {
            super( itemView );
            gridLayout = itemView.findViewById( R.id.category_my_grid_view );
            gridLayoutTitle = itemView.findViewById( R.id.gridLayoutTitle );
            indexNo = itemView.findViewById( R.id.gridIndexNo );
            gridLayoutViewAllBtn = itemView.findViewById( R.id.gridViewAllBtn );
            warningText = itemView.findViewById( R.id.grid_warning_text );
            indexUpBtn = itemView.findViewById( R.id.hrViewUpImgView );
            indexDownBtn = itemView.findViewById( R.id.hrViewDownImgView );
            visibleBtn = itemView.findViewById( R.id.hrViewVisibilitySwitch );
            editLayoutBtn = itemView.findViewById( R.id.edit_layout_imgView );
            dialog = DialogsClass.getDialog( itemView.getContext() );
            visibleBtn.setVisibility( View.INVISIBLE );
            gridLayoutViewAllBtn.setVisibility( View.INVISIBLE );
        }

        private void setData(final String layoutID, List<BannerModel> catList, final int index ){
            SetCategoryItem setCategoryItem = new SetCategoryItem( catList );
            gridLayout.setAdapter( setCategoryItem );
            setCategoryItem.notifyDataSetChanged();
            this.layoutID = layoutID;

            editLayoutBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete...
//                    alertDialog( itemView.getContext(), index, layoutID, "/HOME/ads",  deleteID );
                }
            } );

            // -------  Update Layout...
            setIndexUpDownVisibility( index, indexUpBtn, indexDownBtn ); // set Up and Down Btn Visibility...
            indexUpBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(true, index, dialog );
                }
            } );
            indexDownBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(false, index, dialog );
                }
            } );
            visibleBtn.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ){
                        // Code to Show TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", true );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }else{
                        // Hide the TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", false );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }
                }
            } );
        }

        private class SetCategoryItem extends BaseAdapter {
            List<BannerModel> categoryTypeModelList;
            public SetCategoryItem(List <BannerModel> categoryTypeModelList) {
                this.categoryTypeModelList = categoryTypeModelList;
            }

            @Override
            public int getCount() {
                return categoryTypeModelList.size() + 1;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @SuppressLint({"ViewHolder", "InflateParams"})
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.square_category_layout_item, null );
                if (position < categoryTypeModelList.size()){
                    ImageView itemImage = view.findViewById( R.id.sq_image_view );
                    TextView itemName =  view.findViewById( R.id.sq_text_view );
//                itemImage.setImageResource( Integer.parseInt( categoryTypeModelList.get( position ).getCatImage() ) );
                    Glide.with( itemView.getContext() ).load( categoryTypeModelList.get( position ).getImageLink() )
                            .apply( new RequestOptions().placeholder( R.drawable.ic_photo_black_24dp ) ).into( itemImage );
                    itemName.setText( categoryTypeModelList.get( position ).getNameOrExtraText() );

                    itemImage.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onCategoryClick( parent.getContext(), categoryTypeModelList.get( position ).getClickID() );
                        }
                    } );
                    return view;
                }else{
                    ImageView itemImage = view.findViewById( R.id.sq_image_view );
                    TextView itemName =  view.findViewById( R.id.sq_text_view );
                    itemImage.setImageResource( R.drawable.ic_add_black_24dp );
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        itemImage.setImageTintList( parent.getContext().getResources().getColorStateList( R.color.colorPrimary ) );
                    }
                    itemName.setText( "Add New" );
                    itemImage.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: Add New...
                            showToast( "Code Not Found!", parent.getContext() );
                        }
                    } );
                    return view;
                }
            }

            private void onCategoryClick(Context context, String docID){
//                Intent intent = new Intent( context, new  )
                int tempIndex = 0;
//                String catName = "";
                for (HomeCatListModel temp : homeCatListModelList){
                    if ( temp.getCatID().equals( docID ) ){
//                        catName = temp.getCatName();
                        HomeActivity.setForwardFragment( new HomeFragment( tempIndex, docID, temp.getCatName() ) );
                        break;
                    }
                    tempIndex++;
                }
            }
        }

    }
    //==============  GridProduct Grid Layout View Holder =================

    //============  Banner Slider View Holder ============
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView bannerRecyclerView;
        private TextView layoutTitle;
        private TextView indexNo;
        private Button layoutViewAllBtn;
        private TextView warningText;
        private ImageView indexUpBtn;
        private ImageView indexDownBtn;
        private ImageView deleteLayoutBtn;
        private Switch visibleBtn;
        private int temp = 0;
        private Dialog dialog;
        private int layoutPosition;
        private String layoutID;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super( itemView );
            bannerRecyclerView = itemView.findViewById( R.id.horizontal_recycler );
            layoutTitle = itemView.findViewById( R.id.gridLayoutTitle );
            indexNo = itemView.findViewById( R.id.gridIndexNo );
            layoutViewAllBtn = itemView.findViewById( R.id.gridViewAllBtn );
            warningText = itemView.findViewById( R.id.grid_warning_text );
            indexUpBtn = itemView.findViewById( R.id.hrViewUpImgView );
            indexDownBtn = itemView.findViewById( R.id.hrViewDownImgView );
            visibleBtn = itemView.findViewById( R.id.hrViewVisibilitySwitch );
            deleteLayoutBtn = itemView.findViewById( R.id.edit_layout_imgView );
            dialog = DialogsClass.getDialog( itemView.getContext() );
            visibleBtn.setVisibility( View.INVISIBLE );
        }

        private void setData(final String layoutID, final List<BannerModel> bannerList, final int index){
            // TODO : Set Data....
            layoutPosition = 1 + index;
            indexNo.setText( "position : "+ layoutPosition);
            layoutTitle.setText( "Slider Banners " + " (" + bannerList.size() + ")" );
            if (bannerList.size()>=2){
                warningText.setVisibility( View.GONE );
            }else{
                warningText.setVisibility( View.VISIBLE );
                warningText.setText( "Add 2 or more banner to visible this layout to the customers!!" );
            }

            BannerSliderAdaptor bannerItemAdaptor = new BannerSliderAdaptor( catIndex, index ,catID,  bannerList , false );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( itemView.getContext() );
            linearLayoutManager.setOrientation( RecyclerView.HORIZONTAL );
            bannerRecyclerView.setLayoutManager( linearLayoutManager );
            bannerRecyclerView.setAdapter( bannerItemAdaptor );
            bannerItemAdaptor.notifyDataSetChanged();

            layoutViewAllBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ViewAllActivity.viewAllList = bannerAndCatModelList;
//                    Intent viewAllIntent = new Intent( itemView.getContext(), ViewAllActivity.class);
//                    viewAllIntent.putExtra( "TYPE", BANNER_SLIDER_LAYOUT_CONTAINER );
//                    viewAllIntent.putExtra( "CAT_COLL_ID", collectionID );
//                    viewAllIntent.putExtra( "CAT_INDEX", catType );
//                    viewAllIntent.putExtra( "LAY_INDEX", index );
//                    itemView.getContext().startActivity( viewAllIntent );
                }
            } );

            deleteLayoutBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // delete layout
                    if (bannerList.size() <= 1){
                        if (bannerList.size() == 0)
                            alertDialog( itemView.getContext(), index, layoutID, null,null  );
                        else
                            alertDialog( itemView.getContext(), index, layoutID, "SHOPS/" + SHOP_ID +"/banners", bannerList.get( 0 ).getDeleteID()  );
                    }else{
                        showToast( "You have more than 1 banner in this layout!", itemView.getContext() );
                    }
                }
            } );

            // -------  Update Layout...
            setIndexUpDownVisibility( index, indexUpBtn, indexDownBtn ); // set Up and Down Btn Visibility...
            indexUpBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(true, index, dialog );
                }
            } );
            indexDownBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(false, index, dialog );
                }
            } );
            visibleBtn.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ){
                        // Code to Show TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", true );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }else{
                        // Hide the TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", false );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }
                }
            } );



        }

    }
    //============  Banner Slider View Holder ============

    //============  Product Horizontal View Holder ============
    public class ProductHorizontalViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView productRecyclerView;
        private TextView layoutTitle;
        private TextView indexNo;
        private Button layoutViewAllBtn;
        private TextView warningText;
        private ImageView indexUpBtn;
        private ImageView indexDownBtn;
        private ImageView deleteLayoutBtn;
        private Switch visibleBtn;
        private int temp = 0;
        private Dialog dialog;
        private int layoutPosition;
        private String layoutID;
        private ProductHrGridAdaptor productHrGridAdaptor;
        List<ProductModel> tempProductModelList = new ArrayList <>();

        public ProductHorizontalViewHolder(@NonNull View itemView) {
            super( itemView );
            productRecyclerView = itemView.findViewById( R.id.horizontal_recycler );
            layoutTitle = itemView.findViewById( R.id.gridLayoutTitle );
            indexNo = itemView.findViewById( R.id.gridIndexNo );
            layoutViewAllBtn = itemView.findViewById( R.id.gridViewAllBtn );
            warningText = itemView.findViewById( R.id.grid_warning_text );
            indexUpBtn = itemView.findViewById( R.id.hrViewUpImgView );
            indexDownBtn = itemView.findViewById( R.id.hrViewDownImgView );
            visibleBtn = itemView.findViewById( R.id.hrViewVisibilitySwitch );
            deleteLayoutBtn = itemView.findViewById( R.id.edit_layout_imgView );
            dialog = DialogsClass.getDialog( itemView.getContext() );
            visibleBtn.setVisibility( View.INVISIBLE );
        }

        private void setData(String productLayId, String layoutTitle, final List<String> productIDList, List<ProductModel> productModelList, final int index){

            this.layoutTitle.setText( layoutTitle );

            //--------------------------------------------------------------------------------------
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( itemView.getContext() );
            linearLayoutManager.setOrientation( RecyclerView.HORIZONTAL );
            productRecyclerView.setLayoutManager( linearLayoutManager );

            if (productIDList.size()>6){
                layoutViewAllBtn.setVisibility( View.VISIBLE );
            }else {
                layoutViewAllBtn.setVisibility( View.INVISIBLE );
            }

            layoutViewAllBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            } );

            if(productModelList.size() == 0){
                // Load Product...
                if (productIDList.size() > 0){
                    if (productIDList.size()>6){
                        for (int id_no = 0; id_no < 6; id_no++){
                        loadProductData(index, productIDList.get( id_no ));
                        }
                    }else{
                        for (int id_no = 0; id_no < productIDList.size(); id_no++){
                            loadProductData(index, productIDList.get( id_no ));
                        }
                    }
                }else{

                    productHrGridAdaptor = new ProductHrGridAdaptor( catIndex, index, VIEW_HORIZONTAL_LAYOUT, productModelList );
                    productRecyclerView.setAdapter( productHrGridAdaptor );
                    productHrGridAdaptor.notifyDataSetChanged();

                }

            }else{
                productHrGridAdaptor = new ProductHrGridAdaptor( catIndex, index, VIEW_HORIZONTAL_LAYOUT, productModelList );
                productRecyclerView.setAdapter( productHrGridAdaptor );
                productHrGridAdaptor.notifyDataSetChanged();
            }

            //--------------------------------------------------------------------------------------

            deleteLayoutBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // delete layout
                    if (productIDList.size() <= 1){
                        if (productIDList.size() == 0)
                            alertDialog( itemView.getContext(), index, layoutID, null,null  );
                        else
//                            alertDialog( itemView.getContext(), index, layoutID, "SHOPS/" + SHOP_ID +"/banners", bannerList.get( 0 ).getDeleteID()  );
                            showToast( "Code Not Found!", itemView.getContext() );
                    }else{
                        showToast( "You have more than 1 banner in this layout!", itemView.getContext() );
                    }
                }
            } );

            // -------  Update Layout...
            setIndexUpDownVisibility( index, indexUpBtn, indexDownBtn ); // set Up and Down Btn Visibility...
            indexUpBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(true, index, dialog );
                }
            } );
            indexDownBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(false, index, dialog );
                }
            } );
            visibleBtn.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ){
                        // Code to Show TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", true );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }else{
                        // Hide the TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", false );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }
                }
            } );

        }

        // Load Product Data...
        private void loadProductData(final int index, final String productId){
            DBQuery.firebaseFirestore.collection( "SHOPS" ).document( SHOP_ID )
                    .collection( "PRODUCTS" ).document( productId )
                    .get().addOnCompleteListener( new OnCompleteListener <DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        // access the banners from database...
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if ( documentSnapshot.get( "p_no_of_variants" ) !=null ){
//                            String[] pImage;
                            int p_no_of_variants = Integer.valueOf( String.valueOf( (long) documentSnapshot.get( "p_no_of_variants" ) ) );
                            List<ProductSubModel> productSubModelList = new ArrayList <>();
                            for (int tempI = 1; tempI <= p_no_of_variants; tempI++){

                                // We can use Array...
                                ArrayList<String> Images = (ArrayList <String>) documentSnapshot.get( "p_image_" + tempI );
                                int sz = Images.size();
                                String[] pImage = new String[sz];
                                for (int i = 0; i < sz; i++){
                                    pImage[i] = Images.get( i );
                                }

                                // add Data...
                                productSubModelList.add( new ProductSubModel(
                                        task.getResult().get( "p_name_"+tempI).toString(),
                                        pImage,
                                        task.getResult().get( "p_selling_price_"+tempI).toString(),
                                        task.getResult().get( "p_mrp_price_"+tempI).toString(),
                                        task.getResult().get( "p_weight_"+tempI).toString(),
                                        task.getResult().get( "p_stocks_"+tempI).toString(),
                                        task.getResult().get( "p_offer_"+tempI).toString()
                                ) );
                            }
                            String p_id = task.getResult().get( "p_id").toString();
                            String p_main_name = task.getResult().get( "p_main_name" ).toString();
//                        String p_main_image = task.getResult().get( "p_main_image" ).toString();
                            String p_weight_type = task.getResult().get( "p_weight_type" ).toString();
                            int p_veg_non_type = Integer.valueOf( task.getResult().get( "p_veg_non_type" ).toString() );
                            Boolean p_is_cod = (Boolean) task.getResult().get( "p_is_cod" );

                            tempProductModelList.add( new ProductModel(
                                    p_id,
                                    p_main_name,
                                    " ",
                                    p_is_cod,
                                    String.valueOf(p_no_of_variants),
                                    p_weight_type,
                                    p_veg_non_type,
                                    productSubModelList
                            ) );
                            homeCatListModelList.get( catIndex ).getHomeListModelList().get( index ).setProductModelList( tempProductModelList );

                            if (productRecyclerView != null){
                                if (productHrGridAdaptor == null){
                                    productHrGridAdaptor = new ProductHrGridAdaptor( catIndex, index, VIEW_HORIZONTAL_LAYOUT, tempProductModelList );
                                    productRecyclerView.setAdapter( productHrGridAdaptor );
                                    productHrGridAdaptor.notifyDataSetChanged();
                                }else{
                                    productHrGridAdaptor.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                    else{
                        String error = task.getException().getMessage();
//                                    showToast(error);
//                                    dialog.dismiss();
                    }
                }
            } );

        }

    }
    //============  Product Horizontal View Holder ============

    //============  Product Grid View Holder ============
    public class ProductGridViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView productRecyclerView;
        private TextView layoutTitle;
        private TextView indexNo;
        private Button layoutViewAllBtn;
        private TextView warningText;
        private ImageView indexUpBtn;
        private ImageView indexDownBtn;
        private ImageView deleteLayoutBtn;
        private Switch visibleBtn;
        private int temp = 0;
        private Dialog dialog;
        private int layoutPosition;
        private String layoutID;

        public ProductGridViewHolder(@NonNull View itemView) {
            super( itemView );
            productRecyclerView = itemView.findViewById( R.id.horizontal_recycler );
            layoutTitle = itemView.findViewById( R.id.gridLayoutTitle );
            indexNo = itemView.findViewById( R.id.gridIndexNo );
            layoutViewAllBtn = itemView.findViewById( R.id.gridViewAllBtn );
            warningText = itemView.findViewById( R.id.grid_warning_text );
            indexUpBtn = itemView.findViewById( R.id.hrViewUpImgView );
            indexDownBtn = itemView.findViewById( R.id.hrViewDownImgView );
            visibleBtn = itemView.findViewById( R.id.hrViewVisibilitySwitch );
            deleteLayoutBtn = itemView.findViewById( R.id.edit_layout_imgView );
            dialog = DialogsClass.getDialog( itemView.getContext() );
            visibleBtn.setVisibility( View.INVISIBLE );
        }

        private void setData(String productLayId, String layoutTitle, final List<String> productIDList, List<ProductModel> productModelList, final int index){
            this.layoutTitle.setText( layoutTitle );

            // -------------------------------------------------------------------------------------




            // -------------------------------------------------------------------------------------


            deleteLayoutBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // delete layout
                    if (productIDList.size() <= 1){
                        if (productIDList.size() == 0)
                            alertDialog( itemView.getContext(), index, layoutID, null,null  );
                        else
//                            alertDialog( itemView.getContext(), index, layoutID, "SHOPS/" + SHOP_ID +"/banners", bannerList.get( 0 ).getDeleteID()  );
                            showToast( "Code Not Found!", itemView.getContext() );
                    }else{
                        showToast( "You have more than 1 banner in this layout!", itemView.getContext() );
                    }

                }
            } );

            // -------  Update Layout...
            setIndexUpDownVisibility( index, indexUpBtn, indexDownBtn ); // set Up and Down Btn Visibility...
            indexUpBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(true, index, dialog );
                }
            } );
            indexDownBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIndex(false, index, dialog );
                }
            } );
            visibleBtn.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ){
                        // Code to Show TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", true );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }else{
                        // Hide the TabView...
                        String layoutId = homePageList.get( index ).getLayoutID();
                        Map <String, Object> updateMap = new HashMap <>();
                        updateMap.put( "is_visible", false );
                        dialog.show();
                        updateOnDocument(dialog, layoutId, updateMap);
                    }
                }
            } );

        }

    }
    //============  Product Grid View Holder ============


    private void setIndexUpDownVisibility( int index, ImageView indexUpBtn,  ImageView indexDownBtn){
        if (homePageList.size()>1){
            indexUpBtn.setVisibility( View.VISIBLE );
            indexDownBtn.setVisibility( View.VISIBLE );
            if (index == 0){
                indexUpBtn.setVisibility( View.INVISIBLE );
            }else if (index == homePageList.size()-1){
                indexDownBtn.setVisibility( View.INVISIBLE );
            }
        }else{
            indexUpBtn.setVisibility( View.INVISIBLE );
            indexDownBtn.setVisibility( View.INVISIBLE );
        }
    }
    private void updateIndex(boolean isMoveUp, int index, Dialog dialog){
        dialog.show();
        if (isMoveUp){
            // GoTo Up...
            updateIndexOnDatabase(index, (index - 1), dialog );
        }else{
            // Goto Down..
            updateIndexOnDatabase(index, (index + 1), dialog );
        }
    }
    private void updateIndexOnDatabase(final int startInd, final int endInd, final Dialog dialog){
        String[] layoutId = new String[]{ homePageList.get( startInd ).getLayoutID(), homePageList.get( endInd ).getLayoutID() };

//        Collections.swap( homePageList, startInd, endInd );
        Collections.swap( homeCatListModelList.get( catIndex ).getHomeListModelList(), startInd, endInd );
        // TODO : Notify...
        HomeFragment.homePageAdaptor.notifyDataSetChanged();

        for ( String tempId : layoutId){
//            if (!dialog.isShowing()){
//                dialog.show();
//            }
            Map <String, Object> updateMap = new HashMap <>();
            updateMap.clear();
            if (tempId.equals( layoutId[0] )){
                updateMap.put( "index", ( endInd ) );
            }else{
                updateMap.put( "index", ( startInd ) );
            }
            updateOnDocument(dialog, tempId, updateMap);
        }

    }
    private void updateOnDocument(final Dialog dialog, String layoutId, Map <String, Object> updateMap){
        firebaseFirestore.collection( "SHOPS" ).document( SHOP_ID )
                .collection( catID ).document( layoutId ).update( updateMap )
                .addOnCompleteListener( new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull Task <Void> task) {
                        dialog.dismiss();
                        HomeFragment.homePageAdaptor.notifyDataSetChanged();
                    }
                } );
    }

    private void showToast(String msg, Context context){
        Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
    }
    private void alertDialog(final Context context, final int index, final String layoutId
            , @Nullable final String deletePath, @Nullable final String deleteID){
        AlertDialog.Builder alertD = new AlertDialog.Builder( context );
        alertD.setTitle( "Do You want to delete this Layout.?" );
        alertD.setMessage( "If you delete this layout, you will loose all the inside data of the layout.!" );
        alertD.setCancelable( false );
        alertD.setPositiveButton( "DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final Dialog pDialog = DialogsClass.getDialog( context );
                pDialog.show();
                // Query to delete the Layout...!
                firebaseFirestore.collection( "SHOPS" ).document( SHOP_ID )
                        .collection( catID ).document( layoutId ).delete()
                        .addOnCompleteListener( new OnCompleteListener <Void>() {
                            @Override
                            public void onComplete(@NonNull Task <Void> task) {
                                if (task.isSuccessful()){
                                    showToast( "Deleted Layout Successfully.!", context );
                                    // : Update in local list..!
                                    homePageList.remove( index );
                                    // Notify Data Changed...
                                    HomeFragment.homePageAdaptor.notifyDataSetChanged();
                                    // Delete Process...
                                    if (deleteID != null){
//                                        UpdateImages.deleteImageFromFirebase( context, null
//                                                , CURRENT_CITY_CODE + deletePath
//                                                , deleteID  );
                                    }
                                }else {
                                    showToast( "Failed.! Something went wrong.!", context );
                                }
                                pDialog.dismiss();
                            }
                        } );

            }
        } );
        alertD.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        } );
        alertD.show();
    }

    // Add Category...


}
