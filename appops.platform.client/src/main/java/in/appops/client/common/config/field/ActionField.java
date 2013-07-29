package in.appops.client.common.config.field;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;


public class ActionField extends BaseField implements ClickHandler{
	
	public interface ActionFieldConstant extends BaseFieldConstant {
		String MODE = "mode";
		String AF_TOKENVAL = "tokenValue";
		String AF_PAGE = "page";
		int PAGE = 2;
		int TOKEN = 1;
		String ACSS = "acss";
		String AF_WIDGETTYPE = "widgetType";
		int AF_ANCHOR = 1;
		int AF_BUTTON = 2;
		int AF_LABEL = 3;
	}

	
	private Anchor actionLink;
	private Button actionButton; 
	private Label actionLabel; 
	
	/************************** All Configuration Methods here *****************************************/
	@Override
	protected void initialize() {
		super.initialize();
		actionLink = new Anchor();
	}
	
	protected String getTokenValue() {
		String type = ActionFieldConstant.AF_TOKENVAL;
		if(getConfigurationValue(ActionFieldConstant.AF_TOKENVAL) != null) {
			type = getConfigurationValue(ActionFieldConstant.AF_TOKENVAL).toString();;
		}
		return type;
	}
	
	@Override
	protected String getBaseFieldPrimCss() {
		String primaryCss = super.getBaseFieldPrimCss();
		
		if(primaryCss == null) {
			return "appops-actionBaseField";
		}
		return primaryCss;
	}
	
	protected String getBaseFieldDependentCss() {
		String depCss = super.getBaseFieldDependentCss();
		if(depCss == null) {
			return "appops-SpinnerFieldDependent";
		}	
		return depCss;
	}
	
	protected String getFieldPrimCss() {
		String primaryCss = "appops-actionField";
		if(getConfigurationValue(ActionFieldConstant.ACSS) != null) {
			primaryCss = getConfigurationValue(ActionFieldConstant.ACSS).toString();
		}
		return primaryCss;
	}
	
	private int getMode() {
		if(getConfigurationValue(ActionFieldConstant.MODE) != null) {
			return Integer.parseInt(getConfigurationValue(ActionFieldConstant.MODE).toString());
		}
		return 1;
	}


	private String getPageValue() {
		String page = null;
		if(getConfigurationValue(ActionFieldConstant.AF_PAGE) != null) {
			page = getConfigurationValue(ActionFieldConstant.AF_PAGE).toString();
		}
		return page;
	}
	
	
	private int getWidgetType() {
		if(getConfigurationValue(ActionFieldConstant.AF_WIDGETTYPE) != null) {
			return Integer.parseInt(getConfigurationValue(ActionFieldConstant.AF_WIDGETTYPE).toString());
		}
		return 1;
	}

	
	/************************ End of Configs ***************************/
	
	
	@Override
	public void configure() {
		super.configure();
		
		int widgetType = getWidgetType();
		if(widgetType == ActionFieldConstant.AF_LABEL){
			actionLabel = new Label();
		} else if(widgetType == ActionFieldConstant.AF_ANCHOR){
			actionLink = new Anchor();
		} else if(widgetType == ActionFieldConstant.AF_BUTTON){
			actionButton = new Button();
		}	
		
		if(getFieldPrimCss()!=null) {
			if(widgetType == ActionFieldConstant.AF_LABEL){
				actionLabel.setStylePrimaryName(getFieldPrimCss());
			} else if(widgetType == ActionFieldConstant.AF_ANCHOR){
				actionLink.setStylePrimaryName(getFieldPrimCss());
			} else if(widgetType == ActionFieldConstant.AF_BUTTON){
				actionButton.setStylePrimaryName(getFieldPrimCss());
			}
		}
		
		if(getDefaultValue() == null) {
			setValue("");
		} else {
			setValue(getDefaultValue().toString());
		}
	}
	


	@Override
	public void setValue(Object value) {
		super.setValue(value);
		
		int widgetType = getWidgetType();
		if(widgetType == ActionFieldConstant.AF_LABEL){
			actionLabel.setText(value.toString());
		} else if(widgetType == ActionFieldConstant.AF_ANCHOR){
			actionLink.setText(value.toString());
		} else if(widgetType == ActionFieldConstant.AF_BUTTON){
			actionButton.setText(value.toString());
		}	
	}
	
	@Override
	public void create() {
		super.create();
		
		int widgetType = getWidgetType();
		if(widgetType == ActionFieldConstant.AF_LABEL){
			basePanel.add(actionLabel, DockPanel.CENTER);
			actionLabel.addClickHandler(this);
		} else if(widgetType == ActionFieldConstant.AF_ANCHOR){
			basePanel.add(actionLink, DockPanel.CENTER);
			actionLink.addClickHandler(this);
		} else if(widgetType == ActionFieldConstant.AF_BUTTON){
			basePanel.add(actionButton, DockPanel.CENTER);
			actionButton.addClickHandler(this);
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if(getMode() == ActionFieldConstant.TOKEN) {
			String token = getTokenValue();
		
			if(bindId != null) {
				token = token + Long.toString(bindId);
			} 
			History.newItem(token, true);
		} else if(getMode() == ActionFieldConstant.PAGE) {
			String page = getPageValue(); 
			String moduleUrl = GWT.getHostPageBaseURL();
			String pageUrl = moduleUrl + page; // + "?gwt.codesvr=127.0.0.1:9997";
			Window.open(pageUrl, "_self", "");
		}
	}
	
}