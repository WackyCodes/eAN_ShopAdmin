package wackycodes.ecom.eanshopadmin.product.specifications;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wackycodes.ecom.eanshopadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsSpecificationFragment extends Fragment {


    public ProductDetailsSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_product_details_specification, container, false );
    }

}
