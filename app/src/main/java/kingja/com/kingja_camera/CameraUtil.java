package kingja.com.kingja_camera;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KingJA on 2016/1/24.
 */
public class CameraUtil {
    public static String getDateString() {
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static File createImageFile()  {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void bitmap2Location(Bitmap bitmap, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FileOutputStream fis = null;
        try {
            fis = new FileOutputStream(path);
            fis.write(data);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void byte2Location(byte[] data,String path) {
        FileOutputStream fis=null;
        try {
            fis = new FileOutputStream(path);
            fis.write(data);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
