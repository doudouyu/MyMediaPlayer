package com.example.administrator.mymediaplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.Toast;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements OnSwipeMenuItemClickListener {

    private SwipeMenuRecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    private Context mContext;
    private MyAdapter mMenuAdapter;
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //Todo 在这里写条目的点击事件
            Toast.makeText(MainActivity.this,"这是第"+position+"个条目",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试数据，条目的集合数据
        for (int i = 0; i < 40; i++) {
            list.add("我是第" + i + "个条目");
        }
        this.mContext = this;
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        //设置菜单创建器
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //设置条目点击事件
        recyclerView.setSwipeMenuItemClickListener(this);
        //创建recycleView的适配器对象
        mMenuAdapter = new MyAdapter(list);
        //添加条目的点击事件  这里要注意这个点击事件是设置在适配器中的
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        //给recycleview设置适配器
        recyclerView.setAdapter(mMenuAdapter);
    }

    //条目点击事件的处理
    @Override
    public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
        {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
//这个是区分左侧有子条目还是右边有子条目的，对于你来说，也没有什么用处，就注释掉了
//            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
//            }

            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                list.remove(adapterPosition);
                mMenuAdapter.notifyItemRemoved(adapterPosition);
            }
            //TODO 如果是编辑，在写自己的逻辑  我这里是简单的跳转了一个页面
            if (menuPosition == 1){
                Intent intent = new Intent(mContext,Activity2.class);
                startActivity(intent);
            }
        }
    }
    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            //这个宽高可以自己定义  高度一般都是充满父窗体，宽度自己看需求，这里是写在了dimen里面
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//
//            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            {
//                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
//                        .setText("+")// 图标。
//                        .setWidth(width) // 宽度。
//                        .setHeight(height); // 高度。
//                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。
//
//
//            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                //TODO 在这里我添加了两个子模块，一个删除一个编辑，你可以自己设置背景颜色和字体颜色，字体大小，子模块的宽高等等
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(Color.RED)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);

                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(Color.GREEN)
                        .setText("编辑")
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。

            }
        }
    };
}
