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

	public void setPlaceholderColor(int placeholderColor) {
		_generic.setPlaceholderColor(placeholderColor);
	}

}
