package com.run.util;

import android.content.Context;

import com.run.bean.HttpRequestBean;
import com.run.collections.CommandExecutorCollection;

import org.apache.cordova.LOG;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class XmlParseUtil {

    boolean insideFeature = false;
    private HttpRequestBean requestBean = null;
    private CommandExecutorCollection instance = CommandExecutorCollection.getInstance();
    private String id;

    public void parse(Context action) {
        int id = action.getResources().getIdentifier("config", "xml", action.getClass().getPackage().getName());
        if (id == 0) {
            id = action.getResources().getIdentifier("config", "xml", action.getPackageName());
            if (id == 0) {
                LOG.e(this.getClass().getName(), "res/xml/config.xml is missing!");
                return;
            }
        }
        parse(action.getResources().getXml(id));
    }


    public void parse(XmlPullParser xml) {
        int eventType = -1;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                try {
                    handleStartTag(xml);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                handleEndTag(xml);
            }
            try {
                eventType = xml.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleStartTag(XmlPullParser xml) throws IOException, XmlPullParserException {
        String strNode = xml.getName();
        if (strNode.equals("executor-path")) {
            instance.setPackageName(xml.getAttributeValue(null, "path") + ".");
        } else if (strNode.equals("httprequests")) {
            insideFeature = true;
        } else if (insideFeature && strNode.equals("httprequest")) {
            requestBean = new HttpRequestBean();
        } else if (strNode.equals("id")) {
            id = xml.nextText();
        } else if (strNode.equals("url")) {
            requestBean.setUrl(xml.nextText());
        } else if (strNode.equals("class")) {
            requestBean.setClazz(xml.nextText());
        } else if (strNode.equals("desc")) {
            requestBean.setDesc(xml.nextText());
        }
    }

    public void handleEndTag(XmlPullParser xml) {
        String strNode = xml.getName();
        if (strNode.equals("httprequest")) {
            instance.put(id, requestBean.getClazz(), requestBean);
        }
    }

    public static void main(String[] args) {
    }

}
