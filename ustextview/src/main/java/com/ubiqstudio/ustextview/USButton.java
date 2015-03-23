package com.ubiqstudio.ustextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class USButton extends Button {
	
	private USGeneric<Button> _generic;

	public USButton(Context context) {
		super(context);
		init(null);
	}
	
	public USButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public USButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	private void init(AttributeSet attrs) {
        _generic = new USGeneric<Button>(this, attrs);
	}
	
}
