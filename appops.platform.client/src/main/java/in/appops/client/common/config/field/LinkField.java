package in.appops.client.common.config.field;


import java.util.logging.Level;
import java.util.logging.Logger;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
	private Logger logger = Logger.getLogger(getClass().getName());
	private HandlerRegistration clickHandler  =  null;

	public LinkField(){

	}
	
	/************************************************************************/
	/**
	 * creates the field UI
	 */
	@Override
	public void create(){
		try {
			getBasePanel().add(getWidget(),DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in create method :"+e);
		}
	}

	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		
		try {
			if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)) {
				hyperLink.setText(getValue().toString());
			}else 
				anchor.setText(getValue().toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in reset method :"+e);
		}
	}
	
    /**
     * Method configures the link field.
     */
	@Override
	public void configure() {
		try {
			if (getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)) {
				hyperLink = new Hyperlink();
				hyperLink.setText(getDisplayText());

				if (getTargetHistoryToken() != null)
					hyperLink.setTargetHistoryToken(getTargetHistoryToken());
			} else {
				anchor = new Anchor();
				anchor.setText(getDisplayText());
				if (getHref() != null)
					anchor.setHref(getHref());
				if (getTargetFrame() != null)
					anchor.setTarget(getTargetFrame());

				clickHandler = anchor.addClickHandler(this);
			}

			if (getBaseFieldPrimCss() != null)
				getWidget().setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				getWidget().addStyleName(getBaseFieldDependentCss());
			if (getLinkTitle() != null)
				getWidget().setTitle(getLinkTitle());

			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LinkField] ::Exception in configure method :" + e);
		}
	}
	
	/**
	 * clears the field .
	 */
	@Override
	public void clear() {
		
		try {
			if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)){
				hyperLink.setText("");
			}else 
				anchor.setText("");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in clear method :"+e);
		}
	}
	
	/**
	 * Overriden method from BaseField sets the converted value.
	 */
	@Override
	public void setValue(Object value) {
		
		try {
			super.setValue(value);
			clear();
			setFieldValue(value.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in setValue method :"+e);
		}
		
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
		
		try {
			clear();
			if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)){
				hyperLink.setText(value);
			}else 
				anchor.setText(value);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in setFieldValue method :"+e);
		}
		
	}
	
	/**
	 * Method removed registered handlers from field
	 */
	@Override
	public void removeRegisteredHandlers() {
		
		if(clickHandler!=null)
			clickHandler.removeHandler();
	}
	
	/************************************************/
	
	/**
	 * Method return the type of the link to use . Defaults to anchor.  
	 * @return
	 */
	private String getLinkType() {
		String linkType = LinkFieldConstant.LNKTYPE_ANCHOR;
		try {
			if (getConfigurationValue(LinkFieldConstant.LNK_TYPE) != null) {
				linkType = getConfigurationValue(LinkFieldConstant.LNK_TYPE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in getLinkType method :"+e);
		}
		return linkType;
	}
	
	/**
	 * Method return the display text of the link.  
	 * @return
	 */
	private String getDisplayText() {
		String displayTxt = "";
		try {
			if (getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT) != null) {
				displayTxt = getConfigurationValue(LinkFieldConstant.LNK_DISPLAYTEXT).toString();
			}
			else{
				if(getEntity()!=null){
					if(getConfigurationValue(LinkFieldConstant.BF_BINDPROP) != null)
						if(getEntity().getPropertyByName( getConfigurationValue(LinkFieldConstant.BF_BINDPROP).toString()) != null)
							displayTxt = getEntity().getPropertyByName( getConfigurationValue(LinkFieldConstant.BF_BINDPROP).toString()).toString();
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in getDisplayText method :"+e);
		}
		return displayTxt;
	}
	
	/**
	 * Method return the title text set in the configuration.  
	 * @return
	 */
	private String getLinkTitle() {
		String linkTitle = null;
		try {
			if (getConfigurationValue(LinkFieldConstant.LNK_TITLE) != null) {
				linkTitle = getConfigurationValue(LinkFieldConstant.LNK_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in getLinkTitle method :"+e);
		}
		return linkTitle;
	}
	
	/**
	 * Method return the historyToken.
	 * @return
	 */
	private String getTargetHistoryToken() {
		String historyToken = null;
		try {
			if (getConfigurationValue(LinkFieldConstant.LNK_HISTORYTOKEN) != null) {
				historyToken = getConfigurationValue(LinkFieldConstant.LNK_HISTORYTOKEN).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in getTargetHistoryToken method :"+e);
		}
		return historyToken;
	}
	
	/**
	 * Method return the href link to which it will redirect.
	 * @return
	 */
	private String getHref() {
		String href = null;
		try {
			if (getConfigurationValue(LinkFieldConstant.LNK_HREF) != null) {
				href = getConfigurationValue(LinkFieldConstant.LNK_HREF).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in getHref method :"+e);
		}
		return href;
	}
	
	/**
	 * Method return target frame for hyperlink. Defaults to _blank.
	 * @return
	 */
	private String getTargetFrame() {
		String targetFrm = null;
		try {
			if (getConfigurationValue(LinkFieldConstant.LNK_TARGET_FRAME) != null) {
				targetFrm = getConfigurationValue(LinkFieldConstant.LNK_TARGET_FRAME).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in getTargetFrame method :"+e);
		}
		return targetFrm;
	}
	
	
	/*********************************************************************/
	
	public Widget getWidget() {
		if(getLinkType().equalsIgnoreCase(LinkFieldConstant.LNKTYPE_HYPERLINK)) {
			return hyperLink;
		} else 
			return anchor;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		
		try {
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(FieldEvent.CLICKED);
			fieldEvent.setEventSource(this);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[LinkField] ::Exception in onClick method :"+e);
		}
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
