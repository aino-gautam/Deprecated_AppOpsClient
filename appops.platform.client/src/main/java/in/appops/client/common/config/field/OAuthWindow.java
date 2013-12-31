package in.appops.client.common.config.field;

import com.google.gwt.core.client.JavaScriptObject;

public final class OAuthWindow extends JavaScriptObject {
	
	protected OAuthWindow() {
		
	}

	native boolean isOpen() /*-{
	       return !this.closed;
	    }-*/;

	native void close() /*-{
	       this.close();
	     }-*/;

	public void closeWindow() {
		if (this != null && this.isOpen()) {
			this.close();
		}
	}

	public static native OAuthWindow openWindow(String url) /*-{
		return $wnd.open(url, '_blank', 'width=300,height=200');
	}-*/;
}
