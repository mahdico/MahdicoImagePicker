package ir.rubin.mahdicoimagepicker.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.rubin.mahdicoimagepicker.BuildConfig;
import ir.rubin.mahdicoimagepicker.R;
import ir.rubin.mahdicoimagepicker.listeners.OnMethodSelectedListener;
import ir.rubin.mahdicoimagepicker.listeners.onImageResizedListener;
import ir.rubin.mahdicoimagepicker.util.AskForMethod;
import ir.rubin.mahdicoimagepicker.util.GetFileName;
import ir.rubin.mahdicoimagepicker.util.GetImageUri;
import ir.rubin.mahdicoimagepicker.util.GetPathFromUri;
import ir.rubin.mahdicoimagepicker.util.ResizeImage;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnMethodSelectedListener,onImageResizedListener {

    private int REQUEST_CAMERA = 87;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI(){
        setContentView(R.layout.activity_main);
        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                AskForMethod.doAsk(this);
                break;
        }
    }

    @Override
    public void onCam() {
        int permissionCheck = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            permissionCheck = ContextCompat.checkSelfPermission(this, CAMERA);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
            } else {
                String imageFileName = GetFileName.getNewImageFileName();
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                GetFileName.setTempImagePath(imageFileName);
                GetFileName.mImageCaptureUri = FileProvider.getUriForFile(MainActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        GetFileName.getTempImageFile(this,
                                imageFileName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, GetFileName.mImageCaptureUri);
                startActivityForResult(intent, 111);
            }
        } else {
            String imageFileName = GetFileName.getNewImageFileName();
            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            GetFileName.setTempImagePath(imageFileName);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
                GetFileName.mImageCaptureUri = Uri.fromFile(GetFileName.getTempImageFile(this,
                        imageFileName));
            }else {
                GetFileName.mImageCaptureUri = FileProvider.getUriForFile(MainActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        GetFileName.getTempImageFile(this,
                                imageFileName));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, GetFileName.mImageCaptureUri);
            startActivityForResult(intent, 111);
        }
    }

    @Override
    public void onGallery() {
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = this.getPackageManager();
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        Intent chooserIntent = Intent.createChooser(mainIntent, "Choose the application");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        startActivityForResult(chooserIntent, 222);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            switch (requestCode) {
                case 222:
                    ResizeImage.doResize(BitmapFactory.decodeFile(GetPathFromUri.getRealPathFromURI(data.getData())), this);
                    break;
                case 111:
                    ResizeImage.doResize(BitmapFactory.decodeFile(GetFileName.tempImageFolder + GetFileName.mTempImagePath), this);
                    break;
            }
        }    }

    @Override
    public void onImageResized(String path) {
        Picasso.with(this).load(GetImageUri.doGet(path)).into(((ImageView)findViewById(R.id.img_contentmain_selectedimage)));
    }
}
