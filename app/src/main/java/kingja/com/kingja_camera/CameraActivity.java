package kingja.com.kingja_camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Description：TODO
 * Create Time：2017/8/1422:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        setContentView(R.layout.activity_camera);

        CameraFragment cameraFragment = new CameraFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_camera, cameraFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState: " );
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(TAG, "onRestoreInstanceState: " );
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart: " );
        super.onRestart();
    }
}
