package com.mozre.find.module.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.app.BaseActivity;
import com.mozre.find.domain.User;
import com.mozre.find.module.middle.NewMessage;
import com.mozre.find.module.titlebar.SettingsActivity;
import com.mozre.find.presenter.PostDataDao;
import com.mozre.find.presenter.UpDownLoadIcon;
import com.mozre.find.util.FinderSQLiteOpenHelper;
import com.mozre.find.util.HttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeView {
    public static final int GET_IMAGE_FORM_ALBUM = 1;
    private static final int NEW_MESSAGE_REQUEST_CODE = 2;
    private static final String TAG = "HomeActivity";
    private FragmentTabHost mFragementTabHost;
    private FloatingActionButton mFloationActionButtonNewMessage;
    private SimpleDraweeView mSimpleDraweeViewIcon;
    private TextView mTextViewMail;
    private TextView mTextViewUsername;
    private Toolbar toolbar;
    private ActionBar mActionBar;
    //    private
    private Dialog mDialog;
    private Integer[] mHomeIcon = new Integer[]{
            R.drawable.home_article,
            R.drawable.home_film,
            R.drawable.home_music,
            R.drawable.home_hot,
            R.drawable.home_person

    };
    private String[] mHomeTheme = new String[]{
            "文章",
            "电影",
            "音乐",
            "热门",
            "我"
    };
    private Class[] fragmentArray = new Class[]{
            ArticleFragment.class,
            FilmFragment.class,
            MusicFragment.class,
            HotFragment.class,
            PersonHomeFragement.class
    };

    private void init() {
        mDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否退出？")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("文章");
        init();
        PostDataDao dao = new PostDataDao(getBaseContext());
        try {
            dao.initFlags();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        SharedPreferences preferences = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
//        Log.d("kkk", "onCreate: token = " + preferences.getString("token", "沒有————————————————————"));
//        mSimpleDraweeViewIcon = (SimpleDraweeView) findViewById(R.id.nav_header_home_drawee);
//        mTextViewMail = (TextView) findViewById(R.id.nav_header_home_mail);
        Log.d(TAG, "onCreate: " + mSimpleDraweeViewIcon + " " + mTextViewMail);
        mFloationActionButtonNewMessage = (FloatingActionButton) findViewById(R.id.app_bar_home_fab);
        mFragementTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//        mSwipeRefreshLayout = findViewById(R.id.)
        mFragementTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < fragmentArray.length; ++i) {
            mFragementTabHost.addTab(mFragementTabHost.newTabSpec(mHomeTheme[i]).setIndicator(getItemView(i)), fragmentArray[i], null);
        }
        mFragementTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                mActionBar.setTitle(s);
                switch (s) {
                    case "我":
                    case "热门":
                        mFloationActionButtonNewMessage.setVisibility(View.INVISIBLE);
                        break;
                    case "文章":
                    case "电影":
                    case "音乐":
                        mFloationActionButtonNewMessage.setVisibility(View.VISIBLE);
                        break;
                }
                Log.d(TAG, "onTabChanged: " + s);
            }
        });

        mFloationActionButtonNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = mFragementTabHost.getCurrentTabTag();
                Intent intent = new Intent(getBaseContext(), NewMessage.class);
//                Log.d(TAG, "onClick: str = " + str);
                intent.putExtra("tab", str);
                startActivity(intent);
//                startActivityForResult(intent, NEW_MESSAGE_REQUEST_CODE);
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mSimpleDraweeViewIcon = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_home_drawee);
        mTextViewMail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_home_mail);
        mTextViewUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_home_username);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private View getItemView(int i) {
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.tab_host_item, null);
        ImageView mIcon = (ImageView) view.findViewById(R.id.tab_host_image);
        TextView mText = (TextView) view.findViewById(R.id.tab_host_text);
        mIcon.setImageResource(mHomeIcon[i]);
        mText.setText(mHomeTheme[i]);
        return view;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = new User();
        String path = HttpUtils.getCurrentURI(user.getUserIconAddress());
        Log.d(TAG, "onStart: path = " + path + " " + mSimpleDraweeViewIcon);
        if (path != null && path.length() > 0) {
            mSimpleDraweeViewIcon.setImageURI(path);

        }
        String mail = user.getMail();
        Log.d(TAG, "onStart: mail = " + mail + " " + mTextViewMail);
        if (mail != null && mail.length() > 0) {
            mTextViewMail.setText(mail);
        }
        String username = user.getUserName();
        if (username != null && username.length() > 0) {
            mTextViewUsername.setText(username);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "startActivityForResult: requestCode = " + requestCode);
        Log.d(TAG, "onActivityResult: resultCode = " + resultCode);
        Log.d(TAG, "onActivityResult:++++++++ " + data.getData());
        if (data.getData() instanceof Uri && resultCode == RESULT_OK) {
//            File file = getBaseContext().getCacheDir();
//            File tmpFileIcon = new File(file, "tmp");
//            Log.d(TAG, "onActivityResult: tmpsjdi=" + tmpFileIcon.getAbsolutePath());
//            if (!tmpFileIcon.exists()) {
//                try {
//                    tmpFileIcon.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            Uri uri = Uri.parse(tmpFileIcon.getAbsolutePath());
            Uri regionUri = data.getData();
            Intent intent = new Intent();

            intent.setAction("com.android.camera.action.CROP");
            intent.setDataAndType(regionUri, "image/*");// mUri是已经选择的图片Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);// 裁剪框比例
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 150);// 输出图片大小
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 2);
//            Log.d(TAG, "onActivityResult: jjjj" + regionUri);
//            UCrop.of(regionUri, uri)
//                    .withAspectRatio(16, 9)
//                    .withMaxResultSize(1000, 1000)
//                    .start(this);
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bmap = data.getParcelableExtra("data");
            File file = getBaseContext().getCacheDir();
            File tmpFileIcon = new File(file, "tmp");
            if (!tmpFileIcon.exists()) {
                try {
                    tmpFileIcon.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                OutputStream out = new FileOutputStream(tmpFileIcon);

//                tmpFileIcon.get
                bmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
                EventBus.getDefault().post(tmpFileIcon);
                UpDownLoadIcon uploadIcon = new UpDownLoadIcon(this);
                uploadIcon.postIconToServer(tmpFileIcon.getAbsolutePath(), "mozre");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "onActivityResult: r = " + bmap.toString());
        }

//        if (requestCode == NEW_MESSAGE_REQUEST_CODE && resultCode == RESULT_OK) {
//            Toast.makeText(HomeActivity.this, "发表成功！", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "onActivityResult: heeeeeeeeeeeeeeee");
//        }

//        Log.d(TAG, "onActivityResult:  i == " + UCrop.REQUEST_CROP + " " + UCrop.RESULT_ERROR);
//        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
//            Uri resultUri = UCrop.getOutput(data);
//            String resultPath = resultUri.getPath();
//            Log.d(TAG, "onActivityResult: resultPath = " + resultPath);
//        } else if (resultCode == UCrop.RESULT_ERROR) {
//            Log.d(TAG, "onActivityResult: error");
//            final Throwable cropError = UCrop.getError(data);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_person) {

        } else if (id == R.id.nav_friend) {

        } else if (id == R.id.nav_clear) {

        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(getBaseContext(), SettingsActivity.class));
        } else if (id == R.id.nav_login_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onSubscribe(Object object) {


    }

    @Override
    public void makeMessage(Boolean result) {
        if (result) {
            Toast.makeText(HomeActivity.this, "上传头像成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HomeActivity.this, "上传头像失败", Toast.LENGTH_SHORT).show();
        }
    }
}
