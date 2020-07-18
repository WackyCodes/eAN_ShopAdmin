package wackycodes.ecom.eanshopadmin.home.bannerslider;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.addnew.AddNewLayoutActivity;
import wackycodes.ecom.eanshopadmin.model.BannerModel;

import static wackycodes.ecom.eanshopadmin.database.DBQuery.homeCatListModelList;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.BANNER_SLIDER_CONTAINER_ITEM;

public class BannerSliderAdaptor  extends RecyclerView.Adapter<BannerSliderAdaptor.ViewHolder> {

    int catIndex;
    int layoutIndex;
    String catOrCollectionID;
    List <BannerModel> bannerModelList;
    boolean isViewAll;

    // TODO : We don't have here layout ID...

    public BannerSliderAdaptor(int catIndex, int layoutIndex, String catOrCollectionID, List <BannerModel> bannerModelList, boolean isViewAll) {
        this.catIndex = catIndex;
        this.layoutIndex = layoutIndex;
        this.catOrCollectionID = catOrCollectionID;
        this.bannerModelList = bannerModelList;
        this.isViewAll = isViewAll;
    }

    @NonNull
    @Override
    public BannerSliderAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bannerView =  LayoutInflater.from( parent.getContext() ).inflate( R.layout.banner_slider_item_layout, parent, false );
        return new ViewHolder( bannerView );
    }

    @Override
    public void onBindViewHolder(@NonNull BannerSliderAdaptor.ViewHolder holder, int position) {
        if (position < bannerModelList.size() ){
            String imgLink = bannerModelList.get( position ).getImageLink();
//            String bgColor = bannerAndCatModelList.get( position ).getTitleOrBgColor();
            String clickID = bannerModelList.get( position ).getClickID();
            int clickType = bannerModelList.get( position ).getClickType();
            holder.setData( imgLink, clickID, clickType);
        }
        if ( !isViewAll && position == bannerModelList.size() ){
            holder.setAddNew();
        }
    }

    @Override
    public int getItemCount() {
        if (isViewAll){
            return bannerModelList.size();
        }else {
            return bannerModelList.size()+1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView bannerImage;
        private LinearLayout addNewItemLayout;
        private ImageView editBannerLayBtn;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            bannerImage = itemView.findViewById( R.id.banner_slider_Image );
            addNewItemLayout = itemView.findViewById( R.id.add_new_item_Linearlayout );
            editBannerLayBtn = itemView.findViewById( R.id.update_item_lay_imgBtn );
        }

        private void setData(String imgLink, final String clickID, final int clickType ){
            if (isViewAll){
                editBannerLayBtn.setVisibility( View.VISIBLE );
            }
            bannerImage.setVisibility( View.VISIBLE );
            addNewItemLayout.setVisibility( View.GONE );
            Glide.with( itemView.getContext() ).load( imgLink )
                    .apply( new RequestOptions().placeholder( R.drawable.ic_panorama_black_24dp ) ).into( bannerImage );
            // Set Background color...

            bannerImage.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    switch ( clickType ){
//                        case BANNER_CLICK_TYPE_WEBSITE:
//                            StaticMethods.gotoURL( itemView.getContext(), clickID );
//                            break;
//                        case BANNER_CLICK_TYPE_SHOP:
//                            Intent intent = new Intent( itemView.getContext(), ShopHomeActivity.class );
//                            intent.putExtra( "SHOP_ID", clickID );
//                            itemView.getContext().startActivity( intent );
//                            break;
//                    }
                    Toast.makeText( itemView.getContext(), "Method is not written", Toast.LENGTH_SHORT ).show();
                }
            } );
        }
        private void setAddNew(){
            bannerImage.setVisibility( View.GONE );
            addNewItemLayout.setVisibility( View.VISIBLE );
            addNewItemLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start Activity...
//                    if (catOrCollectionID.equals( "HOME" )){
//                        AddNewLayoutActivity.isHomePage = true;
//                    }else{
//                        AddNewLayoutActivity.isHomePage = false;
//                    }
//                    Intent intent = new Intent( itemView.getContext(), AddNewLayoutActivity.class );
//                    intent.putExtra( "CAT_ID", catOrCollectionID );
////                    intent.putExtra( "LAY_TYPE", BANNER_SLIDER_CONTAINER_ITEM );
//                    intent.putExtra( "LAY_INDEX", layoutIndex );
//                    intent.putExtra( "TASK_UPDATE", false );
//                    intent.putExtra( "CAT_INDEX", catIndex );
//                    itemView.getContext().startActivity( intent );

                    Intent intent = new Intent( itemView.getContext(), AddNewLayoutActivity.class );
                    intent.putExtra( "LAY_TYPE", BANNER_SLIDER_CONTAINER_ITEM );
                    intent.putExtra( "LAY_INDEX", homeCatListModelList.get( catIndex ).getHomeListModelList().size()-1 );
                    intent.putExtra( "CAT_INDEX", catIndex );
                    intent.putExtra( "TASK_UPDATE", true );
                    itemView.getContext().startActivity( intent );

//                    Toast.makeText( itemView.getContext(), "Method is not written", Toast.LENGTH_SHORT ).show();
                }
            } );
        }

    }


}
