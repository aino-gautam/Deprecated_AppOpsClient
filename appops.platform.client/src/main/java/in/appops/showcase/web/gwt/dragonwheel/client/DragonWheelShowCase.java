/**
 * 
 */
package in.appops.showcase.web.gwt.dragonwheel.client;

import java.util.HashMap;
import java.util.Map;

import in.appops.client.gwt.web.ui.Cylinder;
import in.appops.client.gwt.web.ui.DragonWheelNew;
import in.appops.client.gwt.web.ui.MediaViewer;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Debasish Padhy Created it on 06-Mar-2013
 *
 */

public class DragonWheelShowCase implements EntryPoint{

	final Cylinder cylinder = new Cylinder();
	Button btn =new Button("spin");
		
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
	@SuppressWarnings("unchecked")
	@Override
	public void onModuleLoad() {
		
		MediaViewer mediaViewer = new MediaViewer();
		RootPanel.get("wheelContainer").add(mediaViewer);
		
			
	}

	
}
