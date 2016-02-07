package com.xagnhay.kirmancki.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

import com.xagnhay.kirmancki.R;
import com.xagnhay.kirmancki.model.Category;

public class CategoriesPullParser {

	private static final String LOGTAG = "KIRMANCKI";
	
	private static final String CATID = "catId";
	private static final String CATNAME = "catName";
	private static final String CATDESC = "catDesc";
	private static final String CATLANG = "catlangId";
	
	private Category currentCategory  = null;
	private String currentTag = null;
	List<Category> categories = new ArrayList<Category>();

	public List<Category> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = context.getResources().openRawResource(R.raw.categories);
			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					handleStartTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = null;
				} else if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}

		} catch (NotFoundException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (IOException e) {
			Log.d(LOGTAG, e.getMessage());
		}

		return categories;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentCategory != null && currentTag != null) {
			if (currentTag.equals(CATID)) {
				Integer id = Integer.parseInt(xmlText);
				currentCategory.setCatId(id);
			} 
			else if (currentTag.equals(CATNAME)) {
				currentCategory.setCatName(xmlText);
			}
			else if (currentTag.equals(CATDESC)) {
				currentCategory.setCatDesc(xmlText);
			}
			else if (currentTag.equals(CATLANG)) {
				Integer id = Integer.parseInt(xmlText);
				currentCategory.setCatlangId(id);
			}
		}
	}

	private void handleStartTag(String name) {
		if (name.equals("category")) {
			currentCategory = new Category();
			categories.add(currentCategory);
		}
		else {
			currentTag = name;
		}
	}
}
