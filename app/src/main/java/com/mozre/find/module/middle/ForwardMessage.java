package com.mozre.find.module.middle;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mozre.find.R;
import com.mozre.find.app.SwipeBackActivity;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.presenter.PostDynamicPresenter;

public class ForwardMessage extends SwipeBackActivity implements ForwardView {

    private TextView mTextViewReviewContent;
    private Button mButtonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forward_message_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("转发");
        mTextViewReviewContent = (TextView) findViewById(R.id.forward_message_review_content);
        mButtonSubmit = (Button) findViewById(R.id.forward_message_submit);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "不能为空", Snackbar.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(mTextViewReviewContent.getText())) {
                    Toast.makeText(ForwardMessage.this, "不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    PostArticleData data = (PostArticleData) ForwardMessage.this.getIntent().getSerializableExtra("data");
                    PostDynamicPresenter presenter = new PostDynamicPresenter(getBaseContext(), ForwardMessage.this);
                    presenter.postNewForward(data);
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
}
