package in.appops.client.common.snippet;

import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.media.constant.MediaConstant;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ServiceIconSnippet extends Snippet {

	private VerticalPanel basePanel = new VerticalPanel();
	private Image serviceIcon;
	private LabelField serviceEntityTitle;
	private HorizontalPanel labelPanel= new HorizontalPanel();
	
	
	public ServiceIconSnippet() {
		
		initWidget(basePanel);
//		initialize();
	}
	
	
	public void initialize(){
		
		String bloId = getEntity().getProperty("").getValue().toString();
		BlobDownloader blobDownloader = new BlobDownloader();
		serviceIcon = new Image(blobDownloader.getImageDownloadURL(bloId));
		
		serviceEntityTitle = new LabelField();
		serviceEntityTitle.setFieldValue("Restaurant");
		serviceEntityTitle.setConfiguration(getLabelFieldConfiguration(true, "serviceEntityTitle", null, null));
		try {
			serviceEntityTitle.createField();
		} catch (AppOpsException e) {
	
			e.printStackTrace();
		}
		serviceIcon.setStylePrimaryName("serviceEntityIcon");
		labelPanel.setStylePrimaryName("serviceEntityTitlePanel");
		basePanel.setStylePrimaryName("serviceEntityBasePanel");
		basePanel.add(serviceIcon);
		labelPanel.add(serviceEntityTitle);
		basePanel.add(labelPanel);
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap,
			String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP,allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS,primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS,secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}

}
