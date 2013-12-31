/**
 * 
 */
package in.appops.showcase.web.gwt.facebook.client;

import in.appops.client.common.config.field.OAuthWindow;
import in.appops.showcase.web.gwt.facebook.shared.constant.UserPojoConstant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * For this widget to use we would need to provide the service id and the client if of facebook
 * and this in turn will provide the and have access to Oauth window which will be use to talk with fb
 * and to get the response from fb in the form of json object and pass it further
 * for processing.
 * 
 * @author mahesh@ensarm.com
 *
 */
public class AppopsFacebookWidget extends Composite {
	
	private VerticalPanel basePanel;
	private Image facebookImg;
	private String requestUrl;
	
	public AppopsFacebookWidget(String clientId,String serviceId) {
		facebookImg = new Image("images/fb-icon.png");
		initailze(clientId,serviceId);
	}
	
	public AppopsFacebookWidget(String clientId ,Image fbImg,String serviceId) {
		this.setFacebookImg(fbImg);
		initailze(clientId,serviceId);
	}
	
	private void initailze(String clientId,String serviceId){
		try{
			login(this);
			basePanel = new VerticalPanel();
			String url = GWT.getHostPageBaseURL()+"fbLoginServlet?serviceId="+serviceId;
			
			requestUrl = "https://graph.facebook.com/oauth/authorize?client_id="+clientId+"&display=popup&scope=email,create_event,publish_stream,user_birthday&redirect_uri="+url;

			facebookImg.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					OAuthWindow.openWindow(requestUrl);
				}
			});
			
			basePanel.add(facebookImg);
			initWidget(basePanel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	native void login(AppopsFacebookWidget instance) /*-{
		try{
			$wnd.login = function(userData) {
				
				return instance.@in.appops.showcase.web.gwt.facebook.client.AppopsFacebookWidget::doAction(Lcom/google/gwt/core/client/JavaScriptObject;)(userData);
			}
		}
		catch(e){
		alert("An error has occurred: "+e.message)
		}
	}-*/;
  	
  	
	
	private void doAction(JavaScriptObject userDataVal){
		try{
			JSONObject userData = new JSONObject(userDataVal);
			if(!(userData == null)){
				JSONValue val = userData.get(UserPojoConstant.ISREGISTERED);
				boolean isRegistered = Boolean.parseBoolean(val.toString());

				if(isRegistered){
					//Window.alert("User Found");
					//TODO : pass on this json for logged in event for further processing
				}
				else{
					//Window.alert("User Found but doesnt exist ::"+ userData.toString());
					//TODO : sent json for user regieration 
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

  	
	/**
	 * @return the facebookImg
	 */
	public Image getFacebookImg() {
		return facebookImg;
	}

	/**
	 * @param facebookImg the facebookImg to set
	 */
	public void setFacebookImg(Image facebookImg) {
		this.facebookImg = facebookImg;
	}
}
