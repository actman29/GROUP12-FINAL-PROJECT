package com.example.marklee.group12_final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
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
        View res = inflater.inflate(R.layout.fragment_weather, container, false);
        ProgressBar progressBar = (ProgressBar)res.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        return res;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        new ForecastQuery().execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ForecastQuery extends AsyncTask<Object, Object, InputStream> {
        private String minTem;
        private String maxTem;
        private String currentTem;
        private Bitmap pictureTem;

        @Override
        protected InputStream doInBackground(Object... params) {
            try {
                String weathercasturl = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
                URL url = new URL(weathercasturl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                parse(conn.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        List parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                ProgressBar mpro = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                mpro.setVisibility(View.VISIBLE);
                publishProgress(0);
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    if (name.equals("temperature")) {
                        currentTem = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTem = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxTem = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (name.equals("weather")) {
                        String iconfile = parser.getAttributeValue(null, "icon") + ".png";

                        if (!fileExistence(iconfile)) {
                            pictureTem = HttpUtils.getImage("http://openweathermap.org/img/w/" + iconfile);
                            FileOutputStream outputStream = getActivity().openFileOutput(iconfile, Context.MODE_PRIVATE);
                            pictureTem.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                        FileInputStream fis;
                        try {
                            fis = new FileInputStream(getActivity().getBaseContext().getFileStreamPath(iconfile));
                            pictureTem = BitmapFactory.decodeStream(fis);
                            publishProgress(100);
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        boolean fileExistence(String fName){
            File file = getActivity().getBaseContext().getFileStreamPath(fName);
            return file.exists();
        }

        protected void onProgressUpdate(Object... progress) {
            ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            progressBar.setProgress((Integer) progress[0]);

        }

        @Override
        protected void onPostExecute(InputStream result) {
            ((TextView) getActivity().findViewById(R.id.minTem)).setText(getMinTem());
            ((TextView) getActivity().findViewById(R.id.maxTem)).setText(getMaxTem());
            ((TextView) getActivity().findViewById(R.id.currentTem)).setText(getCurrentTem());
            ((ImageView) getActivity().findViewById(R.id.weatherImage)).setImageBitmap(getPictureTem());
            getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        }

        String getMinTem() {
            return minTem;
        }
        String getMaxTem() {
            return maxTem;
        }
        String getCurrentTem() {
            return currentTem;
        }
        Bitmap getPictureTem() {
            return pictureTem;
        }
    }

}
