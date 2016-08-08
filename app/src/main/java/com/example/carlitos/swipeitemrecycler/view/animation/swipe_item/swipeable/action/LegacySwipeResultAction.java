/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action;

import android.support.v7.widget.RecyclerView;

import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.LegacySwipeableItemAdapter;


public class LegacySwipeResultAction<VH extends RecyclerView.ViewHolder>
        extends SwipeResultAction {

    LegacySwipeableItemAdapter<VH> mAdapter;
    VH mHolder;
    int mPosition;
    int mResult;
    int mReaction;

    public LegacySwipeResultAction(
            LegacySwipeableItemAdapter<VH> adapter,
            VH holder, int position, int result, int reaction) {
        super(reaction);
        mAdapter = adapter;
        mHolder = holder;
        mPosition = position;
        mResult = result;
        mReaction = reaction;
    }

    @Override
    protected void onPerformAction() {
        mAdapter.onPerformAfterSwipeReaction(
                mHolder, mPosition, mResult, mReaction);
    }

    @Override
    protected void onCleanUp() {
        super.onCleanUp();

        mAdapter = null;
        mHolder = null;
    }
}
