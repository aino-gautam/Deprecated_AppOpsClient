package in.appops.showcase.web.gwt.holder.client;


import in.appops.client.common.fields.LinkField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class ShowcaseComponentHolder extends DockPanel implements EntryPoint{
	
	
	private String packageName = null;
	private String javaDocPackage = "javadoc/";
	private LinkField helpLink = new LinkField();
	
	public ShowcaseComponentHolder() {
		
		try {
			helpLink.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "appops-LinkField", "fieldsJavaDocLink",null));
			helpLink.setFieldValue("Help");
			helpLink.create();
			helpLink.addHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					setHelpHtml(getPackageName());
					
				}
			}, ClickEvent.getType());
			
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		add(helpLink,DockPanel.EAST);
		
	}
	
	public void setHelpHtml(String name) {
		if (name != null) {
			String docPathFromPackage = name.replace(".","/");
			
			String javaDocPath = GWT.getHostPageBaseURL() +javaDocPackage + docPathFromPackage +".html";
			
			Window.open(javaDocPath, "_blank", "");
		}
	}
	
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
	}
	
	
}
