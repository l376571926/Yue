package group.tonight.yue;

import android.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class MVViewHolder<T extends ViewDataBinding> extends BaseViewHolder {
    T binding;

    public MVViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public BaseViewHolder setQAdapter(BaseQuickAdapter adapter) {
        super.setAdapter(adapter);
        return this;
    }

    public T getDataViewBinding() {
        return this.binding;
    }

}