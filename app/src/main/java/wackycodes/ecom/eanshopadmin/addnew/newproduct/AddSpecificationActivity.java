package wackycodes.ecom.eanshopadmin.addnew.newproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wackycodes.ecom.eanshopadmin.R;
import wackycodes.ecom.eanshopadmin.home.HomeFragment;

import static wackycodes.ecom.eanshopadmin.database.DBQuery.firebaseFirestore;
import static wackycodes.ecom.eanshopadmin.database.DBQuery.homeCatListModelList;
import static wackycodes.ecom.eanshopadmin.other.StaticMethods.showToast;
import static wackycodes.ecom.eanshopadmin.other.StaticValues.SHOP_ID;

public class AddSpecificationActivity extends AppCompatActivity {

    public static List <AddSpecificationModel> specificationModelList = new ArrayList <>();
    public static AddSpecificationAdaptor specificationAdaptor;

    private EditText descriptionEditText;
    private RecyclerView specificationRecycler;
    private Button uploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_specification );

        descriptionEditText = findViewById( R.id.new_pro_description_etext );
        specificationRecycler = findViewById( R.id.new_pro_specifications_recycler );
        uploadBtn = findViewById( R.id.upload_specification_btn );

        /// Set Layout ...
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        specificationRecycler.setLayoutManager( linearLayoutManager );
        // Adaptor...
        specificationAdaptor = new AddSpecificationAdaptor( specificationModelList );
        specificationRecycler.setAdapter( specificationAdaptor );
        specificationAdaptor.notifyDataSetChanged();

        // Upload Button...
        uploadBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty( descriptionEditText.getText().toString() )){
                    showToast(AddSpecificationActivity.this, "Please Fill Required Fields!");
                    descriptionEditText.setError( "Required!" );
                }
                else{
                    if (isListNotEmpty()){
                        // TODO: Upload Data...
                        showToast(AddSpecificationActivity.this, "Code Not Set Yet!");
                    }
                }
            }
        } );

    }

    private boolean isListNotEmpty(){

        if (specificationModelList.size() == 0){
            showToast(AddSpecificationActivity.this, "Please Add Features!");
            return false;
        }else{
            if (specificationModelList.get( 0 ).getSpHeading() == null){
                showToast(AddSpecificationActivity.this, "Please Enter Feateres title!");
                return false;
            }
            if (specificationModelList.get( 0 ).getSpecificationFeatureModelList().size() == 0){
                showToast(AddSpecificationActivity.this, "Please Add Some Features!");
                return false;
            }
            return true;
        }
    }

    private void updateSpecification(){

        String categoryID = "categoryID";
        String productID = "productID";

        Map <String, Object> updateMap = new HashMap <>();
//        int pListIndex = homeCatListModelList.get( catIndex ).getHomeListModelList().get( layIndex ).getProductIdList().size() + 1;
//        updateMap.put( "no_of_products", pListIndex  );
//        updateMap.put( "product_id_"+pListIndex, productID  );
        updateMap.put( "visibility", true  );
        updateMap.put( "specifications", specificationModelList );

        firebaseFirestore
                .collection( "SHOPS" ).document( SHOP_ID )
                .collection( "PRODUCTS" ).document( productID )
                .update( updateMap )
                .addOnCompleteListener( new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull Task <Void> task) {
                        if (task.isSuccessful()){
//                            dialog.dismiss();
//                            showToast( "Added Successfully..!" );
                            HomeFragment.homePageAdaptor.notifyDataSetChanged();
                            finish();
                        }
                        else{
//                            dialog.dismiss();
//                            showToast( "Product Added..! Error : " + task.getException().getMessage() );
                            finish();
                        }
                    }
                } );

    }


}
