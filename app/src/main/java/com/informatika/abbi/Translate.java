package com.informatika.abbi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.informatika.abbi.imaging.IFrame;
import com.informatika.abbi.processing.DetectionMethod;
import com.informatika.abbi.processing.DownSamplingFrameProcessor;
import com.informatika.abbi.processing.IFrameProcessor;
import com.informatika.abbi.processing.MainFrameProcessor;
import com.informatika.abbi.processing.ResizingFrameProcessor;
import com.informatika.abbi.processing.SizeOperation;
import com.informatika.abbi.processing.postprocessing.IFramePostProcessor;
import com.informatika.abbi.processing.postprocessing.OutputFramePostProcessor;
import com.informatika.abbi.processing.postprocessing.UpScalingFramePostProcessor;
import com.informatika.abbi.processing.preprocessing.CameraFrameAdapter;
import com.informatika.abbi.processing.preprocessing.IFramePreProcessor;
import com.informatika.abbi.processing.preprocessing.InputFramePreProcessor;
import com.informatika.abbi.rendering.IRenderer;
import com.informatika.abbi.rendering.MainRenderer;
import com.informatika.abbi.svm.FrameClassifier;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Translate extends Activity implements CvCameraViewListener2 {

    private CameraBridgeViewBase mOpenCvCameraView;

    private IFramePreProcessor preProcessor;
    private IFramePostProcessor postProcessor;
    private IFrame preProcessedFrame, processedFrame, postProcessedFrame, classifiedFrame;

    private IFrameProcessor mainFrameProcessor, frameClassifier;

    private IRenderer mainRenderer;

    private Button btnAdd, btnClear, btnBack;
    private TextView txtView;

    private String currentLetter, previousLetter, modLetter;

    private DetectionMethod detectionMethod;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.enableFpsMeter();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view parameters
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set content view
        setContentView(R.layout.mlpredict);

        // Camera config
        mOpenCvCameraView = findViewById(R.id.mlpredict);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }

        btnAdd = (Button) findViewById(R.id.button_add);
        btnClear = (Button) findViewById(R.id.button_clear);
        btnBack = (Button) findViewById(R.id.button_back);
        txtView = (TextView) findViewById(R.id.textView);


        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!modLetter.equals("?"))
                    txtView.append(modLetter);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtView.setText("");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtView.setText(txtView.getText().toString().substring(0, txtView.getText().toString().length() - 1));
            }
        });


    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        // Generate Frame from input frame and downsample
        preProcessedFrame = preProcessor.preProcess(inputFrame);

        // Generate useful information from frame
        processedFrame = mainFrameProcessor.process(preProcessedFrame);

        // Post processing of processed frame and upsampling
        postProcessedFrame = postProcessor.postProcess(processedFrame);

        // Actual frame classification
        classifiedFrame = frameClassifier.process(postProcessedFrame);

        previousLetter = currentLetter;
        currentLetter = getDisplayableLetter(classifiedFrame.getLetterClass().toString());

        setLetterIfChanged();

        // Display anything required
//        mainRenderer.display(postProcessedFrame);

        // Return processed Mat
        return classifiedFrame.getRGBA();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }



    public void onCameraViewStarted(int width, int height) {

        setProcessors(DetectionMethod.CANNY_EDGES);

        File xmlFile = initialiseXMLTrainingData();

        frameClassifier = new FrameClassifier(xmlFile);

    }

    public void onCameraViewStopped() {

    }

    private void setProcessors(DetectionMethod method){
        // Set detection method
        detectionMethod = method;

        // Create renderers
        mainRenderer = new MainRenderer(detectionMethod);

        // Pre processors
        preProcessor = new InputFramePreProcessor(
                new CameraFrameAdapter(
                        new DownSamplingFrameProcessor(),
                        new ResizingFrameProcessor(SizeOperation.UP)
                )
        );

        // Frame Processors
        mainFrameProcessor = new MainFrameProcessor(detectionMethod);

        // Post processors
        postProcessor = new OutputFramePostProcessor(
                new UpScalingFramePostProcessor(),
                new ResizingFrameProcessor(SizeOperation.UP)
        );
    }

    private void setLetterIfChanged(){
        if (!currentLetter.equals(previousLetter)){
            modLetter = currentLetter;
            if (modLetter.equals("NONE"))
                modLetter = "?";
            if (modLetter.equals("SPACE"))
                modLetter = " ";
            setPossibleLetter(modLetter);
        }
    }

    private void setPossibleLetter(final String currentLetterForMod){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (txtView.getText().toString().length() > 0)
                    txtView.setText(txtView.getText().toString().substring(0, txtView.getText().toString().length() - 1));

                txtView.append(currentLetterForMod);

            }
        });
    }

    private String getDisplayableLetter(String letter){

        switch (letter){
            case "NONE":
                return "?";
            case "SPACE":
                return " ";
            default:
                return letter;
        }

    }

    private File initialiseXMLTrainingData(){

        try {
            InputStream is = getResources().openRawResource(R.raw.trained);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir,"training.xml");

            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            is.close();
            os.close();

            return mCascadeFile;

        } catch (Exception e) {
            e.printStackTrace();
            return new File("");
        }

    }

}