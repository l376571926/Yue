package group.tonight.yue;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserFragment extends DialogFragment {
    private static final String TAG = AddUserFragment.class.getSimpleName();

    private EditText mQqView;
    private EditText mWxView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_add_user, container, false);

        mQqView = (EditText) inflate.findViewById(R.id.qq);
        mWxView = (EditText) inflate.findViewById(R.id.wx);

        inflate.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String qq = mQqView.getText().toString();
                final String wx = mWxView.getText().toString();
                if (TextUtils.isEmpty(qq) && TextUtils.isEmpty(wx)) {
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = null;
                        UserDao userDao = UserDatabase.get().getUserDao();
                        if (!TextUtils.isEmpty(qq)) {
                            User usersByQq = userDao.getUsersByQq(qq);
                            if (usersByQq != null) {
                                user = usersByQq;
                            }
                            Log.e(TAG, "run: ");


                        } else {
                            User usersByWx = userDao.getUsersByWx(wx);
                            if (usersByWx != null) {
                                user = usersByWx;
                            }
                            Log.e(TAG, "run: ");
                        }
                        if (user == null) {
                            user = new User();
                            if (!TextUtils.isEmpty(qq)) {
                                user.setQq(qq);
                            }
                            if (!TextUtils.isEmpty(wx)) {
                                user.setWx(wx);
                            }
                            userDao.insert(user);
                        } else {
                            Looper.prepare();
                            Toast.makeText(v.getContext().getApplicationContext(), "用户已存在", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                dismiss();
            }
        });
        return inflate;
    }
}
