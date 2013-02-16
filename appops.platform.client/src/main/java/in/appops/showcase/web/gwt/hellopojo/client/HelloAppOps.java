/**
 * 
 */
package in.appops.showcase.web.gwt.hellopojo.client;

import java.util.HashMap;
import java.util.Map;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.operation.Result;
import in.appops.showcase.web.gwt.hellopojo.shared.HelloPojo;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Debasish Padhy Created it on 27-Aug-2012
 */
public class HelloAppOps implements EntryPoint {
	
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
	@SuppressWarnings("unchecked")
	
	public void onModuleLoad() {
		/**
		 * For Upload Service
		 */
	/*	final FormPanel formpanel = new FormPanel();
		formpanel.setAction(GWT.getModuleBaseURL() + "uploadservice");
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
		
		formpanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			
			public void onSubmitComplete(SubmitCompleteEvent event) {
				RootPanel.get().clear();
				TestServiceComponent component = new TestServiceComponent();
				RootPanel.get().add(component);
			}
		});*/
		

		
		/**
		 * For HelooPojo
		 */
		Map map = new HashMap();
		map.put("name", "Debasish");
		map.put("msg", " Welcome to Appops!");
		
		StandardAction action = new StandardAction(HelloPojo.class, "hello.HelloService.sayHello", map);
		dispatch.execute(action, new AsyncCallback<Result<HelloPojo>>() {
			
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			
			public void onSuccess(Result<HelloPojo> result) {
				Window.alert(result.getOperationResult().getName());
			}
		});
		
	}
}
