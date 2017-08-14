package kingja.com.kingja_camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Description：TODO
 * Create Time：2017/8/1422:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CameraFragment extends Fragment {

    private static final String TAG = "CameraFragment";
    private View rootView;
    private ImageView iv;
    private static final int RES_CAMERA = 1;
    private static final int RES_CUSTOM_CAMERA = 2;
    private static final int RES_COMMON_CAMERA = 3;
    private File imageFile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv = (ImageView) rootView.findViewById(R.id.iv);
        Button  btn_takePhoto = (Button) rootView.findViewById(R.id.btn_takePhoto);
        Button  btn_takePhotoAndSave = (Button) rootView.findViewById(R.id.btn_takePhotoAndSave);
        Button  btn_takeCustomPhoto = (Button) rootView.findViewById(R.id.btn_takeCustomPhoto);
        btn_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });    btn_takePhotoAndSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoAndrSave();
            }
        });
        btn_takeCustomPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customcamera();
            }
        });
    }
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RES_COMMON_CAMERA);
    }
    public void customcamera() {
        Intent intent = new Intent(getActivity(), CustomCameraActivity.class);
        startActivityForResult(intent, RES_CUSTOM_CAMERA);
    }

    public void takePhotoAndrSave() {
        try {
            imageFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, RES_CAMERA);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RES_CAMERA:
                if (resultCode == RESULT_OK) {
                    scaleImg(iv, imageFile.getAbsolutePath());
                }
                break;
            case RES_COMMON_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    iv.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }

    private void scaleImg(ImageView imageView, String photoPaht) {
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPaht, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPaht, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState: " );
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewStateRestored: " );
        super.onViewStateRestored(savedInstanceState);
    }
}
