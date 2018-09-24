package group.tonight.yue;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jxl.read.biff.BiffException;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                requestAllUser();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

//        mRecyclerView.setAdapter(mBaseQuickAdapter);
//        mBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                User user = mBaseQuickAdapter.getItem(position);
//                if (user == null) {
//                    return;
//                }
//                Intent intent = new Intent(HomeActivity.this, UserDetailActivity.class);
//                intent.putExtra("user", user);
//                startActivity(intent);
//            }
//        });

        mRecyclerView.setAdapter(mGirlBaseQAdapter);
//        mGirlBaseQAdapter.setOnItemClick();


        requestAllUser();

        UserDatabase.get().getGirlDao().getAllUsersLiveData().observe(this, new Observer<List<Girl>>() {
            @Override
            public void onChanged(@Nullable List<Girl> girls) {
                if (girls == null) {
                    return;
                }
                mGirlBaseQAdapter.replaceData(girls);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            requestAllUser();
        }
    }

    private void requestAllUser() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final List<User> allUsers = UserDatabase.get()
//                        .getUserDao()
//                        .getAllUsers();
//                InputStream inputStream = getResources().openRawResource(R.raw.user);
//
//                Type type = new TypeToken<List<User>>() {
//                }.getType();
//                List<User> userList = new Gson().fromJson(new InputStreamReader(inputStream), type);
//                allUsers.addAll(userList);
//                KLog.e();
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mBaseQuickAdapter.replaceData(allUsers);
//                    }
//                });
//            }
//        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            new AddUserFragment()
                    .show(getSupportFragmentManager(), AddUserFragment.class.getSimpleName());
        }
        return super.onOptionsItemSelected(item);
    }

//    private BaseQuickAdapter<User, BaseViewHolder> mBaseQuickAdapter = new BaseQuickAdapter<User, BaseViewHolder>(R.layout.list_item_user) {
//        @Override
//        protected void convert(BaseViewHolder helper, User item) {
//            helper.setText(android.R.id.text1, "QQ：" + item.getQq());
//            helper.setText(android.R.id.text2, "微信：" + item.getWx());
//
//            helper.setText(R.id.remark, "备注：" + item.getRemark());
//        }
//    };

    private BaseQAdapter<Girl, ViewDataBinding, MVViewHolder> mGirlBaseQAdapter = new BaseQAdapter<Girl, ViewDataBinding, MVViewHolder>(R.layout.list_item_girl) {
        @Override
        protected void convert(MVViewHolder helper, Girl item) {
            helper.getDataViewBinding().setVariable(group.tonight.yue.BR.girl, item);
        }
    };

}
