package wackycodes.ecom.eanshopadmin.admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wackycodes.ecom.eanshopadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdminDataFragment extends Fragment {


    public AddAdminDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_add_admin_data, container, false );
    }

}
