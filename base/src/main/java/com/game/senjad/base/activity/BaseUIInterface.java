package com.game.senjad.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by RGB on 6/1/2018.
 */

interface BaseUIInterface {

    /**
     * display toast message
     *
     * @param message to be show
     */
    void displayToast(@NonNull String message);

    void displayToast(@StringRes int message);

    /**
     * display SnackBar message
     * snackBar button clickListener calls method with name 'onSnackBarButtonClick'
     *
     * @param message to be show
     * @param btnText text of button in snackbar
     */
    void displaySnackBar(@NonNull String message, String btnText);

    void displaySnackBar(@StringRes int message, @StringRes int btnText);

    /**
     * display snackbar
     *
     * @param message to be show
     */
    void displaySnackBar(@NonNull String message);

    void displaySnackBar(@StringRes int message);

    /**
     * displays a dialog for showing message with a simple 'OK' button
     * 'OK' clickListener calls method with name 'onOkClick'
     *
     * @param message is description of the dialog
     */
    void displayMessageDialog(String message);

    void displayMessageDialog(@StringRes int title, @StringRes int message);

    /**
     * displays a dialog with two buttons 'yes' and 'no'
     * 'yes' clickListener calls method with name 'onYesClick'
     * 'no' clickListener calls method with name 'onNoClick'
     *
     * @param title       is title of dialog
     * @param description is description if the dialog
     */
    void displayYesNoDialog(String title, String description, String TAG);

    void displayYesNoDialog(@StringRes int title, @StringRes int description, String TAG);

    void displayYesNoDialog(String title, String description);

    void displayYesNoDialog(@StringRes int title, @StringRes int description);
    void finishActivity();
    void startActivity(Class c);
    void startActivity(Class c, Bundle b);

    String getDeviceId();
    void dismissDialog();

    void onSnackBarButtonClick();

    void onCancelClick(String tag);

    void onNoClick(String tag);

    void onYesClick(String tag);
    void onProgressCancel();
    void setStatusBarColor(int color);
    void dismissMessageDialog();
}
