package com.example.administrator.mymediaplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import java.util.List;

/** * Created by Administrator on 2016/10/31.
 */
public class MyAdapter extends SwipeMenuAdapter<MyAdapter.MyViewHolder> {
    private  List <String> list;
    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
        public MyAdapter(List<String> list) {
            //构造方法，在这里是初始化集合的作用
        this.list = list;
    }
//TODO 这个是每个子条目的父View  只需要把R.layout.item改成自己的布局即可
    @Override
    public View onCreateContentView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
    }
//TODO 自己创建一个ViewHolder对象返回
    @Override
    public MyViewHolder onCompatCreateViewHolder(View view, int i) {
        //返回一个ViewHolder对象
        return new MyViewHolder(view);
    }
//这里是设置数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(list.get(position));
        //设置监听，把对象传递到holder里面处理回调事件
        holder.setOnItemClickListener(mOnItemClickListener);
    }
//TODO 相当于getCount（）方法
    @Override
    public int getItemCount() {
        //返回当前条目的id  在这里用了三元运算符，判断力和对象是否为空，如果为空，返回0
        return list == null? 0:list.size();
    }

    static  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        OnItemClickListener mOnItemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
