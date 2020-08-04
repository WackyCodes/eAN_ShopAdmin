package wackycodes.ecom.eanshopadmin.main.orderlist;

/*
 * Copyright (c) 2020.
 * WackyCodes : Tech Services.
 * https://linktr.ee/wackycodes
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import wackycodes.ecom.eanshopadmin.R;

import static wackycodes.ecom.eanshopadmin.other.StaticMethods.showToast;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_CHECK;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_NEW_ORDER;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_PREPARING;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.ORDER_LIST_READY_TO_DELIVER;

/**
 * Created by Shailendra (WackyCodes) on 31/07/2020 21:21
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class OrderListAdaptor extends RecyclerView.Adapter {

    private List <OrderListModel> orderListModelList;
    private int listType;

    public OrderListAdaptor(List <OrderListModel> orderListModelList, int listType) {
        this.orderListModelList = orderListModelList;
        this.listType = listType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (listType){
            case ORDER_LIST_CHECK:
                View orderListView =  LayoutInflater.from( parent.getContext() ).inflate(
                        R.layout.order_list_layout_item, parent, false );
                return new OrderListViewHolder(orderListView);

            case ORDER_LIST_NEW_ORDER:
            case ORDER_LIST_PREPARING:
            case ORDER_LIST_READY_TO_DELIVER:
                View newOrderListView =  LayoutInflater.from( parent.getContext() ).inflate(
                        R.layout.new_order_list_layout_item, parent, false );
                return new NewOrderListViewHolder(newOrderListView);

            default:
                return null;
        }

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
        switch (listType){
            case ORDER_LIST_CHECK:
                // TODO : Set Data...
                ((OrderListViewHolder)holder).setData( orderID, pName, oItemsAmounts, oStatus, oDate, oTime, oTotalItems, pImage );
                break;
            case ORDER_LIST_NEW_ORDER:
            case ORDER_LIST_PREPARING:
            case ORDER_LIST_READY_TO_DELIVER:
                ((NewOrderListViewHolder)holder).setData( orderID, pName, oItemsAmounts, oStatus, oDate, oTime, oTotalItems, pImage );
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
//        if (orderListModelList.size() == 0){
//            OrderListFragment.no_order_text.setVisibility( View.VISIBLE );
//        }else{
//            OrderListFragment.no_order_text.setVisibility( View.GONE );
//        }
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
            // Current Date : "yyyy/MM/dd"
            // Current Time : "HH:mm"

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

    public class NewOrderListViewHolder extends RecyclerView.ViewHolder{
        private TextView orderId;
        private TextView productName;
        private TextView orderItemsAmount;
        private TextView orderStatus;
        private TextView orderTime;
        private TextView totalItems;
        private ImageView productImage;
        // New Order Action Layout...
        private LinearLayout newOrderActionLayout;
        private TextView rejectOrderBtn;
        private TextView acceptOrderBtn;
        // Order Preparing Text Button...
        private TextView packingTextBtn;
        // Out For Delivery Layout...
        private LinearLayout outForDeliveryLayout;
        private EditText outForDeliveryPinEt;
        private TextView outForDeliveryBtn;
        //

        public NewOrderListViewHolder(@NonNull View itemView) {
            super( itemView );
            orderId = itemView.findViewById( R.id.order_id );
            productName = itemView.findViewById( R.id.product_name );
            orderItemsAmount = itemView.findViewById( R.id.product_items_amount );
            orderStatus = itemView.findViewById( R.id.order_status );
            orderTime = itemView.findViewById( R.id.order_time );
            totalItems = itemView.findViewById( R.id.product_qty );
            productImage = itemView.findViewById( R.id.product_image );
            // ---
            newOrderActionLayout = itemView.findViewById( R.id.new_order_action_layout );
            rejectOrderBtn = itemView.findViewById( R.id.order_reject_text );
            acceptOrderBtn = itemView.findViewById( R.id.order_accept_text );
            // ------
            packingTextBtn = itemView.findViewById( R.id.preparing_packing_text_btn );
            // ---
            outForDeliveryLayout = itemView.findViewById( R.id.new_order_out_for_delivery_layout );
            outForDeliveryPinEt = itemView.findViewById( R.id.out_for_delivery_pin_et );
            outForDeliveryBtn = itemView.findViewById( R.id.out_for_delivery_text_btn );

        }

        private void setData( String orderID, String pName, String oItemsAmounts, String oStatus,  String oDate, String oTime, int oTotalItems, String pImage ){

            // Decide based On List Type...
            switch (listType){
                case ORDER_LIST_NEW_ORDER:
                    newOrderActionLayout.setVisibility( View.VISIBLE );
                    packingTextBtn.setVisibility( View.GONE );
                    outForDeliveryLayout.setVisibility( View.GONE );
                    break;
                case ORDER_LIST_PREPARING:
                    newOrderActionLayout.setVisibility( View.GONE );
                    packingTextBtn.setVisibility( View.VISIBLE );
                    outForDeliveryLayout.setVisibility( View.GONE );
                    break;
                case ORDER_LIST_READY_TO_DELIVER:
                    newOrderActionLayout.setVisibility( View.GONE );
                    packingTextBtn.setVisibility( View.GONE );
                    outForDeliveryLayout.setVisibility( View.VISIBLE );
                    break;
                default:
                    break;
            }

            // set Image Resource from database...
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
