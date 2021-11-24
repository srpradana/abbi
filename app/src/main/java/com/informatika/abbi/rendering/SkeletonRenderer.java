package com.informatika.abbi.rendering;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.informatika.abbi.imaging.IFrame;

public class SkeletonRenderer implements IRenderer {

    private Scalar skeletonColour;

    public SkeletonRenderer() {
       setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

//         display skeleton
        Imgproc.drawContours(inputFrame.getRGBA(),
                inputFrame.getSkeletonContours(),
                -1,
                skeletonColour,
                1);

    }

    private void setDefaultColour(){
        skeletonColour = new Scalar(0,0,255,255);
    }

}
