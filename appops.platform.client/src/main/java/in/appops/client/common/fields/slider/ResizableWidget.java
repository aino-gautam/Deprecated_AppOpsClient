package in.appops.client.common.fields.slider;

import com.google.gwt.dom.client.Element;

public interface ResizableWidget {
	Element getElement();

	boolean isAttached();

	void onResize(int width, int height);
}
