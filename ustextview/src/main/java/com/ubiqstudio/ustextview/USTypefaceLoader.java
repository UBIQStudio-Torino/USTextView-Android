package com.ubiqstudio.ustextview;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class USTypefaceLoader {
	
	private static final Map<String, Typeface> typefaces = new HashMap<String, Typeface>();
	
	public static Typeface getTypeface(AssetManager mgr, String path) {
		if (!typefaces.containsKey(path)) {
			typefaces.put(path, Typeface.createFromAsset(mgr, path));
		}
		
		return typefaces.get(path);
	}
	
}
