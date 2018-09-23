package group.tonight.yue;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public abstract class BaseQAdapter<T, D extends ViewDataBinding, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    public BaseQAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != LOADING_VIEW && viewType != HEADER_VIEW && viewType != EMPTY_VIEW && viewType != FOOTER_VIEW) {
            D d = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.mLayoutResId, new FrameLayout(parent.getContext()), false);
            d.executePendingBindings();
            MVViewHolder mvViewHolder = new MVViewHolder(d);
            bindViewClickListener(mvViewHolder);
            mvViewHolder.setQAdapter(this);
            return (K) mvViewHolder;
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }


    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(v -> getOnItemClickListener().onItemClick(BaseQAdapter.this, v, baseViewHolder.getLayoutPosition() - getHeaderLayoutCount()));
        }
        if (getOnItemLongClickListener() != null) {
            view.setOnLongClickListener(v -> getOnItemLongClickListener().onItemLongClick(BaseQAdapter.this, v, baseViewHolder.getLayoutPosition() - getHeaderLayoutCount()));
        }
    }
}