package cn.moon.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.net.CartModel;
import cn.moon.fulicenter.model.net.ICartModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    ICartModel mModel;

    public CartFragment() {
        mModel = new CartModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        return layout;
    }

}
