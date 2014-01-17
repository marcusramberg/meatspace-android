package com.romainpiel.lib.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraView;
import com.commonsware.cwac.camera.SimpleCameraHost;

/**
 * meatspace-android
 * romainpiel
 * 16/01/2014
 */
public class CameraPreviewFragment extends CameraFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CameraView cameraView = new CameraView(getActivity());
        cameraView.setHost(new CameraHost(getActivity()));
        cameraView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER_VERTICAL
        ));

        setCameraView(cameraView);

        return cameraView;
    }

    private class CameraHost extends SimpleCameraHost {

        public CameraHost(Context context) {
            super(context);
        }
    }
}
