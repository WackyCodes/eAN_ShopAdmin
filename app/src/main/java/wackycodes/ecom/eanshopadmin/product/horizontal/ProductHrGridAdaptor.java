package wackycodes.ecom.eanshopadmin.product.horizontal;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.addnew.AddNewProductActivity;
import wackycodes.ecom.eanshopadmin.product.ProductModel;
import wackycodes.ecom.eanshopadmin.product.productview.ProductDetails;

import static wackycodes.ecom.eanshopadmin.other.StaticValues.VIEW_GRID_LAYOUT;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.VIEW_HORIZONTAL_LAYOUT;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.VIEW_RECTANGLE_LAYOUT;

public class ProductHrGridAdaptor extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private int crrShopCatIndex;
    private int layoutIndex;
    private int viewType;
    private List <ProductModel> productModelList;

    public ProductHrGridAdaptor(int crrShopCatIndex, int layoutIndex, int viewType, List <ProductModel> productModelList) {
        this.crrShopCatIndex = crrShopCatIndex;
        this.layoutIndex = layoutIndex;
        this.viewType = viewType;
        this.productModelList = productModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (viewType) {
            case VIEW_HORIZONTAL_LAYOUT:
                return VIEW_HORIZONTAL_LAYOUT;
            case VIEW_RECTANGLE_LAYOUT:
                return VIEW_RECTANGLE_LAYOUT;
            case VIEW_GRID_LAYOUT:
                return VIEW_GRID_LAYOUT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO : Case...
        View hrView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.product_square_layout_item, parent, false );
        return new HomeHorizontalViewHolder( hrView );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position < productModelList.size()){
            // TODO : Case...
            String productId = productModelList.get( position ).getpProductID();
            List<String> imgLink = productModelList.get( position ).getProductSubModelList().get( 0 ).getpImage();
            String name = productModelList.get( position ).getProductSubModelList().get( 0 ).getpName();
            String price = productModelList.get( position ).getProductSubModelList().get( 0 ).getpSellingPrice();
            String cutPrice = productModelList.get( position ).getProductSubModelList().get( 0 ).getpMrpPrice();

            ((HomeHorizontalViewHolder) holder).setHomeHrProduct( productId, imgLink, name, price, cutPrice, position );
        }else{
            ((HomeHorizontalViewHolder) holder).setAddNewProductView();
        }

    }

    @Override
    public int getItemCount() {
        if (viewType == VIEW_HORIZONTAL_LAYOUT ){
            return productModelList.size()+1;
        }else {
            return productModelList.size();
        }
    }

    public class HomeHorizontalViewHolder extends RecyclerView.ViewHolder {

        ImageView hrProductImage;
        TextView hrProductName;
        TextView hrProductPrice;
        TextView hrProductCutPrice;
        TextView hrProductOffPercentage;

        ConstraintLayout productView;
        LinearLayout addNewProductView;

        public HomeHorizontalViewHolder(@NonNull View itemView) {
            super( itemView );

            hrProductImage = itemView.findViewById( R.id.hr_product_image );
            hrProductName = itemView.findViewById( R.id.hr_product_name );
            hrProductPrice = itemView.findViewById( R.id.hr_product_price );
            hrProductCutPrice = itemView.findViewById( R.id.hr_product_cut_price );
            hrProductOffPercentage = itemView.findViewById( R.id.hr_off_percentage );

            productView = itemView.findViewById( R.id.product_view_const_layout );
            addNewProductView = itemView.findViewById( R.id.product_view_linear_layout );

        }

        private void setHomeHrProduct(final String productId,List<String> imgLink, String name, String price, String cutPrice, final int index) {

            addNewProductView.setVisibility( View.GONE );
            productView.setVisibility( View.VISIBLE );

            hrProductName.setText( name );
            hrProductPrice.setText( "Rs." + price + "/-" );
            hrProductCutPrice.setText( "Rs." + cutPrice + "/-" );

            Glide.with( itemView.getContext() ).load( imgLink.get( 0 ) ).apply( new RequestOptions()
                    .placeholder( R.drawable.ic_photo_black_24dp ) ).into( hrProductImage );

            int perOff = ((Integer.parseInt( cutPrice ) - Integer.parseInt( price )) * 100) / Integer.parseInt( cutPrice );
            hrProductOffPercentage.setText( perOff + "% Off" );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent( itemView.getContext(), ProductDetails.class );
                    productDetailIntent.putExtra( "PRODUCT_ID", productId );
                    productDetailIntent.putExtra( "HOME_CAT_INDEX", crrShopCatIndex );
                    productDetailIntent.putExtra( "LAYOUT_INDEX", layoutIndex );
                    productDetailIntent.putExtra( "PRODUCT_INDEX", index );
                    itemView.getContext().startActivity( productDetailIntent );

                }
            } );

        }

        private void setAddNewProductView(){
            addNewProductView.setVisibility( View.VISIBLE );
            productView.setVisibility( View.GONE );

            addNewProductView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addProduct = new Intent( itemView.getContext(), AddNewProductActivity.class );

                    addProduct.putExtra( "CAT_INDEX", crrShopCatIndex );
                    addProduct.putExtra( "LAY_INDEX", layoutIndex );
                    addProduct.putExtra( "UPDATE", false );

                    itemView.getContext().startActivity( addProduct );
                }
            } );

        }

    }


}


