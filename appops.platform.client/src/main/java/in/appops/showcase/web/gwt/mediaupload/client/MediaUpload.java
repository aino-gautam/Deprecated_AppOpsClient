package in.appops.showcase.web.gwt.mediaupload.client;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MediaUpload implements EntryPoint {
	
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
	@SuppressWarnings("unchecked")
	
	public void onModuleLoad() {
		final FormPanel formpanel = new FormPanel();
		 formpanel.setAction(GWT.getHostPageBaseURL() + "servlet.gupld");
		formpanel.setMethod(FormPanel.METHOD_POST);
		formpanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		
		VerticalPanel panel = new VerticalPanel();
		FileUpload fileUpload = new FileUpload();
		fileUpload.setName("fileupload");
		panel.add(fileUpload);
		Button button = new Button("Done");
		panel.add(button);
		
		button.addClickHandler(new ClickHandler() {
			
			
			public void onClick(ClickEvent event) {
				formpanel.submit();
				
			}
		});
		
		formpanel.add(panel);
		RootPanel.get().add(formpanel);
		
		
	}
	
}
