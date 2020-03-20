package dev.htm.ncovid.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ViewUtil {

    private static final String LOG_TAG = "SoftKeyboardHelper";

    public static void hideKeyboardOnTouch(Activity activity, View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardOnTouch(activity, innerView);
            }
        }
    }

    /**
     * getInputMethodManager
     */
    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
    }

    /**
     * Hide the soft keyboard
     */
    public static void hide(Context context, View view) {

        if (context == null) {
            Log.w(LOG_TAG, "Cannot hide soft keyboard with null Context");
        }
        else if (view == null) {
            Log.w(LOG_TAG, "Cannot hide soft keyboard with null View");
        }
        else {
            InputMethodManager imm = getInputMethodManager(context);
            IBinder token = view.getApplicationWindowToken();
            imm.hideSoftInputFromWindow(token, 0);
        }
    }

    /**
     * Show the soft keyboard
     */
    public static void show(Context context) {

        if (context == null) {
            Log.w(LOG_TAG, "Cannot show soft keyboard with null Context");
        }
        else {
            InputMethodManager imm = getInputMethodManager(context);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }


}
