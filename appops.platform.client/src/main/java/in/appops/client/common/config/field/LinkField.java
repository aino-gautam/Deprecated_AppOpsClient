package in.appops.client.common.config.field;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * Field class to represent a {@link Hyperlink} or {@link Anchor}
 * @author pallavi@ensarm.com
 *
 */
public class LinkField extends BaseField implements ValueChangeHandler<String> ,ClickHandler{

	private Anchor anchor;
	private Hyperlink hyperLink;
	
	public LinkField(){

	}
	
	/**
	 * creates the field UI
	 */
	@Override
	public void create(){
		getBasePanel().add(getWidget(),DockPanel.CENTER);
	}

	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)) {
			hyperLink.setText(getValue().toString());
		}else 
			anchor.setText(getValue().toString());
	}
	

	@Override
	public void configure() {
		
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)){
			hyperLink = new Hyperlink();
			hyperLink.setText(getDisplayText());
			if(getTargetHistoryToken()!=null)
				hyperLink.setTargetHistoryToken(getTargetHistoryToken());
			
		}else {
			anchor = new Anchor();
			anchor.setText(getDisplayText());
			anchor.setHref(getHref());
			anchor.setTarget(getTargetFrame());
			
		}
		
		if(getBaseFieldPrimCss()!= null)
			getWidget().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			getWidget().addStyleName(getBaseFieldCss());
		if(getLinkTitle()!=null)
			getWidget().setTitle(getLinkTitle());
		
	}
	
	/**
	 * clears the field .
	 */
	@Override
	public void clear() {
		
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)){
			hyperLink.setText("");
		}else 
			anchor.setText("");
	}
	
	/**
	 * Overriden method from BaseField returns the converted field value.
	 */
	@Override
	public void setValue(Object value) {
		
		super.setValue(value);
		clear();
		setFieldValue(value.toString());
		
	}
	
	/**
	 * Overriden method from BaseField returns the converted field value.
	 */
	@Override
	public Object getValue() {
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)){
			return hyperLink.getText();
		}else 
			return anchor.getText();
	}
	
	/**************** Configuration method **********************/
	/**
	 * Method return the type of the link to use . Defaults to anchor.  
	 * @return
	 */
	private String getLinkType() {
		String linkType = LinkFieldConstant.LNKTYPE_ANCHOR;
		if (getConfigurationValue(LinkFieldConstant.LNK_TYPE) != null) {
			linkType = getConfigurationValue(LinkFieldConstant.LNK_TYPE).toString();
		}
		return linkType;
	}
	
	/**
	 * Method return the display text of the link.  
	 * @return
	 */
	private String getDisplayText() {
		String linkType = "";
		if (getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT) != null) {
			linkType = getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT).toString();
		}
		return linkType;
	}
	
	/**
	 * Method return the title text set in the configuration.  
	 * @return
	 */
	private String getLinkTitle() {
		String linkType = "";
		if (getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT) != null) {
			linkType = getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT).toString();
		}
		return linkType;
	}
	
	/**
	 * Method return the historyToken.
	 * @return
	 */
	private String getTargetHistoryToken() {
		String historyToken = null;
		if (getConfigurationValue(LinkFieldConstant.LNK_HISTORYTOKEN) != null) {
			historyToken = getConfigurationValue(LinkFieldConstant.LNK_HISTORYTOKEN).toString();
		}
		return historyToken;
	}
	
	/**
	 * Method return the href link to which it will redirect.
	 * @return
	 */
	private String getHref() {
		String href = null;
		if (getConfigurationValue(LinkFieldConstant.LNK_HREF) != null) {
			href = getConfigurationValue(LinkFieldConstant.LNK_HREF).toString();
		}
		return href;
	}
	
	/**
	 * Method return target frame for hyperlink. Defaults to _blank.
	 * @return
	 */
	private String getTargetFrame() {
		String targetFrm = "_blank";
		if (getConfigurationValue(LinkFieldConstant.LNK_TARGET_FRAME) != null) {
			targetFrm = getConfigurationValue(LinkFieldConstant.LNK_TARGET_FRAME).toString();
		}
		return targetFrm;
	}
	/**************************   *******************************/
	
	public Widget getWidget() {
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)) {
			return hyperLink;
		} else 
			return anchor;
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String historyToken = event.getValue();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		
	}
	
	public interface LinkFieldConstant extends BaseFieldConstant{
		
		/** Specifies the type of the linkField **/
		public static final String LNK_TYPE = "LinkType";
		
		public static final String LNKTYPE_HYPERLINK = "Hyperlink";
		
		public static final String LNKTYPE_ANCHOR = "Anchor";
		
		/** Specifies display text for link **/
		public static final String LNK_DISPLAYTEXT = "displayText";
		
		/** Specifies the title to display in the tooltip  **/
		public static final String LNK_TITLE = "title";
		
		/** Specifies the history token if link type is hyperlink**/
		public static final String LNK_HISTORYTOKEN = "historyToken";
		
		/** Specifies the href if link type is anchor**/
		public static final String LNK_HREF = "href";
		
		/** Specifies the target frame for anchor **/
		public static final String LNK_TARGET_FRAME = "targetFrame";
		
	}
	
	
}
