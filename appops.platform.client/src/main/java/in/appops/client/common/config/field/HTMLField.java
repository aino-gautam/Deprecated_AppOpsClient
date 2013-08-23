package in.appops.client.common.config.field;

import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HTMLField extends BaseField implements ClickHandler  {

	private HTML htmlField;
	
	public HTMLField() {
		htmlField = new HTML();
	}
	
	/******************************** BaseField Overriden methods ****************************************/
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	@Override
	public void create() {
		
		if(isLimitLines()) {
			VerticalPanel panel = new VerticalPanel();
			panel.add(htmlField);
			
			VerticalPanel innerPanel = new VerticalPanel();
			Widget expandWidget = getExpandWidget();
			expandWidget.addStyleName("handCursor");
			expandWidget.addStyleName("seeMoreLabelField");
			innerPanel.add(expandWidget);
			innerPanel.setStylePrimaryName(getExpandWidgetCSS());
			innerPanel.addStyleName("textAlignHTMLField");
			innerPanel.setWidth("100%");
			panel.add(innerPanel);
			
			String position = getWidgetPosition();
			if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_CENTER)) {
				expandWidget.addStyleName("centerAlignLabel");
				innerPanel.setCellHorizontalAlignment(expandWidget, HasHorizontalAlignment.ALIGN_CENTER);
			} else if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_RIGHT)) {
				expandWidget.addStyleName("rightAlignLabel");
				innerPanel.setCellHorizontalAlignment(expandWidget, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			
			panel.setWidth("100%");
			getBasePanel().add(panel, DockPanel.CENTER);
		} else {
			getBasePanel().add(htmlField, DockPanel.CENTER);
		}
	}
	
	@Override
	public void configure() {
		
		setValue(getDisplayText());
		htmlField.setTitle(getLblTitle());
		
		if(getBaseFieldPrimCss() != null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldDependentCss() != null)
			getBasePanel().addStyleName(getBaseFieldDependentCss());

		if(getHTMLFieldCss() != null)
			htmlField.setStylePrimaryName(getHTMLFieldCss());
		
	}
	
	private String getHTMLFieldCss() {
		String depCss = null;
		if(getConfigurationValue(HTMLFieldConstant.LBLFD_FCSS) != null) {
			depCss = getConfigurationValue(HTMLFieldConstant.LBLFD_FCSS).toString();
		}
		return depCss;
	}

	/**************** Configuration method **********************/
	/**
	 * Method returns diplaytext for label.
	 * @return
	 */
	private String getDisplayText() {
		
		String displayTxt = "";
		if(getConfigurationValue(HTMLFieldConstant.LBLFD_DISPLAYTXT) != null) {
			
			displayTxt = getConfigurationValue(HTMLFieldConstant.LBLFD_DISPLAYTXT).toString();
		}
		return displayTxt;
	}
	
	/**
	 * Method returns title for the label.
	 * @return
	 */
	private String getLblTitle() {
		
		String title = "";
		if(getConfigurationValue(HTMLFieldConstant.LBLFD_TITLE) != null) {
			
			title = getConfigurationValue(HTMLFieldConstant.LBLFD_TITLE).toString();
		}
		return title;
	}
	
	/**
	 * Method read the wordwrap value from configuration and return. Defaults to false;
	 * @return
	 */
	private Boolean isWordWrap() {
		
		Boolean isWordWrap = false;
		
		if(getConfigurationValue(HTMLFieldConstant.LBLFD_ISWORDWRAP) != null) {
			
			isWordWrap = (Boolean) getConfigurationValue(HTMLFieldConstant.LBLFD_ISWORDWRAP);
		}
		return isWordWrap;
	}
	
	@Override
	public void setValue(Object value) {
//		BlobDownloader blobDownloader = new BlobDownloader();
//		String url = blobDownloader.getImageDownloadURL(value.toString());
//		
//		String iframe = "<iframe class=\"" + getHTMLFieldCss() + "\"name=\"frameName1\" src=\"" + url + "\" width=\"100%\" height=\"250\"></iframe>";
		htmlField.setHTML(value.toString());
	}
	
	@Override
	public Object getValue() {
		return htmlField.getText();
	}
	
	private Boolean isLimitLines() {
		
		Boolean isLimitLines = false;
		
		if(getConfigurationValue(HTMLFieldConstant.LIMIT_HTMLFIELD_MODE) != null) {
			isLimitLines = (Boolean) getConfigurationValue(HTMLFieldConstant.LIMIT_HTMLFIELD_MODE);
		}
		return isLimitLines;
	}
	
	private Widget getExpandWidget() {
		
		String type = getWidgetType();
		String title = getWidgetTitle();
		if(type.equals(HTMLFieldConstant.EXPAND_WIDGET_LABEL)) {
			HTML widget = new HTML(title);
			widget.addClickHandler(this);
			return widget;
		} else if(type.equals(HTMLFieldConstant.EXPAND_WIDGET_ANCHOR)) {
			Anchor widget = new Anchor(title);
			widget.addClickHandler(this);
			return widget;
		}
		return null;
	}
	
	private String getWidgetTitle() {
		String title = null;
		if(getConfigurationValue(HTMLFieldConstant.EXPAND_LABEL_NAME) != null) {
			title = getConfigurationValue(HTMLFieldConstant.EXPAND_LABEL_NAME).toString();
		} else {
			title = HTMLFieldConstant.EXPAND_LABEL_DEFAULT_NAME;
		}
		return title;
	}

	private String getWidgetType() {
		String type = null;
		if(getConfigurationValue(HTMLFieldConstant.EXPAND_WIDGET_TYPE) != null) {
			type = getConfigurationValue(HTMLFieldConstant.EXPAND_WIDGET_TYPE).toString();
		} else {
			type = HTMLFieldConstant.EXPAND_WIDGET_LABEL;
		}
		return type;
	}
	
	private String getWidgetPosition() {
		String position = null;
		if(getConfigurationValue(HTMLFieldConstant.EXPAND_LABEL_POSTION_MODE) != null) {
			position = getConfigurationValue(HTMLFieldConstant.EXPAND_LABEL_POSTION_MODE).toString();
		} else {
			position = HTMLFieldConstant.EXPAND_LABEL_POSTION_RIGHT;
		}
		return position;
	}
	
	private String getExpandWidgetCSS() {
		String css = null;
		if(getConfigurationValue(HTMLFieldConstant.EXPAND_WIDGET_CSS) != null) {
			css = getConfigurationValue(HTMLFieldConstant.EXPAND_WIDGET_CSS).toString();
		}
		return css;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource() instanceof HTML) {
			HTML widget = (HTML) event.getSource();
			Widget panel = widget.getParent();
			String text = widget.getHTML();
			String position = getWidgetPosition();
			if(text.equals(HTMLFieldConstant.WIDGET_COLLAPSE)) {
				htmlField.setStylePrimaryName(getHTMLFieldCss());
				panel.setStylePrimaryName(getExpandWidgetCSS());
				
				if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_CENTER)) {
					widget.addStyleName("centerAlignLabel");
				} else if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_RIGHT)) {
					widget.addStyleName("rightAlignLabel");
				}
				
				widget.setHTML(getWidgetTitle());
			} else {
				htmlField.removeStyleName(getHTMLFieldCss());
				panel.removeStyleName(getExpandWidgetCSS());
				
				if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_CENTER)) {
					widget.removeStyleName("centerAlignLabel");
				} else if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_RIGHT)) {
					widget.removeStyleName("rightAlignLabel");
				}
				
				widget.setHTML(HTMLFieldConstant.WIDGET_COLLAPSE);
			}
		} else if(event.getSource() instanceof Anchor) {
			Anchor widget = (Anchor) event.getSource();
			Widget panel = widget.getParent();
			String text = widget.getText();
			String position = getWidgetPosition();
			if(text.equals(HTMLFieldConstant.WIDGET_COLLAPSE)) {
				htmlField.setStylePrimaryName(getHTMLFieldCss());
				panel.setStylePrimaryName(getExpandWidgetCSS());
				
				if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_CENTER)) {
					widget.addStyleName("centerAlignLabel");
				} else if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_RIGHT)) {
					widget.addStyleName("rightAlignLabel");
				}
				
				widget.setText(getWidgetTitle());
			} else {
				htmlField.removeStyleName(getHTMLFieldCss());
				panel.removeStyleName(getExpandWidgetCSS());
				
				if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_CENTER)) {
					widget.removeStyleName("centerAlignLabel");
				} else if(position.equals(HTMLFieldConstant.EXPAND_LABEL_POSTION_RIGHT)) {
					widget.removeStyleName("rightAlignLabel");
				}
				
				widget.setText(HTMLFieldConstant.WIDGET_COLLAPSE);
			}
		}
	}
	
	/********************************************************************************/
	
	public interface HTMLFieldConstant extends BaseFieldConstant{
		
		/** Specifies if wordwrap is allowed for label or not. **/
		public static final String LBLFD_ISWORDWRAP = "isWordWrap";
		
		/** Display text for label **/
		public static final String LBLFD_DISPLAYTXT = "displayTxt";
		
		/** Title text for label **/
		public static final String LBLFD_TITLE = "title";

		public static final String LBLFD_FCSS = "htmlfieldcss";
		
		String LIMIT_HTMLFIELD_MODE = "limitHtmlfieldMode";
		
		String EXPAND_WIDGET_TYPE = "expandWidgetType";
		
		String EXPAND_WIDGET_LABEL = "expandWidgetLabel";
		
		String EXPAND_WIDGET_ANCHOR = "expandWidgetAnchor";
		
		String EXPAND_LABEL_POSTION_MODE = "expandLabelPositionMode";
		
		String EXPAND_LABEL_POSTION_CENTER = "expandLabelPositionCenter";
		
		String EXPAND_LABEL_POSTION_LEFT = "expandLabelPositionLeft";
		
		String EXPAND_LABEL_POSTION_RIGHT = "expandLabelPositionRight";
		
		String EXPAND_LABEL_NAME = "expandLabelName";
		
		String EXPAND_LABEL_DEFAULT_NAME = "See more";
		
		String EXPAND_WIDGET_CSS = "expandWidgetCSS";
		
		String WIDGET_COLLAPSE = "Click to collapse";
	}
}