package com.example.marklee.group12_final_project;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.example.marklee.group12_final_project.R.drawable.close;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GarageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GarageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean bLight = false;
    private boolean bDoor = false;
    private CharSequence toastText = "";// "Switch is Off"


    public GarageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GarageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GarageFragment newInstance(String param1, String param2) {
        GarageFragment fragment = new GarageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View res = inflater.inflate(R.layout.fragment_garage, container, false);
        ImageView door = (ImageView) res.findViewById(R.id.imageView);
        door.setImageResource(R.drawable.close);

        Switch swD = (Switch)res.findViewById(R.id.switch_door);
        swD.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bDoor = false;
                bLight = false;

                if(isChecked){
                    toastText = "Door Opened";// "Switch is Off"
                    bDoor = true;
                    bLight = true;
                } else {
                    toastText = "Door Closed";// "Switch is Off"
                    bDoor = false;
                    bLight = false;
                }
                setDoorState();

            }
        });
        Switch swL = (Switch)res.findViewById(R.id.switch_light);
        swL.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!bDoor)
                {
                    toastText = "Cannot turn on, the garage door is closed.";// "Switch is Off"
                    bLight = false;
                }
                else if(isChecked){
                    toastText = "Light turned on";// "Switch is Off"
                    bLight = true;
                } else {
                    toastText = "Light turned off";// "Switch is Off"
                    bLight = false;
                }
                setDoorState();
                Toast toast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG); //this is the ListActivity
                toast.show(); //display your message box

            }
        });
        return res;
    }

    private void setDoorState() {
        ImageView door = (ImageView) getActivity().findViewById(R.id.imageView);
        Switch swL = (Switch)getActivity().findViewById(R.id.switch_light);
        swL.setChecked(bLight);
        if(bDoor == false)
        {
            door.setImageResource(R.drawable.close);
        } else if (bLight == true)
        {
            door.setImageResource(R.drawable.open_turnon);
        } else if (bLight == false )
        {
            door.setImageResource(R.drawable.open_turnoff);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        parent = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


}
