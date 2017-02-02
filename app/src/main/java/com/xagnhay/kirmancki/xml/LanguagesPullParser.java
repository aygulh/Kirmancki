package com.xagnhay.kirmancki.xml;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

import com.xagnhay.kirmancki.R;
import com.xagnhay.kirmancki.model.Langs;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LanguagesPullParser {

	private static final String LOGTAG = "LANGUAGES";
	
	private static final String LANGID = "langId";
	private static final String LANGSHORTNAME = "langShortName";
	private static final String LANGNAME = "langName";
	
	private Langs currentLanguage  = null;
	private String currentTag = null;
	List<Langs> languages = new ArrayList<Langs>();

	public List<Langs> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = context.getResources().openRawResource(R.raw.languages);
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

		return languages;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentLanguage != null && currentTag != null) {
			if (currentTag.equals(LANGID)) {
				Integer id = Integer.parseInt(xmlText);
				currentLanguage.setLangId(id);
			} 
			else if (currentTag.equals(LANGSHORTNAME)) {
				currentLanguage.setLangShortName(xmlText);
			}
			else if (currentTag.equals(LANGNAME)) {
				currentLanguage.setLangName(xmlText);
			}			
		}
	}

	private void handleStartTag(String name) {
		if (name.equals("langs")) {
			currentLanguage = new Langs();
			languages.add(currentLanguage);
		}
		else {
			currentTag = name;
		}
	}
}
