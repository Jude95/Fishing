package com.jude.fishing.module.social;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.rong.imkit.tools.PhotoFragment;

@RequiresPresenter(PhotoPresenter.class)
public class PhotoActivity extends BeamBaseActivity<PhotoPresenter> {
    PhotoFragment mPhotoFragment;
    Uri mUri;
    Uri mDownloaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_activity_photo);
        initView();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.save) {
            if(mDownloaded == null) {
                Toast.makeText(this, "正在下载，请稍后保存！", Toast.LENGTH_SHORT).show();
                return true;
            }

            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path, "RongCloud/Image");
            if(!dir.exists())
                dir.mkdirs();

            File from = new File(mDownloaded.getPath());
            String name = from.getName() + ".jpg";
            File to = new File(dir.getAbsolutePath(), name);
            if(to.exists()) {
                Toast.makeText(this, "文件保存成功！", Toast.LENGTH_SHORT).show();
                return true;
            }
            copyFile(from.getAbsolutePath(), to.getAbsolutePath());
        }
        return super.onOptionsItemSelected(item);
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "文件保存出错！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            Toast.makeText(this, "文件保存成功！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mUri != null && mUri.getScheme().startsWith("http")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_social_photo_save, menu);
            return super.onCreateOptionsMenu(menu);
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    protected void initView() {
        mPhotoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_photo);
    }

    protected void initData() {
        Uri uri = getIntent().getParcelableExtra("photo");
        Uri thumbUri = getIntent().getParcelableExtra("thumbnail");

        mUri = uri;
        if (uri != null)
            mPhotoFragment.initPhoto(uri, thumbUri, new PhotoFragment.PhotoDownloadListener() {
                @Override
                public void onDownloaded(Uri uri) {
                    mDownloaded = uri;
                }

                @Override
                public void onDownloadError() {

                }
            });
    }
}