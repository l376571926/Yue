package group.tonight.yue;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserDetailActivity extends AppCompatActivity {

    private EditText mQqView;
    private EditText mWxView;
    private EditText mPhoneView;
    private EditText mAddressView;
    private EditText mRemarkView;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mQqView = (EditText) findViewById(R.id.qq);
        mWxView = (EditText) findViewById(R.id.wx);
        mPhoneView = (EditText) findViewById(R.id.phone);
        mAddressView = (EditText) findViewById(R.id.address);
        mRemarkView = (EditText) findViewById(R.id.remark);

        mUser = (User) getIntent().getSerializableExtra("user");
        if (mUser.getQq() != null) {
            mQqView.setText(mUser.getQq());
            mQqView.setEnabled(false);
        }
        if (mUser.getWx() != null) {
            mWxView.setText(mUser.getWx());
            mWxView.setEnabled(false);
        }

        mPhoneView.setText(mUser.getPhone());
        mAddressView.setText(mUser.getAddress());
        mRemarkView.setText(mUser.getRemark());

        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qq = mQqView.getText().toString();
                String wx = mWxView.getText().toString();
                String phone = mPhoneView.getText().toString();
                String address = mAddressView.getText().toString();
                String remark = mRemarkView.getText().toString();

                mUser.setQq(qq);
                mUser.setWx(wx);
                mUser.setPhone(phone);
                mUser.setAddress(address);
                mUser.setRemark(remark);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserDatabase.get()
                                .getUserDao()
                                .update(mUser);
                        Looper.prepare();
                        Toast.makeText(UserDetailActivity.this, "资料更新成功", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                        Looper.loop();
                    }
                }).start();

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
