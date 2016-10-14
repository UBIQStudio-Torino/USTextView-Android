/*
 * Copyright 2016 UBIQ Studio S.n.c.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ubiqstudio.ustextview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;

public class USEditText extends EditText {

	private USGeneric<EditText> _generic;

	public USEditText(Context context) {
		super(context);
		init(null);
	}

	public USEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public USEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
        _generic = new USGeneric<EditText>(this);
        _generic.init(attrs);
	}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        _generic.onDraw(canvas);
    }

	public void setPlaceholder(String placeholder) {
		_generic.setPlaceholder(placeholder);
	}

	public void setPlaceholderColor(int placeholderColor) {
		_generic.setPlaceholderColor(placeholderColor);
	}

}
