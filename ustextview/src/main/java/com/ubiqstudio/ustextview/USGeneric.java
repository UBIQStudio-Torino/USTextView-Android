package com.ubiqstudio.ustextview;

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

public class USGeneric<T extends TextView> {
	
	private String _fontFamily;
	private String _fontFace;
	private CharSequence _html;
	
	public USGeneric(T textView, AttributeSet attrs) {
		if (attrs == null) {
			_fontFamily = null;
			_fontFace = null;
			_html = null;
			return;
		}
		
		TypedArray attrsArray = textView.getContext().obtainStyledAttributes(attrs, R.styleable.TextView);
		_fontFamily = attrsArray.getString(R.styleable.TextView_usFontFamily);
		_fontFace = attrsArray.getString(R.styleable.TextView_usFontFace);
		_html = attrsArray.getText(R.styleable.TextView_usHtml);
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
				if (!textView.isInEditMode()) {
                    textView.setTypeface(USTypefaceLoader.getTypeface(textView.getContext().getAssets(), path.concat(_fontFace)));
				}
			}
			
			return;
		}
		
		Spanned spanned = null;
		if (_html instanceof Spanned) {
			spanned = (Spanned) _html;
		}
		
		if (spanned == null) {
            textView.setText(Html.fromHtml(_html.toString()));
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
			if (!textView.isInEditMode()) {
				customSpan.setTypeface(USTypefaceLoader.getTypeface(textView.getContext().getAssets(), path.concat(span.getFamily())));
			}
			editable.setSpan(customSpan, editable.getSpanStart(span), editable.getSpanEnd(span), editable.getSpanFlags(span));
			editable.removeSpan(span);
		}

        textView.setText(editable);
	}
	
}
