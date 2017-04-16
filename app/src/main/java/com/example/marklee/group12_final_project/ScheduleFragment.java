package com.example.marklee.group12_final_project;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.marklee.group12_final_project.R.id.btDelete;
import static com.example.marklee.group12_final_project.R.id.scheduleList;
import static com.example.marklee.group12_final_project.R.id.spinner;
import static com.example.marklee.group12_final_project.ScheduleDatabaseHelper.KEY_ID;
import static com.example.marklee.group12_final_project.ScheduleDatabaseHelper.SCHEDULE_TIME;
import static com.example.marklee.group12_final_project.ScheduleDatabaseHelper.TABLE_NAME;
import static com.example.marklee.group12_final_project.ScheduleDatabaseHelper.TEMPETURE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<TempSchedule> listItems;
    ScheduleAdapter<TempSchedule> adapter;
    SQLiteDatabase mydb = null;
    ListView scheduleList;
    private int ps;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VersionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        View res = inflater.inflate(R.layout.fragment_schedule, container, false);
        scheduleList = (ListView) getActivity().findViewById(R.id.scheduleList);
        listItems = new ArrayList<>();
        adapter = new ScheduleAdapter<>(getActivity());
        updateListView();
        scheduleList.setAdapter(adapter);
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button deleteBt = (Button) getActivity().findViewById(btDelete);
                Button updateBt = (Button) getActivity().findViewById(R.id.btUpdate);
                deleteBt.setEnabled(true);
                updateBt.setEnabled(true);
            }
        });
        final Button btTime = (Button) res.findViewById(R.id.btTime);
        btTime.setText("00:00");
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetTime.class);
                startActivityForResult(intent, 0);
            }
        });

        final Spinner spinTemp = (Spinner) res.findViewById(spinner);
        List<String> tempList = new ArrayList<>();
        for (int i = 1; i<=30 ; i++)
        {
            tempList.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tempList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTemp.setAdapter(dataAdapter);


        Button btAdd = (Button) res.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cValues = new ContentValues();
                cValues.put(TEMPETURE, spinTemp.getSelectedItem().toString());
                cValues.put(SCHEDULE_TIME, (String)btTime.getText());
                mydb.insert(ScheduleDatabaseHelper.TABLE_NAME, "NullPlaceHolder", cValues);
                listItems.add(new TempSchedule(spinTemp.getSelectedItem().toString(), (String)btTime.getText()));
                adapter.notifyDataSetChanged();

            }
        });
        final Button btDelete = (Button) res.findViewById(R.id.btDelete);
        final Button btUpdate = (Button) res.findViewById(R.id.btUpdate);
        btDelete.setEnabled(false);
        btUpdate.setEnabled(false);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mydb.delete(ScheduleDatabaseHelper.TABLE_NAME, "id = ?", new String[]{String.valueOf(adapter.getItemId(ps))} );
                listItems.remove(ps);
                adapter.notifyDataSetChanged();
                btDelete.setEnabled(false);
                btUpdate.setEnabled(false);
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cValues = new ContentValues();
                cValues.put(TEMPETURE, spinTemp.getSelectedItem().toString());
                cValues.put(SCHEDULE_TIME, (String)btTime.getText());

                mydb.update(ScheduleDatabaseHelper.TABLE_NAME,  cValues, "id = ?", new String[]{String.valueOf(adapter.getItemId(ps))});
                listItems.remove(ps);
                listItems.add(ps,new TempSchedule(spinTemp.getSelectedItem().toString(), (String)btTime.getText()));
                adapter.notifyDataSetChanged();
                btDelete.setEnabled(false);
                btUpdate.setEnabled(false);
            }
        });
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ps = position;
                btDelete.setEnabled(true);
                btUpdate.setEnabled(true);
            }
        });

        return res;
    }
    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if (responseCode == Activity.RESULT_OK){
            Button btTime = (Button) getActivity().findViewById(R.id.btTime);
            btTime.setText(data.getStringExtra("resultStr"));
        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateListView() {
        listItems.clear();
        ScheduleDatabaseHelper dbHelper = new ScheduleDatabaseHelper(getActivity());
        mydb = dbHelper.getWritableDatabase();
        String[] columns = {KEY_ID, TEMPETURE, SCHEDULE_TIME};
        Cursor myCursor = mydb.query(false, TABLE_NAME, null, null, null, null, null, null, null);
        if(myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                TempSchedule ts = new TempSchedule(
                        myCursor.getString(myCursor.getColumnIndex(TEMPETURE)),
                        myCursor.getString(myCursor.getColumnIndex(SCHEDULE_TIME)));
                listItems.add(ts);
                myCursor.moveToNext();
            }
        }
        adapter.notifyDataSetChanged();

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

    private class ScheduleAdapter<TempSchedule > extends ArrayAdapter<TempSchedule > {
        public ScheduleAdapter(Context ctx) {
            super(ctx, 0);
        }

        public long getItemId(int position){
            ScheduleDatabaseHelper dbHelper = new ScheduleDatabaseHelper(this.getContext());
            mydb = dbHelper.getReadableDatabase();
            Cursor cursor = mydb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(ScheduleDatabaseHelper.KEY_ID));
        }

        public int getCount(){
            return listItems.size();
        }

        public TempSchedule getItem(int position){
            return (TempSchedule)listItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.schedule_item, null);

            TextView tempeture = (TextView)result.findViewById(R.id.temperature_text);
            TextView timeText = (TextView)result.findViewById(R.id.time_text);
            tempeture.setText(listItems.get(position).temperature); // get the string at position
            timeText.setText(listItems.get(position).time);
            return result;
        }
    }
}

