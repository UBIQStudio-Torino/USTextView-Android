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

import android.graphics.Typeface;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class USTypefaceSpan extends TypefaceSpan {

	private Typeface _typeface;

	public USTypefaceSpan(String family) {
		super(family);
		setTypeface(null);
	}

	public USTypefaceSpan(Parcel src) {
		super(src);
		setTypeface(null);
	}

	public void setTypeface(Typeface typeface) {
		_typeface = typeface;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		super.updateDrawState(ds);

		if (_typeface != null) {
			ds.setTypeface(_typeface);
		}
	}

	@Override
	public void updateMeasureState(TextPaint paint) {
		super.updateMeasureState(paint);

		if (_typeface != null) {
			paint.setTypeface(_typeface);
		}
	}

}
