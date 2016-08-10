package es.javocsoft.android.lib.toolbox.javascript;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import es.javocsoft.android.lib.toolbox.ToolBox;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * This class is an Interface to be able to launch Android native
 * methods from web through JS. The channel is bidirectional and
 * allows to call web JS methods within Android application.<br><br>
 *
 * Extend this class to add your own Javascript methods.
 *
 * To enable it in the WebView:<br><br>
 * 
 * //This enables the JS in the webviews's content.<br>
 * myWebView.getSettings().setJavaScriptEnabled(true);<br>
 * //This enables to expose to webviews's web some native android app methods.<br>
 * myWebView.addJavascriptInterface(new WebviewJavascriptInterface(activity, webview), "AndroidJS");<br><br>
 * 
 * Once enabled, in web side there should be a JS object called "AndroidJS".<br><br>
 * 
 * We can use it to check if we are running the web inside Android native app
 * by using this JS code:<br>
 * <code>
 * 	if("AndroidJS" in window){....} 
 * </code>
 *
 * <br><br>
 * See <a href="http://developer.android.com/guide/webapps/webview.html">WebView</a>.
 *
 * @author JavocSoft 2016
 * @version 1.0
 */
public abstract class WebviewJavascriptInterface {

	private Context context;
    private Activity activity;
    private WebView webview;
    
	
	public WebviewJavascriptInterface(Activity activity, WebView webview) {
		this.context = activity.getApplicationContext();
        this.activity = activity;
        this.webview = webview;
	}
	
	
	@JavascriptInterface
    public void androidRefresh() {
    	if(ToolBox.LOG_ENABLE)
    		Log.d(ToolBox.TAG + "::JS[androidRefresh]", "Refresh command send from web.");
    	
    	webview.post(new Runnable() {
            public void run() {
            	webview.reload();
            }
        });
    }
    
    @JavascriptInterface
    public void androidAppExit() {
    	if(ToolBox.LOG_ENABLE)
    		Log.d(ToolBox.TAG + "::JS[androidAppExit]", "Exit command send from web. Finishing app.");
    	
    	activity.finish();
    }

    
    /**
     * This method allows to get the loaded HTML in the webview as an string and do something
     * with it.<br><br>
     * 
     * Usage:<br>
     *	Put this in webview's "onPageFinished":<br
     *		<pre>view.loadUrl("javascript:window.Android.getContent('<html>' + escape(document.getElementsByTagName('html')[0].innerHTML) + '</html>');");</pre>
     * 
     * 
     * @param htmlRaw
     */
    @JavascriptInterface
    public void getContent(String htmlRaw) {
    	
    	String htmlCode = null;
	    try{
	    	htmlCode = URLDecoder.decode(htmlRaw, "UTF-8");
	    }catch(Exception e){}
	    if(ToolBox.LOG_ENABLE)
	    	Log.d(ToolBox.TAG + "::JS[getContent]", "Content: " + (htmlCode!=null?htmlCode:"not_available"));
	    
	    doTaskOnWebviewContent(htmlCode, getOnHTMLContentRunnable());
    }
    
    /**
     * This method should return a class that extends HTMLContentRunnable. 
     * This runnable will be used to do something with the retrieved 
     * webview HTML content.
     * 
     * @return
     */
    protected abstract HTMLContentRunnable getOnHTMLContentRunnable();
    
    
    private void doTaskOnWebviewContent(final String htmlCode, final HTMLContentRunnable runnable) {
    	
    	//Set the HTML to the runnable
    	runnable.setHtmlCode(htmlCode);
    	
    	ToolBox.application_runOnUIThread(null, new Runnable() {
			@Override
			public void run() {
				runnable.run();
			}
		});
    }
    
    protected Context getContext() {
		return context;
	}

	protected Activity getActivity() {
		return activity;
	}

	protected WebView getWebview() {
		return webview;
	}
    
	/**
     * Returning a string implies to url encode
     * to avoid issues in the JS function when
     * using as function parameter.
     * 
     * @param data	data to url encode
     * @return  The url encoded result or ERROR string.
     */
    protected String urlEncode(String data) {
    	String res = "ERROR";
		try {
			res = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(ToolBox.TAG + "::JS[urlEncode]", "Error encoding to UTF-8 the result for the web [" + e.getMessage() + "]");
		}
		
		return res;
    }
    
    //AUXILIAR

	/**
     * 
     * @author jgonzalez
     *
     */
    public abstract class HTMLContentRunnable extends Thread implements Runnable {
    	private String htmlCode = null;
    	
    	public HTMLContentRunnable(){}
    	
    	
		/** 
    	 * Gets the HTML content.
    	 * 
    	 * @return
    	 */
		protected String getHtmlCode() {
			return htmlCode;
		}
		
		public void setHtmlCode(String htmlCode) {
			this.htmlCode = htmlCode;
		}

		/**
		 * Use this method to do something with the webview HTML.
		 */
		public abstract void processHtmlCode();
			

		@Override
		public void run() {
			if(getHtmlCode()!=null && getHtmlCode().length()>0) {
				processHtmlCode();
			}
		}
    }
    
}
