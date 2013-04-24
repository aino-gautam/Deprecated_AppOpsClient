package in.appops.client.common.components;

import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * 
 * @author nitish@ensarm.com
 * TODO - This will enhanced/changed as required.
 */
public class ActionWidget extends Composite implements Configurable{
	private Label actionLabel;
	private Anchor actionLink;
	private Button actionButton;

	private Configuration configuration;
	private ActionWidgetType widgetType;

	/******** ActionWidget Related Constants **********/
	public enum ActionWidgetType{
		LABEL, LINK, BUTTON
	};

	public enum ActionWidgetConfiguration{
		PRIMARY_CSS("primaryCss"),
		DEPEDENT_CSS("dependentcss");
		
		private final String value;       

	    private ActionWidgetConfiguration(String value) {
	        this.value= value;
	    }

	    public String toString(){
	       return value;
	    }
	};
	
	/************ Constructors *************************/
	
	public ActionWidget(ActionWidgetType widgetType) {
		this.widgetType = widgetType;
		
		intializeWidget();
		initWidget(widgetType == ActionWidgetType.LABEL ? actionLabel : (widgetType == ActionWidgetType.LINK ? actionLink : actionButton));
	}

	/*********** Member Methods  *************/
	private void intializeWidget() {
		if(widgetType == ActionWidgetType.LABEL){
			actionLabel = new Label();
		} else if(widgetType == ActionWidgetType.LINK){
			actionLink = new Anchor();
		} else if(widgetType == ActionWidgetType.BUTTON){
			actionButton = new Button();
		}		
	}

	public void createUi() {
		
		/********** You can set here any required things like CSS etc from Configuration ******/
		if(configuration != null){
			applyConfiguration();
		}
		
	}

	public void setWidgetText(String text) {
		if(getWidgetType() == ActionWidgetType.LABEL){
			actionLabel.setText(text);
		} else if(getWidgetType() == ActionWidgetType.LINK){
			actionLink.setText(text);
		} else if(getWidgetType() == ActionWidgetType.BUTTON){
			actionButton.setText(text);
		}
	}
	
	public String getWidgetText() {
		if(getWidgetType() == ActionWidgetType.LABEL){
			return actionLabel.getText();
		} else if(getWidgetType() == ActionWidgetType.LINK){
			return actionLink.getText();
		} else if(getWidgetType() == ActionWidgetType.BUTTON){
			return actionButton.getText();
		}
		return null;
	}

	public void addClickHandler(ClickHandler handler) {
		if(widgetType == ActionWidgetType.LABEL){
			actionLabel.addClickHandler(handler);
		} else if(widgetType == ActionWidgetType.LINK){
			actionLink.addClickHandler(handler);
		} else if(widgetType == ActionWidgetType.BUTTON){
			actionButton.addClickHandler(handler);
		}			
	}
	
	private void applyConfiguration() {
		//TODO Apply Configurations here

		String primaryCss = configuration.getPropertyByName(ActionWidgetConfiguration.PRIMARY_CSS.toString()).toString();
		//String dependentCss = configuration.getPropertyByName(ActionWidgetConfiguration.DEPEDENT_CSS.toString()).toString();
		
		if(widgetType == ActionWidgetType.LABEL){
			if(primaryCss != null && !primaryCss.equals("")){
				actionLabel.setStylePrimaryName(primaryCss);
			}
		} else if(widgetType == ActionWidgetType.LINK){
			if(primaryCss != null && !primaryCss.equals("")){
				actionLink.setStylePrimaryName(primaryCss);
			}
		} else if(widgetType == ActionWidgetType.BUTTON){
			if(primaryCss != null && !primaryCss.equals("")){
				actionButton.setStylePrimaryName(primaryCss);
			}
		}
	}


	/********* Setters Getters *****************/

	public ActionWidgetType getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(ActionWidgetType widgetType) {
		this.widgetType = widgetType;
	}

	/************* Overridden method here ******************/
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event != null){
			AppUtils.EVENT_BUS.fireEvent(event);
		}
	}
	
}
