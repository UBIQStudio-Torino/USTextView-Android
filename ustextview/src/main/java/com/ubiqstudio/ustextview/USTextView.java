package com.ubiqstudio.ustextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class USTextView extends TextView {
	
	private USGeneric<TextView> _generic;

	public USTextView(Context context) {
		super(context);
		init(null);
	}
	
	public USTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public USTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	private void init(AttributeSet attrs) {
        _generic = new USGeneric<TextView>(this);
        _generic.init(attrs);
	}
	
}
