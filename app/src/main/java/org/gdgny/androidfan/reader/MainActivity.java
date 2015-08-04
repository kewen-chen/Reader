package org.gdgny.androidfan.reader;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.gdgny.androidfan.reader.ui.StaringActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardAdapter mAdapter;
    private FloatingActionButton mfloatingActionButton;
    //上次按下返回键的系统时间
    private long lastBackTime = 0;
    //当前按下返回键的系统时间
    private long currentBackTime = 0;

//    private ArrayList<String> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        initialize();
    }


    private void initialize(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mfloatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        setSupportActionBar(mToolbar);
        initView();
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(mNavigationView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(mNavigationView);
            }
        };

        mDrawerLayout.setDrawerListener(drawerToggle);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            MenuItem preMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (preMenuItem != null)
                    preMenuItem.setChecked(false);
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.section1:
                        Intent in1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivityForResult(in1, 1);
                        finish();

                        break;
                    case R.id.section2: {
                        if (getSupportActionBar() != null) {

                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_about));

                        }
                        mDrawerLayout.closeDrawer(Gravity.LEFT);

                        // v.setSelected(true);
                        Bundle bundle = new Bundle();
                        bundle.putInt(ColorFragment.sARGUMENT_COLOR, R.color.white);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, ColorFragment.newInstance(bundle)).commit();
                        break;
                    }

                }
                return true;

            }
        });
        drawerToggle.syncState();
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemLongClick(View view, int position) {
//               Toast.makeText(getApplicationContext(), "长按", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, StaringActivity.class);

                        intent.putExtra("bookid", position);


                        startActivity(intent);

                    }

                }));
        mfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"感谢南阳GDG android菜鸟饭团！",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //初始化。分割cardview.
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView788);

        mRecyclerView.setHasFixedSize(true);
        int spanCount = 2;
        mLayoutManager = new StaggeredGridLayoutManager(
                spanCount,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new  CardAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }



    private void showSection(int section) {
        Toast.makeText(this, "Section " + String.valueOf(section), Toast.LENGTH_SHORT).show();
    }
    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

        List<Book> mItems;

        public CardAdapter() {

            super();
            mItems = new ArrayList<Book>();
            Book book = new Book();
            book.setName("Ulysses");
            book.setThumbnail(R.drawable.a);
            mItems.add(book);

            book = new Book();
            book.setName("The Iliad of Homer");
            book.setThumbnail(R.drawable.b);
            mItems.add(book);

            book = new Book();
            book.setName("Moby Dick The Whale");
            book.setThumbnail(R.drawable.c);
            mItems.add(book);

            book = new Book();
            book.setName("The Yellow Wallpaper");
            book.setThumbnail(R.drawable.d);
            mItems.add(book);

            book = new Book();
            book.setName("A Tale of Two Cities");
            book.setThumbnail(R.drawable.e);
            mItems.add(book);

            book = new Book();
            book.setName("The Picture of Dorian");
            book.setThumbnail(R.drawable.f);
            mItems.add(book);

            book = new Book();
            book.setName("Grimms' Fairy Tales");
            book.setThumbnail(R.drawable.g);
            mItems.add(book);

            book = new Book();
            book.setName("Being Earnest");
            book.setThumbnail(R.drawable.h);
            mItems.add(book);

            book = new Book();
            book.setName("The Prince");
            book.setThumbnail(R.drawable.i);
            mItems.add(book);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_card, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Book book = mItems.get(i);
            viewHolder.imgThumbnail.setImageResource(book.getThumbnail());
            viewHolder.textview.setText(book.getName());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
        public long getItemId(int position) {

            return position;
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            public  ImageView imgThumbnail;
            public TextView textview;
            public ViewHolder(View itemView) {
                super(itemView);
                imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
                textview=(TextView)itemView.findViewById(R.id.text_card);
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //捕获返回键按下的事件
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //获取当前系统时间的毫秒数
            currentBackTime = System.currentTimeMillis();
            //比较上次按下返回键和当前按下返回键的时间差，如果大于2秒，则提示再按一次退出
            if(currentBackTime - lastBackTime > 2 * 1000){
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_LONG).show();
                lastBackTime = currentBackTime;
            }else{ //如果两次按下的时间差小于2秒，则退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}