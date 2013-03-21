package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class IntelliThoughtField extends Widget implements Field, HasText, ClickHandler {
	private Configuration configuration;
	private String fieldValue;
	
	public static final String INTELLITEXTFIELD_VISIBLELINES = "intelliShareFieldVisibleLines";
	public static final String INTELLITEXTFIELD_PRIMARYCSS = "intelliShareFieldPrimaryCss";
	public static final String INTELLITEXTFIELD_DEPENDENTCSS = "intelliShareFieldDependentCss";
	public static final String INTELLITEXTFIELD_MAXCHARLENGTH = "maxLength";
	public static final String INTELLITEXTFIELD_CONTENTEDITABLE = "contenteditable";
	


	@Override
	public void createField() throws AppOpsException {
		if(configuration == null){
			throw new AppOpsException("No Configuration available");
		} else{
			String primaryCss = configuration.getPropertyByName(INTELLITEXTFIELD_PRIMARYCSS) != null ?  configuration.getPropertyByName(INTELLITEXTFIELD_PRIMARYCSS).toString() : null;  
			String dependentCss = configuration.getPropertyByName(INTELLITEXTFIELD_DEPENDENTCSS) != null ?  configuration.getPropertyByName(INTELLITEXTFIELD_DEPENDENTCSS).toString() : null;  
			String maxCharLength = configuration.getPropertyByName(INTELLITEXTFIELD_MAXCHARLENGTH) != null ?  configuration.getPropertyByName(INTELLITEXTFIELD_MAXCHARLENGTH).toString() : null;  
			
			if(primaryCss != null){
				this.setStylePrimaryName("intelliShareField");
			} 

			if(dependentCss != null){
				this.addStyleName(dependentCss);
			} 

			if(dependentCss != null){
				this.getElement().setAttribute(INTELLITEXTFIELD_MAXCHARLENGTH, maxCharLength);
			}
			
			Element intelliText = DOM.createDiv();

			intelliText.setClassName("intelliTextField");
			intelliText.setId("intelliTextField");
			this.setElement(intelliText);
			this.setText("Any Thoughts");
			this.getElement().setAttribute(INTELLITEXTFIELD_CONTENTEDITABLE, "true");
			this.addDomHandler(this, ClickEvent.getType());
		}
		
	}

	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}	
	
	@Override
	public void clearField() {
		this.setText("");
	}

	@Override
	public void resetField() {
		
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}


	@Override
	public String getText() {
		return this.getElement().getInnerText().trim();
	}


	@Override
	public void setText(String text) {
		this.getElement().setInnerText(text);
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(ClickEvent event) {
		if(this.getText().equalsIgnoreCase("Any Thoughts")) {
			this.setText("");
		}
	}

}
