package com.ubiqstudio.ustextview;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class USGeneric<T extends TextView> {

    private final T _textView;
	
	private String _fontFamily;
	private String _fontFace;
	private CharSequence _html;
    private String _placeholder;
    private int _placeholderColor;
    private boolean _placeholderOnDisabled;

    public USGeneric(T textView) {
        _textView = textView;
    }

    public void setPlaceholder(String placeholder) {
		_placeholder = placeholder;
    }

	public void setPlaceholderColor(int placeholderColor) {
		_placeholderColor = placeholderColor;
    }

    public void init(AttributeSet attrs) {
		if (attrs == null) {
			_fontFamily = null;
			_fontFace = null;
			_html = null;
            _placeholder = null;
			_placeholderColor = Color.GRAY;
            _placeholderOnDisabled = true;
			return;
		}
		
		TypedArray attrsArray = _textView.getContext().obtainStyledAttributes(attrs, R.styleable.TextView);
		_fontFamily = attrsArray.getString(R.styleable.TextView_usFontFamily);
		_fontFace = attrsArray.getString(R.styleable.TextView_usFontFace);
		_html = attrsArray.getText(R.styleable.TextView_usHtml);
        _placeholder = attrsArray.getString(R.styleable.TextView_usPlaceholder);
		_placeholderColor = attrsArray.getColor(R.styleable.TextView_usPlaceholderColor, Color.GRAY);
        _placeholderOnDisabled = attrsArray.getBoolean(R.styleable.TextView_usPlaceholderOnDisabled, true);
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
				if (!_textView.isInEditMode()) {
                    _textView.setTypeface(USTypefaceLoader.getTypeface(_textView.getContext().getAssets(), path.concat(_fontFace)));
				}
			}
			
			return;
		}
		
		Spanned spanned = null;
		if (_html instanceof Spanned) {
			spanned = (Spanned) _html;
		}
		
		if (spanned == null) {
            _textView.setText(Html.fromHtml(_html.toString()));
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
			if (!_textView.isInEditMode()) {
				customSpan.setTypeface(USTypefaceLoader.getTypeface(_textView.getContext().getAssets(), path.concat(span.getFamily())));
			}
			editable.setSpan(customSpan, editable.getSpanStart(span), editable.getSpanEnd(span), editable.getSpanFlags(span));
			editable.removeSpan(span);
		}

        _textView.setText(editable);
	}
	
    public void onDraw(Canvas canvas) {
        if (_placeholder == null) {
            return;
        }

        if (_textView.getText().length() > 0) {
            return;
        }

        if (!_textView.isEnabled()) {
            if (!_placeholderOnDisabled) {
                return;
            }
        }

        Rect lineBounds = new Rect();
        int baselineTop = _textView.getLineBounds(0, lineBounds);

        TextPaint placeholderPaint = new TextPaint(_textView.getPaint());
		placeholderPaint.setColor(_placeholderColor);

		Rect placeholderBounds = new Rect();
		placeholderPaint.getTextBounds(_placeholder, 0, _placeholder.length(), placeholderBounds);

		Rect bounds = new Rect();
		Gravity.apply(_textView.getGravity(), placeholderBounds.width(), placeholderBounds.height(), lineBounds, bounds, _textView.getLayoutDirection());

		canvas.drawText(_placeholder, bounds.left, baselineTop, placeholderPaint);
    }

}
