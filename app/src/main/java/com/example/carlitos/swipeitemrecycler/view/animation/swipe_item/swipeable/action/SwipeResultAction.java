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


public abstract class SwipeResultAction {
    private final int mResultAction;

    protected SwipeResultAction(int resultAction) {
        mResultAction = resultAction;
    }

    public int getResultActionType() {
        return mResultAction;
    }

    public final void performAction() {
        onPerformAction();
    }

    public final void slideAnimationEnd() {
        onSlideAnimationEnd();
        onCleanUp();
    }


    protected void onPerformAction() {
    }

    /**
     * This method is called when item slide animation has completed.
     */
    protected void onSlideAnimationEnd() {
    }

    /**
     * This method is called after the {@link #onSlideAnimationEnd()} method. Clear fields to avoid memory leaks.
     */
    protected void onCleanUp() {
    }
}
