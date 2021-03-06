package com.romainpiel.meatspace.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.romainpiel.Constants;
import com.romainpiel.lib.bus.BusManager;
import com.romainpiel.lib.bus.MuteEvent;
import com.romainpiel.lib.bus.UIEvent;
import com.romainpiel.lib.ui.fragment.ChatFragment;
import com.romainpiel.lib.ui.fragment.SettingsFragment;
import com.romainpiel.meatspace.BuildConfig;
import com.romainpiel.meatspace.R;
import com.romainpiel.meatspace.service.ChatService;

/**
 * Meatspace
 * User: romainpiel
 * Date: 01/11/2013
 * Time: 16:54
 */
public class MainActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!BuildConfig.DEBUG) {
            BugSenseHandler.initAndStartSession(this, getString(R.string.key_bugsense));
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
        ChatService.start(this);
        BusManager.get().getUiBus().post(UIEvent.FOREGROUD);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
        BusManager.get().getUiBus().post(UIEvent.BACKGROUND);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.menu_main_switch_camera);
        if (item != null) {
            item.setVisible(Camera.getNumberOfCameras() > 1);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_switch_camera:
                ChatFragment fragment = (ChatFragment) getFragmentManager().findFragmentById(R.id.main_fragment);
                if (fragment != null) {
                    fragment.switchCamera();
                }
                break;
            case R.id.menu_main_disconnect:
                sendBroadcast(new Intent(Constants.FILTER_CHAT_CLOSE));
                break;
            case R.id.menu_main_unmute_all:
                BusManager.get().getChatBus().post(new MuteEvent(false, null));
                break;
            case R.id.menu_main_settings:
                showSettings();
                break;
            case R.id.menu_main_about:
                showAboutDialog();
                break;
        }


        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * show about dialog
     */
    private void showAboutDialog() {
        ViewGroup view = (ViewGroup) View.inflate(this, R.layout.dialog_about, null);

        TextView versionTv = (TextView) view.findViewById(R.id.dialog_about_version);
        versionTv.setText(String.format(getString(R.string.dialog_about_version),
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));

        TextView messageTv = (TextView) view.findViewById(R.id.dialog_about_message);
        messageTv.setText(Html.fromHtml(getString(R.string.dialog_about_content)));
        messageTv.setMovementMethod(LinkMovementMethod.getInstance());

        Dialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.Meatspace_Dialog_WindowAnimation;
        dialog.show();
    }

    /**
     * show settings dialog
     */
    private void showSettings() {
        FragmentManager fm = getFragmentManager();
        new SettingsFragment().show(fm, null);
    }
}
