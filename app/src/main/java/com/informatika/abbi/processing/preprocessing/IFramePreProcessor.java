package com.informatika.abbi.processing.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import com.informatika.abbi.imaging.IFrame;

public interface IFramePreProcessor {

    IFrame preProcess(CvCameraViewFrame inputFrame);

}
