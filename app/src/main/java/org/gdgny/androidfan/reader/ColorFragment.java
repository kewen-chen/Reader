package org.gdgny.androidfan.reader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ColorFragment extends Fragment
{
    public static final String sARGUMENT_COLOR = "backgroundColor";

    public static ColorFragment newInstance(Bundle bundle)
    {
        ColorFragment colorFragment = new ColorFragment();

        if (bundle != null)
        {
            colorFragment.setArguments(bundle);
        }

        return colorFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // The last two arguments ensure LayoutParams are inflated properly
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        initialise(view);

        return view;
    }


    private void initialise(View view)
    {
        // Set the background color
        view.getRootView().setBackgroundColor(getResources().getColor(getArguments().getInt(sARGUMENT_COLOR)));
    }
}
