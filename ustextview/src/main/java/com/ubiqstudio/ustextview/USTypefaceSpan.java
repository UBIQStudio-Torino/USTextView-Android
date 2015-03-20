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
