/* File name: AutoFragRadio.java
 * Author: Maga Lee, 040852763
 * Course: CST2335
 * Date: Apr 15, 2017
 * Professor: Eric Torunski
 * Purpose: This file is used to list items that are managed in this application.
 */

package com.example.marklee.group12_final_project;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * This class is the automobile main class which displays 4 items using ListView, including help dialog.
 * @author Maga Lee
 * @version 1.0
 */
public class AutoFragRadio extends Fragment {

    protected Context parent;
    protected View view;

    //Variables related to the database
    protected AutoDatabaseHelper autoDBHelper;
    protected SQLiteDatabase db;
    protected ContentValues newValues;
    protected int keyID;

    //Variables related to the radio control
    protected final double MAX_FREQ = 107.9;
    protected final double MIN_FREQ = 88.5;
    protected MathContext mc;
    protected TextView radioFrequency;
    protected Double curFreq = new Double(0.0);
    protected ImageButton freqUp;
    protected ImageButton freqDown;
    protected RadioButton addPreset;
    protected RadioButton removePreset;
    protected AlertDialog.Builder presetDialog;
    protected LayoutInflater presetInflater;
    protected View presetDialogView;
    protected Button addPreset1;
    protected Button addPreset2;
    protected Button addPreset3;
    protected Button addPreset4;
    protected Button addPreset5;
    protected Button addPreset6;
    protected double preset1Freg;
    protected double preset2Freg;
    protected double preset3Freg;
    protected double preset4Freg;
    protected double preset5Freg;
    protected double preset6Freg;
    protected ToggleButton toggleMute;
    protected SeekBar volumeBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle bun) {

        bun = getArguments();
        preset1Freg = bun.getDouble("preset1Freg");
        preset2Freg = bun.getDouble("preset2Freg");
        preset3Freg = bun.getDouble("preset3Freg");
        preset4Freg = bun.getDouble("preset4Freg");
        preset5Freg = bun.getDouble("preset5Freg");
        preset6Freg = bun.getDouble("preset6Freg");
        keyID = bun.getInt("keyID");

        autoDBHelper = new AutoDatabaseHelper(parent);
        db = autoDBHelper.getWritableDatabase();

        view = inflater.inflate(R.layout.radio_layout, null);

        radioFrequency = (TextView) view.findViewById(R.id.radioFrequency);
        freqDown = (ImageButton) view.findViewById(R.id.freqDown);
        freqUp = (ImageButton) view.findViewById(R.id.freqUp);
        //curFreq = preset1Freg==0.0?Double.parseDouble(radioFrequency.getText().toString()):preset1Freg;
        curFreq = Double.parseDouble(radioFrequency.getText().toString());
        radioFrequency.setText(Double.toString(curFreq));
        addPreset1 = (Button) view.findViewById(R.id.addPreset1);
        addPreset2 = (Button) view.findViewById(R.id.addPreset2);
        addPreset3 = (Button) view.findViewById(R.id.addPreset3);
        addPreset4 = (Button) view.findViewById(R.id.addPreset4);
        addPreset5 = (Button) view.findViewById(R.id.addPreset5);
        addPreset6 = (Button) view.findViewById(R.id.addPreset6);

        presetDialog = new AlertDialog.Builder(parent);
        presetInflater = LayoutInflater.from(parent);

        toggleMute = (ToggleButton) view.findViewById(R.id.toggleMute);
        volumeBar = (SeekBar) view.findViewById(R.id.volumeBar);

        freqUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (curFreq < MAX_FREQ) {
                curFreq += 0.1;
                if (Integer.toString(curFreq.intValue()).length() == 2)
                    mc = new MathContext(3); // 3 precision
                else
                    mc = new MathContext(4); // 4 precision

                curFreq = new BigDecimal(curFreq).round(mc).doubleValue();
                radioFrequency.setText(curFreq + "");
            } else {
                curFreq = MIN_FREQ;
                radioFrequency.setText(curFreq + "");
            }
            }
        });

        freqDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(curFreq > MIN_FREQ) {
                curFreq -= 0.1;
                if(Integer.toString(curFreq.intValue()).length() == 2)
                    mc = new MathContext(3); // 3 precision
                else
                    mc = new MathContext(4); // 4 precision

                curFreq = new BigDecimal(curFreq).round(mc).doubleValue();
                radioFrequency.setText(curFreq + "");
            } else {
                curFreq = MAX_FREQ;
                radioFrequency.setText(curFreq + "");
            }
            }
        });

        addPreset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preset1Freg != 0.0) presetChange(1);
            }
        });

        addPreset1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view1) {
                presetAddDelete(1);
                return false;
            }
        });

        addPreset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preset2Freg != 0.0) presetChange(2);
            }
        });

        addPreset2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view2) {
                presetAddDelete(2);
                return false;
            }
        });

        addPreset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preset3Freg != 0.0) presetChange(3);
            }
        });

        addPreset3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view2) {
                presetAddDelete(3);
                return false;
            }
        });

        addPreset4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preset4Freg != 0.0) presetChange(4);
            }
        });

        addPreset4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view2) {
                presetAddDelete(4);
                return false;
            }
        });

        addPreset5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preset5Freg != 0.0) presetChange(5);
            }
        });

        addPreset5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view2) {
                presetAddDelete(5);
                return false;
            }
        });

        addPreset6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preset6Freg != 0.0) presetChange(6);
            }
        });

        addPreset6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view2) {
                presetAddDelete(6);
                return false;
            }
        });

        volumeBar.setProgress(0);
        toggleMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(toggleMute.isChecked()){
                toggleMute.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.ic_speaker)
                );
                volumeBar.setProgress(1);
            }else{
                toggleMute.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.ic_mute)
                );
                volumeBar.setProgress(0);
            }
            }
        });

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if(progressChangedValue == 0) {
                    toggleMute.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.ic_mute)
                    );
                    toggleMute.setChecked(false);
                } else {
                    toggleMute.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.ic_speaker)
                    );
                    toggleMute.setChecked(true);
                }
                Toast.makeText(parent, "Volume " + progressChangedValue, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void presetChange(int presetNum) {
        if(presetNum == 1) curFreq = preset1Freg;
        else if(presetNum == 2) curFreq = preset2Freg;
        else if(presetNum == 3) curFreq = preset3Freg;
        else if(presetNum == 4) curFreq = preset4Freg;
        else if(presetNum == 5) curFreq = preset5Freg;
        else if(presetNum == 6) curFreq = preset6Freg;
        radioFrequency.setText(curFreq + "");
    }

    public void presetAddDelete(final int presetNum) {
        presetDialogView = presetInflater.inflate(R.layout.dialog_preset, null);
        addPreset = (RadioButton) presetDialogView.findViewById(R.id.addPreset);
        removePreset = (RadioButton) presetDialogView.findViewById(R.id.removePreset);
        addPreset.setText(getResources().getString(R.string.radio_addPreset)+" "+presetNum);
        removePreset.setText(getResources().getString(R.string.radio_removePreset)+" "+presetNum);
        presetDialog.setView(presetDialogView).setTitle(getResources().getString(R.string.dialog_titlePreset)+" "+presetNum)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(addPreset.isChecked()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                            builder.setTitle(getResources().getString(R.string.dialog_addPreset)+" "+presetNum+"?");
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    newValues = new ContentValues();
                                    if(presetNum == 1) {
                                        preset1Freg = curFreq;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET1, curFreq);
                                    } else if(presetNum == 2) {
                                        preset2Freg = curFreq;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET2, curFreq);
                                    } else if(presetNum == 3) {
                                        preset3Freg = curFreq;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET3, curFreq);
                                    } else if(presetNum == 4) {
                                        preset4Freg = curFreq;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET4, curFreq);
                                    } else if(presetNum == 5) {
                                        preset5Freg = curFreq;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET5, curFreq);
                                    } else if(presetNum == 6) {
                                        preset6Freg = curFreq;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET6, curFreq);
                                    }
                                    db.update(autoDBHelper.tableName, newValues, autoDBHelper.KEY_ID + "=?", new String[] {String.valueOf(keyID)});
                                    Snackbar.make(view, getResources().getString(R.string.msg_preset)+" "+presetNum+" "+
                                                        getResources().getString(R.string.msg_setConfirm), Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {  }
                            });
                            builder.create().show();
                        } else if(removePreset.isChecked()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                            builder.setTitle(getResources().getString(R.string.dialog_removePreset)+" "+presetNum+"?");
                            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    newValues = new ContentValues();
                                    if(presetNum == 1) {
                                        preset1Freg = 0.0;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET1, 0.0);
                                    } else if(presetNum == 2) {
                                        preset2Freg = 0.0;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET2, 0.0);
                                    } else if(presetNum == 3) {
                                        preset3Freg = 0.0;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET3, 0.0);
                                    } else if(presetNum == 4) {
                                        preset4Freg = 0.0;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET4, 0.0);
                                    } else if(presetNum == 5) {
                                        preset5Freg = 0.0;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET5, 0.0);
                                    } else if(presetNum == 6) {
                                        preset6Freg = 0.0;
                                        newValues.put(autoDBHelper.COL_RADIO_PRESET6, 0.0);
                                    }
                                    db.update(autoDBHelper.tableName, newValues, autoDBHelper.KEY_ID + "=?", new String[] {String.valueOf(keyID)});
                                    Snackbar.make(view, getResources().getString(R.string.msg_preset)+" "+presetNum+" "+
                                                        getResources().getString(R.string.msg_removeConfirm), Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) { }
                            });
                            builder.create().show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {  }
                });
        presetDialog.create().show();
    }

}