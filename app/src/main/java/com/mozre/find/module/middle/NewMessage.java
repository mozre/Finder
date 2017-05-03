package com.mozre.find.module.middle;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.app.BaseActivity;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.presenter.NewMessagePresenter;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewMessage extends BaseActivity implements NewMessageView, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "NewMessage";
    private Dialog dialog;
    private android.widget.EditText mEditTextTitle;
    private android.widget.EditText mEditTextDescription;
    private android.widget.ImageButton mButtonSrc;
    private com.facebook.drawee.view.SimpleDraweeView mSimpleDraweeShow;
    private android.widget.EditText mEditTextDetail;
    private android.widget.Button mButtonSubmit;
    private PopupMenu mPopupMenu;
    private InputMethodManager mMethodManager;
    private String imagePath;
    private static final int GET_IMG_REQUEST_CODE = 100;
    private static final int GET_IMG_UCROP_REQUEST_CODE = 200;
    private static final int SUCCESS_TO_HOME_RESULT_CODE = 20;
    private PostArticleData checkSubmit() {
        if (TextUtils.isEmpty(mEditTextTitle.getText())) {
            return null;
        }
        if (TextUtils.isEmpty(mEditTextDescription.getText())) {
            return null;
        }
        if (TextUtils.isEmpty(mEditTextDetail.getText())) {
            return null;
        }
        PostArticleData mData = new PostArticleData();
        mData.setTitle(mEditTextTitle.getText().toString());
        mData.setDescription(mEditTextDescription.getText().toString());
        mData.setDetail(mEditTextDetail.getText().toString());
        if (imagePath != null) {
            mData.setImageUri(imagePath);
        }
        return mData;
    }

    private void init() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("放弃此次编辑?")
                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.new_message_button_submit:
//                    Log.d(TAG, "onClick: submit");
                    PostArticleData mData = checkSubmit();
                    if (mData != null) {
                        NewMessagePresenter presenter = new NewMessagePresenter(NewMessage.this, getBaseContext());
                        String tab = NewMessage.this.getIntent().getStringExtra("tab");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        mData.setSeconds(Calendar.getInstance().getTimeInMillis());
                        mData.setPostTime(dateFormat.format(new Date()));
                        switch (tab) {
                            case "文章":
                                Log.d(TAG, "onClick: 文章" + mData.getImageUri());
                                presenter.postNewArticleMessage(mData);
                                break;
                            case "电影":
                                presenter.postNewFlimMessage(mData);
                                break;
                            case "音乐":
                                break;
                        }

                    }
                    break;
                case R.id.new_message_button_src:
                    showPopupMenu(view);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_IMG_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: uri = " + data.getData());
            Uri uri = data.getData();
            imagePath = uri.getPath();
        } else if (requestCode == GET_IMG_UCROP_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult2: uri = " + data.getData());
            File maindir = new File(this.getCacheDir().getAbsolutePath() + File.separator + "tmp");
            try {
                Log.d(TAG, "onActivityResult: here" + maindir.exists());
                File tmpfile = new File(this.getCacheDir().getAbsolutePath() + File.separator + "tmp" + File.separator + "uploadimage.png");
                if (!maindir.exists()) {
                    maindir.mkdirs();
                }
                Log.d(TAG, "onActivityResult: " + maindir.getAbsolutePath() + tmpfile.exists() + " " + tmpfile.getAbsolutePath());
                if (!tmpfile.exists()) {
                    boolean r = tmpfile.createNewFile();
                    Log.d(TAG, "onActivityResult: " + r);
                }
                Log.d(TAG, "onActivityResult: " + tmpfile.exists());
                Uri regionUri = data.getData();
                Uri aimUri = Uri.fromFile(tmpfile);
                UCrop.of(regionUri, aimUri)
                        .withAspectRatio(5, 4)
                        .withMaxResultSize(1000, 1000)
                        .start(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            mSimpleDraweeShow.setImageURI(uri);
            imagePath = uri.getPath();
            Log.d(TAG, "onActivityResult: resultPath = " + imagePath);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Log.d(TAG, "onActivityResult: error");
            final Throwable cropError = UCrop.getError(data);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message_activity);
        getSupportActionBar().setTitle("新建消息");
        this.mButtonSubmit = (Button) findViewById(R.id.new_message_button_submit);
        this.mEditTextDetail = (EditText) findViewById(R.id.new_message_edit_detail);
        this.mSimpleDraweeShow = (SimpleDraweeView) findViewById(R.id.new_message_drawer_show);
        this.mButtonSrc = (ImageButton) findViewById(R.id.new_message_button_src);
        this.mEditTextDescription = (EditText) findViewById(R.id.new_message_edit_description);
        this.mEditTextTitle = (EditText) findViewById(R.id.new_message_edit_title);
        this.mMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        this.mButtonSrc.setOnClickListener(onClickListener);
        this.mButtonSubmit.setOnClickListener(onClickListener);
    }

    public void showPopupMenu(View view) {
        mPopupMenu = new PopupMenu(this, view);
        mPopupMenu.getMenuInflater().inflate(R.menu.popup_menu, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(this);
        mPopupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popup_menu_item_choice_1:
                Toast.makeText(NewMessage.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_IMG_REQUEST_CODE);
                break;
            case R.id.popup_menu_item_choice_2:
                Toast.makeText(NewMessage.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1, GET_IMG_UCROP_REQUEST_CODE);
                break;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "onOptionsItemSelected: ");
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog.show();
        }

        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
//            Log.d(TAG, "onTouchEvent: ");
//            mMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public void publishState(boolean result) {

        if (result) {
            Toast.makeText(NewMessage.this, "发表成功！", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(NewMessage.this, "发表失败！", Toast.LENGTH_SHORT).show();
        }

    }
}
