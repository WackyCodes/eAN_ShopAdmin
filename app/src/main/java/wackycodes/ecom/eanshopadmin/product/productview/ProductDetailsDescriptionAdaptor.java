package wackycodes.ecom.eanshopadmin.product.productview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import wackycodes.ecom.eanshopadmin.product.description.ProductDetailsDescriptionFragment;
import wackycodes.ecom.eanshopadmin.product.specifications.ProductDetailsSpecificationFragment;
import wackycodes.ecom.eanshopadmin.product.specifications.ProductDetailsSpecificationModel;

public class ProductDetailsDescriptionAdaptor extends FragmentPagerAdapter {

    int totalTabs;
    private String productSpecificationText;
    private List <ProductDetailsSpecificationModel> productDetailsSpecificationModelList;

    public ProductDetailsDescriptionAdaptor(FragmentManager fm, int totalTabs, String productSpecificationText,
                                            List <ProductDetailsSpecificationModel> productDetailsSpecificationModelList) {
        super( fm );
        this.totalTabs = totalTabs;
        this.productSpecificationText = productSpecificationText;
        this.productDetailsSpecificationModelList = productDetailsSpecificationModelList;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDetailsDescriptionFragment productDetailsDescriptionFragment = new ProductDetailsDescriptionFragment();
                ProductDetailsDescriptionFragment.productDescription = productSpecificationText;
                return productDetailsDescriptionFragment;
            case 1:
                ProductDetailsSpecificationFragment productDetailsSpecificationFragment = new ProductDetailsSpecificationFragment();
                ProductDetailsSpecificationFragment.productDetailsSpecificationModelList = productDetailsSpecificationModelList;
                return productDetailsSpecificationFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
