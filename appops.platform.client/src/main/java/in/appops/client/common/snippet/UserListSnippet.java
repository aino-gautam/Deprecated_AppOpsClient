package in.appops.client.common.snippet;

import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LabelField.LabelFieldConstant;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class UserListSnippet extends RowSnippet {
	
	private FocusPanel basePanel = new FocusPanel();
	private HorizontalPanel userDetailsPanel;
	private FlowPanel userNameFlowPanel;
	private LabelField userName;
	private ImageField imageField;
	private Entity entity;
	private boolean isSelected;
	
	public UserListSnippet() {
		
	}
	
	public UserListSnippet(boolean isSelectionAllowed) {
		basePanel.setStylePrimaryName("contactSnippetBasePanel");
	}
    @Override
	public void initialize() {
    	super.initialize();
		userDetailsPanel = new HorizontalPanel();
		userNameFlowPanel = new FlowPanel();
		userName = new LabelField();
		imageField = new ImageField();
		
		userNameFlowPanel.add(userName);
		userNameFlowPanel.setWidth("120px");
		userDetailsPanel.add(userNameFlowPanel);
		userDetailsPanel.add(imageField);
		basePanel.add(userDetailsPanel);
		userDetailsPanel.setWidth("100%");
		basePanel.addClickHandler(this);
		
		BlobDownloader downloader = new BlobDownloader();
		
		String url = null;
		if(entity.getPropertyByName(ContactConstant.IMGBLOBID)!=null){
			String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
			url = downloader.getIconDownloadURL(blobId);
		}else{
			url = "images/default_Icon.png";
		}
		
		Configuration imageConfig = getImageFieldConfiguration(url, "defaultIcon");
		Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
		setConfigurationForFields(labelConfig,imageConfig);
		
		if(entity!=null){
			if(entity.getPropertyByName(ContactConstant.NAME)!=null){
				userName.setFieldValue(entity.getPropertyByName(ContactConstant.NAME).toString());
			}else{
				userName.setFieldValue(entity.getPropertyByName(ContactConstant.EMAILID).toString());
			}
			userName.reset();
		}
		
		add(basePanel,DockPanel.CENTER);
    }
	
	public FocusPanel getBasePanel() {
		return basePanel;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}


	public void setConfigurationForFields(Configuration labelConfig, Configuration imageConfig) {

		try {
			userName.setConfiguration(labelConfig);
			userName.create();
			imageField.setConfiguration(imageConfig);
            imageField.create();
            imageField.addErrorHandler(new ErrorHandler() {
				
				@Override
				public void onError(ErrorEvent event) {
					imageField.setUrl("images/default_Icon.png");
				}
			});
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}


}
