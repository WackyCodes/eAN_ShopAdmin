package wackycodes.ecom.eanshopadmin.main.orderlist;

/*
 * Copyright (c) 2020.
 * WackyCodes : Tech Services.
 * https://linktr.ee/wackycodes
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import wackycodes.ecom.eanshopadmin.R;

import static wackycodes.ecom.eanshopadmin.other.StaticMethods.showToast;

/**
 * Created by Shailendra (WackyCodes) on 31/07/2020 21:21
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class OrderListAdaptor extends RecyclerView.Adapter {

    private List <OrderListModel> orderListModelList;

    public OrderListAdaptor(List <OrderListModel> orderListModelList) {
        this.orderListModelList = orderListModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from( parent.getContext() ).inflate(
                R.layout.order_list_layout_item, parent, false );
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderListModel orderListModel = orderListModelList.get( position );

        String orderID = orderListModel.getOrderID();
        String pName = orderListModel.getOrderProductItemsList().get( 0 ).getProductName();
        String oItemsAmounts = orderListModel.getProductAmounts();
        String oStatus = orderListModel.getDeliveryStatus();
        String oDate = orderListModel.getOrderDate();
        String oTime = orderListModel.getOrderTime();
        int oTotalItems = orderListModel.getOrderProductItemsList().size();
        String pImage = orderListModel.getOrderProductItemsList().get( 0 ).getProductImage();

        // TODO : Set Data...
        ((OrderListViewHolder)holder).setData( orderID, pName, oItemsAmounts, oStatus, oDate, oTime, oTotalItems, pImage );
    }

    @Override
    public int getItemCount() {
        return orderListModelList.size();
    }


    //-------------------------------------------------

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        private TextView orderId;
        private TextView productName;
        private TextView orderItemsAmount;
        private TextView orderStatus;
        private TextView orderTime;
        private TextView totalItems;
        private ImageView productImage;

        public OrderListViewHolder(@NonNull View itemView) {
            super( itemView );

            orderId = itemView.findViewById( R.id.order_id );
            productName = itemView.findViewById( R.id.product_name );
            orderItemsAmount = itemView.findViewById( R.id.product_items_amount );
            orderStatus = itemView.findViewById( R.id.order_status );
            orderTime = itemView.findViewById( R.id.order_time );
            totalItems = itemView.findViewById( R.id.product_qty );
            productImage = itemView.findViewById( R.id.product_image );

        }

        private void setData( String orderID, String pName, String oItemsAmounts, String oStatus,  String oDate, String oTime, int oTotalItems, String pImage ){

            // set Image Resource from database..
            Glide.with( itemView.getContext() ).load( pImage )
                    .apply( new RequestOptions().placeholder( R.drawable.ic_photo_black_24dp ) ).into( productImage );

            orderId.setText( orderID );
            productName.setText( pName );
            orderItemsAmount.setText( "Rs." + oItemsAmounts +"/-" );
            orderStatus.setText( oStatus );
            orderTime.setText( "Order " + oTime );
            totalItems.setText( String.valueOf( oTotalItems ) );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast(itemView.getContext(), "Code Not Found!");
                }
            } );

        }

    }



}
