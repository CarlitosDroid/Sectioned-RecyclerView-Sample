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

package com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable;

import android.support.v7.widget.RecyclerView;

import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action.LegacySwipeResultAction;
import com.example.carlitos.swipeitemrecycler.view.animation.swipe_item.swipeable.action.SwipeResultAction;


public class SwipeableItemInternalUtils {
    private SwipeableItemInternalUtils() {
    }

    @SuppressWarnings("unchecked")
    public static SwipeResultAction invokeOnSwipeItem(
            BaseSwipeableItemAdapter<?> adapter, RecyclerView.ViewHolder holder, int position, int result) {

        if (adapter instanceof LegacySwipeableItemAdapter) {

            int reaction = ((LegacySwipeableItemAdapter) adapter).onSwipeItem(
                    holder, position, result);

            switch (reaction) {
                case RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_DEFAULT:
                case RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_MOVE_TO_SWIPED_DIRECTION:
                case RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_REMOVE_ITEM:
                    //noinspection deprecation
                    return new LegacySwipeResultAction<>(
                            (LegacySwipeableItemAdapter) adapter,
                            holder, position, result, reaction);
                default:
                    throw new IllegalStateException("Unexpected reaction type: " + reaction);
            }
        } else {
            return ((SwipeableItemAdapter) adapter).onSwipeItem(holder, position, result);
        }
    }
}
