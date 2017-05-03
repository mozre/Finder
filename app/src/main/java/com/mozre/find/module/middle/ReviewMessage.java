package com.mozre.find.module.middle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mozre.find.R;
import com.mozre.find.app.SwipeBackActivity;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.presenter.PostDataDao;
import com.mozre.find.presenter.PostDetailPresenter;
import com.mozre.find.presenter.PostDynamicPresenter;

public class ReviewMessage extends SwipeBackActivity implements ReviewView {

    private TextView mTextViewContext;
    private Button mButtonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("评论");
        mTextViewContext = (TextView) findViewById(R.id.review_message_content);
        mButtonSubmit = (Button) findViewById(R.id.review_message_submit);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTextViewContext.getText())) {
                    Toast.makeText(ReviewMessage.this, "空空如也~~再写几句吧！", Toast.LENGTH_SHORT).show();
                } else {
                    PostDynamicPresenter presenter = new PostDynamicPresenter(getBaseContext(), ReviewMessage.this);
                    PostArticleData data = (PostArticleData) ReviewMessage.this.getIntent().getSerializableExtra("data");
                    presenter.postNewReview(mTextViewContext.getText().toString(), data);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void reviewState(Boolean result) {
        if (result) {
            Toast.makeText(ReviewMessage.this, "发表成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ReviewMessage.this, "发表失败！", Toast.LENGTH_SHORT).show();
        }
    }
}
