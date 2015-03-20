package com.ubiqstudio.ustextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class USTextView extends TextView {
	
	private String _fontFamily;
	private String _fontFace;
	private CharSequence _html;
	
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
		if (attrs == null) {
			_fontFamily = null;
			_fontFace = null;
			_html = null;
			return;
		}
		
		TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.USTextView);
		_fontFamily = attrsArray.getString(R.styleable.USTextView_fontFamily);
		_fontFace = attrsArray.getString(R.styleable.USTextView_fontFace);
		_html = attrsArray.getText(R.styleable.USTextView_html);
		attrsArray.recycle();
		
		if (_fontFamily == null) {
			_fontFamily = "";
		} else {
			_fontFamily = _fontFamily.trim();
		}
		
		String path = _fontFamily;
		if (!path.isEmpty()) {
			path = path.concat("/");
		}
		
		if (_html == null) {
			if (_fontFace == null) {
				_fontFace = "";
			} else {
				_fontFace = _fontFace.trim();
			}
			
			if (!_fontFace.isEmpty()) {
				if (!isInEditMode()) {
					setTypeface(USTypefaceLoader.getTypeface(getContext().getAssets(), path.concat(_fontFace)));
				}
			}
			
			return;
		}
		
		Spanned spanned = null;
		if (_html instanceof Spanned) {
			spanned = (Spanned) _html;
		}
		
		if (spanned == null) {
			setText(Html.fromHtml(_html.toString()));
			return;
		}
		
		SpannableStringBuilder editable = new SpannableStringBuilder();
		
		int spanned_length = spanned.length();
		for (int i=0, next=0; i<spanned_length; i=next) {
			next = spanned.nextSpanTransition(i, spanned_length, Object.class);
			
			Spanned spannedPart = Html.fromHtml(spanned.subSequence(i, next).toString());
			Spannable spannablePart = new SpannableString(spannedPart.toString());
			
			for (Object span : spanned.getSpans(i, next, Object.class)) {
				spannablePart.setSpan(span, 0, spannablePart.length(), spanned.getSpanFlags(span));
			}
			
			for (Object span : spannedPart.getSpans(0, spannedPart.length(), Object.class)) {
				spannablePart.setSpan(span, spannedPart.getSpanStart(span), spannedPart.getSpanEnd(span), spannedPart.getSpanFlags(span));
			}
			
			editable.append(spannablePart);
		}
		
		for (ForegroundColorSpan span : editable.getSpans(0, editable.length(), ForegroundColorSpan.class)) {
			ForegroundColorSpan customSpan = new ForegroundColorSpan((int) (0xFF000000l + span.getForegroundColor()));
			editable.setSpan(customSpan, editable.getSpanStart(span), editable.getSpanEnd(span), editable.getSpanFlags(span));
			editable.removeSpan(span);
		}
		
		for (TypefaceSpan span : editable.getSpans(0, editable.length(), TypefaceSpan.class)) {
			USTypefaceSpan customSpan = new USTypefaceSpan(_fontFamily);
			if (!isInEditMode()) {
				customSpan.setTypeface(USTypefaceLoader.getTypeface(getContext().getAssets(), path.concat(span.getFamily())));
			}
			editable.setSpan(customSpan, editable.getSpanStart(span), editable.getSpanEnd(span), editable.getSpanFlags(span));
			editable.removeSpan(span);
		}
		
		setText(editable);
	}
	
}
