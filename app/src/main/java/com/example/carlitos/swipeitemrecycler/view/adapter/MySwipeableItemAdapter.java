package com.example.carlitos.swipeitemrecycler.view.adapter;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.carlitos.swipeitemrecycler.R;
import com.example.carlitos.swipeitemrecycler.model.entity.SubHeaderEntity;
import com.example.carlitos.swipeitemrecycler.model.entity.MainHeaderEntity;
import com.example.carlitos.swipeitemrecycler.view.control.ImageProfilePercentage;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.SwipeableItemAdapter;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.SwipeableItemConstants;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action.SwipeResultAction;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action.SwipeResultActionDefault;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action.SwipeResultActionRemoveItem;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.utils.AbstractSwipeableItemViewHolder;
import com.example.carlitos.swipeitemrecycler.model.entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Vargas on 07/08/16.
 * Alias: CarlitosDroid
 */

public class MySwipeableItemAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements SwipeableItemAdapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MySwipeableItemAdapter";


    public static final int TYPE_MAIN_HEADER = 0;
    public static final int TYPE_SUB_HEADER = 1;
    public static final int TYPE_ITEM = 2;

    List<Object> listObjects = new ArrayList<>();
    private EventListener mEventListener;
    private long objectsId;

    public interface EventListener {
        void onItemPinned(int position);
    }


    public MySwipeableItemAdapter(List<Object> listObjects, long objectsId) {
        this.listObjects = listObjects;
        this.objectsId = objectsId;
        // SwipeableItemAdapter requires stable ID, and also
        // have to implement the getItemId() method appropriately.
        setHasStableIds(true);
    }


    public class MainHeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageProfilePercentage imgProfile;
        AppCompatTextView lblName;
        AppCompatTextView lblAge;
        AppCompatTextView lblAddress;

        public MainHeaderViewHolder(View itemView) {
            super(itemView);
            imgProfile = (ImageProfilePercentage) itemView.findViewById(R.id.imgProfile);
            lblName = (AppCompatTextView) itemView.findViewById(R.id.lblName);
            lblAge = (AppCompatTextView) itemView.findViewById(R.id.lblAge);
            lblAddress = (AppCompatTextView) itemView.findViewById(R.id.lblAddress);
            imgProfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgProfile:
                    Toast.makeText(view.getContext(), "IMAGE", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatImageView imgAdd;
        AppCompatTextView lblDate;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            lblDate = (AppCompatTextView) itemView.findViewById(R.id.lblDate);
            imgAdd = (AppCompatImageView) itemView.findViewById(R.id.imgAdd);
            imgAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgAdd:
                    if(listObjects.get(getAdapterPosition()) instanceof SubHeaderEntity){
                        if((((SubHeaderEntity)listObjects.get(getAdapterPosition())).getTitle().equals("Hobby"))){
                            ItemEntity itemEntity = new ItemEntity(objectsId++, false, "OrbisMobile ","CarlitosDroid ", false);
                            listObjects.add(getAdapterPosition()+1, itemEntity);
                            notifyItemInserted(getAdapterPosition()+1);

                        }else if((((SubHeaderEntity)listObjects.get(getAdapterPosition())).getTitle().equals("Skills"))){
                            ItemEntity itemEntity = new ItemEntity(objectsId++, false, "OrbisMobile ", "Ricardo Sensei", false);
                            listObjects.add(getAdapterPosition()+1, itemEntity);
                            notifyItemInserted(getAdapterPosition()+1);
                        }
                    }

                    break;
            }
        }
    }

    public class ItemViewHolder extends AbstractSwipeableItemViewHolder implements View.OnClickListener{
        final FrameLayout mContainer;
        final AppCompatTextView lblTitle;
        final AppCompatTextView lblDescription;
        final LinearLayout lnlNotification;
        final AppCompatImageButton btnDelete;
        public ItemViewHolder(View v) {
            super(v);
            mContainer = (FrameLayout) itemView.findViewById(R.id.container);
            lblTitle = (AppCompatTextView) itemView.findViewById(R.id.lblTitle);
            lblDescription = (AppCompatTextView) itemView.findViewById(R.id.lblDescription);
            lnlNotification = (LinearLayout) itemView.findViewById(R.id.lnlNotification);
            btnDelete = (AppCompatImageButton) itemView.findViewById(R.id.btnDelete);

            lblTitle.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public View getSwipeableContainerView() {
            return mContainer;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lblTitle:
                    Toast.makeText(v.getContext(), lblTitle.getText(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnDelete:

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Toast.makeText(v.getContext(), "item deleted "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        listObjects.remove(position);
                        notifyItemRemoved(position);
                    }
                    break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        if(listObjects.get(position) instanceof MainHeaderEntity){
            return ((MainHeaderEntity)listObjects.get(position)).getId();
        }else if(listObjects.get(position) instanceof SubHeaderEntity){
            return ((SubHeaderEntity)listObjects.get(position)).getId();
        }else {
            return ((ItemEntity)listObjects.get(position)).getId();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(listObjects.get(position) instanceof MainHeaderEntity){
            return TYPE_MAIN_HEADER;
        }else if(listObjects.get(position) instanceof SubHeaderEntity){
            return TYPE_SUB_HEADER;
        }else if(listObjects.get(position) instanceof ItemEntity){
            return TYPE_ITEM;
        }else{
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_MAIN_HEADER:
                final LayoutInflater inflater1 = LayoutInflater.from(parent.getContext());
                final View v1 = inflater1.inflate(R.layout.list_main_header, parent, false);
                return new MainHeaderViewHolder(v1);
            case TYPE_SUB_HEADER:
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.list_item_subheader, parent, false);
                return new HeaderViewHolder(view);
            case TYPE_ITEM:
                final LayoutInflater inflater2 = LayoutInflater.from(parent.getContext());
                final View v2 = inflater2.inflate(R.layout.list_item, parent, false);
                return new ItemViewHolder(v2);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_MAIN_HEADER:
                break;
            case TYPE_SUB_HEADER:
                ((HeaderViewHolder)holder).lblDate.setText(((SubHeaderEntity)listObjects.get(position)).getTitle());

                break;
            case TYPE_ITEM:
                ((ItemViewHolder)holder).lblTitle.setText(((ItemEntity)listObjects.get(position)).getName());
                ((ItemViewHolder)holder).lblDescription.setText(((ItemEntity)listObjects.get(position)).getDescription());

                // set background resource (target view ID: container)
                final int swipeState = ((ItemViewHolder)holder).getSwipeStateFlags();

                if ((swipeState & SwipeableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {
                    int bgResId = R.drawable.bg_item_normal_state;
                    ((ItemViewHolder)holder).mContainer.setBackgroundResource(bgResId);
                }

                // set swiping properties
                ((ItemViewHolder)holder).setSwipeItemHorizontalSlideAmount(
                        ((ItemEntity)listObjects.get(position)).isPinned() ? SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_LEFT : 0);

                // set swiping properties
                ((ItemViewHolder)holder).setMaxLeftSwipeAmount(-0.3f);
                ((ItemViewHolder)holder).setMaxRightSwipeAmount(0);
                ((ItemViewHolder)holder).setSwipeItemHorizontalSlideAmount(
                        ((ItemEntity)listObjects.get(position)).isPinned() ? -0.3f : 0);

                break;
        }

    }

    @Override
    public int getItemCount() {
        return listObjects.size();
    }

    @Override
    public int onGetSwipeReactionType(RecyclerView.ViewHolder holder, int position, int x, int y) {
        return SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public void onSetSwipeBackground(RecyclerView.ViewHolder holder, int position, int type) {
        int bgRes = 0;
        holder.itemView.setBackgroundResource(bgRes);
    }

    @Override
    public SwipeResultAction onSwipeItem(RecyclerView.ViewHolder holder, final int position, int result) {
        Log.d(TAG, "onSwipeItem(position = " + position + ", result = " + result + ")");

        switch (result) {
            // swipe right
            case SwipeableItemConstants.RESULT_SWIPED_RIGHT:
                if (((ItemEntity)listObjects.get(position)).isPinned()) {
                    // pinned --- back to default position
                    return new UnpinResultAction(this, position);
                } else {
                    // not pinned --- remove
                    return new SwipeRightResultAction(this, position);
                }
                // swipe left -- pin
            case SwipeableItemConstants.RESULT_SWIPED_LEFT:
                return new SwipeLeftResultAction(this, position);
            // other --- do nothing
            case SwipeableItemConstants.RESULT_CANCELED:
            default:
                if (position != RecyclerView.NO_POSITION) {
                    return new UnpinResultAction(this, position);
                } else {
                    return null;
                }
        }
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }

    private static class SwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {
        private MySwipeableItemAdapter mAdapter;
        private final int mPosition;
        private boolean mSetPinned;

        SwipeLeftResultAction(MySwipeableItemAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            ItemEntity itemEntity = (ItemEntity)mAdapter.listObjects.get(mPosition);

            if (!itemEntity.isPinned()) {
                itemEntity.setPinned(true);
                mAdapter.notifyItemChanged(mPosition);
                mSetPinned = true;
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

            if (mSetPinned && mAdapter.mEventListener != null) {
                mAdapter.mEventListener.onItemPinned(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class SwipeRightResultAction extends SwipeResultActionRemoveItem {
        private MySwipeableItemAdapter mAdapter;
        private final int mPosition;
        private boolean mSetPinned;

        SwipeRightResultAction(MySwipeableItemAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();
            ItemEntity itemEntity = (ItemEntity)mAdapter.listObjects.get(mPosition);

            if (!itemEntity.isPinned()) {
                itemEntity.setPinned(true);
                mAdapter.notifyItemChanged(mPosition);
                mSetPinned = true;
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
            Log.e("Y-slindeend ","Y-slindeend ");
            if (mSetPinned && mAdapter.mEventListener != null) {
                mAdapter.mEventListener.onItemPinned(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class UnpinResultAction extends SwipeResultActionDefault {
        private MySwipeableItemAdapter mAdapter;
        private final int mPosition;

        UnpinResultAction(MySwipeableItemAdapter adapter, int position) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            ItemEntity itemEntity = (ItemEntity) mAdapter.listObjects.get(mPosition);
            if (itemEntity.isPinned()) {
                itemEntity.setPinned(false);
                mAdapter.notifyItemChanged(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }
}
