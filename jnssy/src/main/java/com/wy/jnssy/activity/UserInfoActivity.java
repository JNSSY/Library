package com.wy.jnssy.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.drawee.view.SimpleDraweeView;
import com.wy.jnssy.R;
import com.wy.jnssy.activity.BaseActivity;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
@SuppressLint("HandlerLeak")
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_user_icon;
    private PopupWindow popupWindow;
    private Uri imageUri;
    private static final int TAKE_PHONE = 1;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 3;
    private static final int CHOOSE_FORM_ALBUM = 2;
    //    private SimpleDraweeView sd_user_icon;
    private static final int SHOW_COMPRESSED_IMAGE = 0x0001;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_COMPRESSED_IMAGE:
                    File file = (File) msg.obj;
                    Uri uri = Uri.parse("file://" + file.getAbsolutePath());
//                    sd_user_icon.setImageURI(uri);
                    Intent intent = new Intent();
                    intent.putExtra("path", "file://"+uri);
                    setResult(RESULT_OK, intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        initView();
//        initData();
    }

    /*private void initData() {
        File icon_file = new File(getExternalCacheDir() + File.separator + "user_icon");
        if (icon_file.exists()) {
            Uri uri = Uri.parse("file://" + icon_file.getAbsolutePath());
            sd_user_icon.setImageURI(uri);
        } else {
            sd_user_icon.setImageResource(R.mipmap.ic_launcher);
        }
    }*/


    private void initView() {
        ll_user_icon = findViewById(R.id.ll_user_icon);
//        sd_user_icon = findViewById(R.id.sd_user_icon);


        ll_user_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_user_icon) {
            showChoosePop();
        }
    }

    private void showChoosePop() {
        View convertView = LayoutInflater.from(this).inflate(R.layout.layout_pop_choose_imgs, null);
        popupWindow = new PopupWindow(convertView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        setBackgroundAlpha(0.5f);
        popupWindow.showAtLocation(findViewById(R.id.ll_root), Gravity.BOTTOM, 0, 0);


        TextView tv_cancel = convertView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    setBackgroundAlpha(1.0f);
                }
            }
        });

        TextView tv_take_pic = convertView.findViewById(R.id.tv_take_pic);
        tv_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                popupWindow.dismiss();
            }
        });

        TextView tv_choose_image = convertView.findViewById(R.id.tv_choose_image);
        tv_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromAlbum();
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });


    }

    private void setBackgroundAlpha(float v) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = v;
        getWindow().setAttributes(lp);
    }

    private void takePhoto() {
        //创建一个File对象用于存储拍照后的照片
        File outputImage = new File(getExternalCacheDir(), "user_icon");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断Android版本是否是Android7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this, "com.wy.demo.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHONE);
    }

    private void chooseFromAlbum() {
        //权限检查
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            //打开相册
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FORM_ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHONE && resultCode == RESULT_OK) {
            //相机拍照回调
            try {
                final File file = new File(getExternalCacheDir() + File.separator + "user_icon");
                compressImage(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_OK && requestCode == CHOOSE_FORM_ALBUM) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //Android4.4及以上版本
                handleImageOnKitkat(data);
            } else {
                //Android4.4以下版本
                handleImageBeforeKitkat(data);
            }
        }
    }


    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri ，直接获取图片的路径即可
            imagePath = uri.getPath();
        }
        showImage(imagePath);

    }

    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        showImage(imagePath);
    }


    //获取照片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和 selection获取真实图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void compressImage(final File file) {
        Log.e("wy", "压缩前图片大小：" + file.length() / 1024 + " k");
        Luban.with(this)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(getExternalCacheDir() + File.separator)
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return "user_icon";
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        showProgress("请稍后...");
                    }

                    @Override
                    public void onSuccess(File file) {
                        dissmissProgress();
                        Log.e("wy", "压缩后的图片大小：" + file.length() / 1024 + " k");
                        Message msg = Message.obtain();
                        msg.obj = file;
                        msg.what = SHOW_COMPRESSED_IMAGE;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgress();
                        e.printStackTrace();
                    }
                }).launch();
    }

    private void showImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            File file = new File(imagePath);
            compressImage(file);
        } else {
            Toast.makeText(this, "错误的图片", Toast.LENGTH_SHORT).show();
        }
    }


}
