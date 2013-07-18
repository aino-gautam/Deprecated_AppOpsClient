package in.appops.client.common.config.field;

import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

public class HTMLField extends BaseField  {

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
		
		getBasePanel().add(htmlField, DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
		
		setValue(getDisplayText());
		htmlField.setTitle(getLblTitle());
		
		if(getBaseFieldPrimCss() != null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			getBasePanel().addStyleName(getBaseFieldCss());

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
	/********************************************************************************/
	
	public interface HTMLFieldConstant extends BaseFieldConstant{
		
		/** Specifies if wordwrap is allowed for label or not. **/
		public static final String LBLFD_ISWORDWRAP = "isWordWrap";
		
		/** Display text for label **/
		public static final String LBLFD_DISPLAYTXT = "displayTxt";
		
		/** Title text for label **/
		public static final String LBLFD_TITLE = "title";

		public static final String LBLFD_FCSS = "htmlfieldcss";
		
	}

}