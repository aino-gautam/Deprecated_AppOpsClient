package in.appops.client.common.config.field;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
* Field class to represent a {@link Hyperlink} or {@link Anchor}
* @author pallavi@ensarm.com
*
*<p>
<h3>Configuration</h3>
<a href="LinkField.LinkFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>
LinkField hyperLinkField = new LinkField();<br>
Configuration conf = new Configuration();<br>
conf.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_HYPERLINK);<br>
conf.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");<br>
conf.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Hyperlink");<br>
conf.setPropertyByName(LinkFieldConstant.LNK_HISTORYTOKEN, "historyToken");<br>
hyperLinkField.setConfiguration(conf);<br>
hyperLinkField.configure();<br>
hyperLinkField.create();<br>

</p>*/
public class LinkField extends BaseField implements ClickHandler{

	private Anchor anchor;
	private Hyperlink hyperLink;
	
	public LinkField(){

	}
	
	
	/******************************** BaseField Overriden methods ****************************************/
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
			if(getHref()!=null)
			anchor.setHref(getHref());
			if(getTargetFrame()!=null)
				anchor.setTarget(getTargetFrame());
			anchor.addClickHandler(this);
		}
		
		if(getBaseFieldPrimCss()!= null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			getBasePanel().addStyleName(getBaseFieldCss());
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
	 * Overriden method from BaseField sets the converted value.
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
	
	
	/**
	 * Overriden method from BaseField set the text to hyperlink or anchor.
	 */
	@Override
	public void setFieldValue(String value) {
		
		clear();
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)){
			hyperLink.setText(value);
		}else 
			anchor.setText(value);
		
	}
	
	
	/************************** Configuration method **********************/
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
		String displayTxt = "";
		if (getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT) != null) {
			displayTxt = getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT).toString();
		}
		return displayTxt;
	}
	
	/**
	 * Method return the title text set in the configuration.  
	 * @return
	 */
	private String getLinkTitle() {
		String linkTitle = null;
		if (getConfigurationValue(LinkFieldConstant.LNK_TITLE) != null) {
			linkTitle = getConfigurationValue(LinkFieldConstant.LNK_TITLE).toString();
		}
		return linkTitle;
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
		String targetFrm = null;
		if (getConfigurationValue(LinkFieldConstant.LNK_TARGET_FRAME) != null) {
			targetFrm = getConfigurationValue(LinkFieldConstant.LNK_TARGET_FRAME).toString();
		}
		return targetFrm;
	}
	
	
	/************************************** *******************************/
	
	public Widget getWidget() {
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)) {
			return hyperLink;
		} else 
			return anchor;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		//TODO fire events 
		Window.alert("Anchor events");
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
