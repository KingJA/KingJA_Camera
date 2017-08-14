package kingja.com.kingja_camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by KingJA on 2016/1/24.
 */
public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback{
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private ImageButton shutter;
    private ImageView iv_preview;
    private TextView tv_date;
    private File imageFile;
    private Bitmap newBitmap;
    private Camera.PictureCallback pictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            imageFile = CameraUtil.createImageFile();
            try {

                FileOutputStream fis = new FileOutputStream(imageFile);
                fis.write(data);
                fis.close();
                Bitmap oldBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                iv_preview.setImageBitmap(oldBitmap);
//                CameraUtil.bitmap2Location(newBitmap,imageFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_camera);
        mPreview = (SurfaceView) findViewById(R.id.preview);
        shutter = (ImageButton) findViewById(R.id.shutter);
        tv_date = (TextView) findViewById(R.id.tv_date);
        iv_preview = (ImageView) findViewById(R.id.iv_preview);
        tv_date.setText(CameraUtil.getDateString());
        mHolder=mPreview.getHolder();
        mHolder.addCallback(this);
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
    }

    /**
     * 快门
     * @param view
     */
    public void shutter(View view) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);

//        parameters.setPreviewSize(1280,720);
        parameters.setPictureSize(1280,720);
        parameters.setRotation(90);
        parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);

//        mCamera.autoFocus(new Camera.AutoFocusCallback() {
//            @Override
//            public void onAutoFocus(boolean success, Camera takePhotoAndrSave) {
//                if (success) {
//                    mCamera.takePicture(null,null,pictureCallback);
//                }
//            }
//        });
        mCamera.setParameters(parameters);
        mCamera.takePicture(null,null,pictureCallback);
    }

    /**
     * 保存
     * @param v
     */
    public void save(View v) {
        Intent intent = new Intent(this, displayActivity.class);
        imageFile.getAbsoluteFile();
        intent.putExtra("PHOTO",imageFile.getAbsolutePath());
        startActivity(intent);
    }

    public Camera getCamera(){
        Camera camera =  Camera.open();

        return camera;
    }
    public void releaseCameraAndPreview(){
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }

    }

    public void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera==null){
            mCamera=getCamera();
            if (mHolder != null) {
                setStartPreview(mCamera,mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCameraAndPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera,mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCameraAndPreview();
    }
}
