package com.example.android.sunshine.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created with Android Studio
 * @author vspreys
 * Date: 26/10/15
 * Project: Sunshine-Version-2
 * Contact by: vlad@spreys.com
 */
public class LocationEditTextPreference extends EditTextPreference implements TextWatcher {
    static final private int DEFAULT_MINIMUM_LOCATION_LENGTH = 2;
    private int minLength;

    public LocationEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LocationEditTextPreference,
                0, 0);

        try {
            minLength = a.getInteger(R.styleable.LocationEditTextPreference_minLength,
                    DEFAULT_MINIMUM_LOCATION_LENGTH);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);

        //Add the text changed listener to enable/disable the positive button
        EditText editText = getEditText();
        editText.addTextChangedListener(this);

        //Enable/disable the positive button state right away
        if(getDialog() instanceof AlertDialog) {
            AlertDialog dialog = (AlertDialog)getDialog();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setEnabled(editText.getText().length() >= minLength);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //This method is intentionally left empty
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //This method is intentionally left empty
    }

    @Override
    public void afterTextChanged(Editable s) {
        Dialog d = getDialog();

        if(d instanceof AlertDialog) {
            AlertDialog dialog = (AlertDialog)d;
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(s.length() >= minLength);
        }
    }
}
