package com.example.carlitos.swipeitemrecycler.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.carlitos.swipeitemrecycler.R;
import com.example.carlitos.swipeitemrecycler.model.entity.SubHeaderEntity;
import com.example.carlitos.swipeitemrecycler.model.entity.MainHeaderEntity;
import com.example.carlitos.swipeitemrecycler.view.adapter.MySwipeableItemAdapter;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.animator.GeneralItemAnimator;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.animator.SwipeDismissItemAnimator;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.RecyclerViewSwipeManager;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.touchguard.RecyclerViewTouchActionGuardManager;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.utils.WrapperAdapterUtils;
import com.example.carlitos.swipeitemrecycler.model.entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Vargas on 07/08/16.
 * Alias: CarlitosDroid
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MySwipeableItemAdapter myItemAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewSwipeManager mRecyclerViewSwipeManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;

    private List<Object> listFQ = new ArrayList<>();
    private long objectsId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false);

        // touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);

        // swipe manager
        mRecyclerViewSwipeManager = new RecyclerViewSwipeManager();

        MainHeaderEntity mainHeaderEntity = new MainHeaderEntity(objectsId++, "Orbis Mobile", "176", "Orbis Ventures");
        SubHeaderEntity subHeaderEntity = new SubHeaderEntity(objectsId++, getString(R.string.sub_header_hobby));
        ItemEntity itemEntity2 = new ItemEntity(objectsId++,false,"Android ", "I like Android development  ",false);
        ItemEntity itemEntity3 = new ItemEntity(objectsId++,false,"iOS ", "I like iOS development  ",false);
        SubHeaderEntity subHeaderEntity1 = new SubHeaderEntity(objectsId++, getString(R.string.sub_header_skills));
        ItemEntity itemEntity5 = new ItemEntity(objectsId++,false,"Javascript ", "I like Javascript development  ",false);
        ItemEntity itemEntity6 = new ItemEntity(objectsId++,false,"C++ ", "I like C++  ",false);
        ItemEntity itemEntity7 = new ItemEntity(objectsId++,false,"C# ", "I like C# development  ",false);
        ItemEntity itemEntity8 = new ItemEntity(objectsId++,false,"MYSQL ", "I like MYSQL development  ",false);
        SubHeaderEntity subHeaderEntity2 = new SubHeaderEntity(objectsId++, getString(R.string.sub_header_about_us));
        ItemEntity itemEntity9 = new ItemEntity(objectsId++,false," We are a team of mobile developers in Orbis Ventures mobiles S.A.C. ",
                getString(R.string.item_about_us),false);

        listFQ.add(mainHeaderEntity);
        listFQ.add(subHeaderEntity);
        listFQ.add(itemEntity2);
        listFQ.add(itemEntity3);
        listFQ.add(subHeaderEntity1);
        listFQ.add(itemEntity5);
        listFQ.add(itemEntity6);
        listFQ.add(itemEntity7);
        listFQ.add(itemEntity8);
        listFQ.add(subHeaderEntity2);
        listFQ.add(itemEntity9);

        myItemAdapter = new MySwipeableItemAdapter(listFQ, objectsId);

        mWrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(myItemAdapter);      // wrap for swiping

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Disable the change animation in order to make turning back animation of swiped item works properly.
        animator.setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);

        // NOTE:
        // The initialization order is very important! This order determines the priority of touch event handling.

        // priority: TouchActionGuard > Swipe > DragAndDrop
        mRecyclerViewTouchActionGuardManager.attachRecyclerView(mRecyclerView);
        mRecyclerViewSwipeManager.attachRecyclerView(mRecyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        if (mRecyclerViewSwipeManager != null) {
            mRecyclerViewSwipeManager.release();
            mRecyclerViewSwipeManager = null;
        }

        if (mRecyclerViewTouchActionGuardManager != null) {
            mRecyclerViewTouchActionGuardManager.release();
            mRecyclerViewTouchActionGuardManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        myItemAdapter = null;
        mLayoutManager = null;

        super.onDestroy();
    }
}
