package in.appops.client.common.snippet;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceConstants;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceTypeConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BoxSnippet extends Composite implements Snippet,ClickHandler{
	
	private ImageField icon;
	private LabelField entityTitle;
	private HorizontalPanel basePanel= new HorizontalPanel();
	private Entity entity;
	private String type;
	private Configuration configuration;	
	
	public BoxSnippet() {
		initWidget(basePanel);
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
	
	@Override
	public void initialize(){
		String blobId = null;
		
		if(getEntity().getProperty(SpaceConstants.BANNERBLOBID) != null) {
			blobId = getEntity().getProperty(SpaceConstants.BANNERBLOBID).getValue().toString();
		}

		icon = new ImageField();
		
		BlobDownloader blobDownloader = new BlobDownloader();
		if(blobId != null)
			icon.setConfiguration(getIconConfig(blobDownloader.getIconDownloadURL(blobId)));
		else
			icon.setConfiguration(getIconConfig("images/NoImage.gif"));
		try {
			icon.create();
		} catch (AppOpsException e1) {
			e1.printStackTrace();
		}
		icon.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				icon.setUrl("images/NoImage.gif");
			}
		});
		entityTitle = new LabelField();
		entityTitle.setFieldValue(getEntity().getProperty("name").getValue().toString());
		entityTitle.setConfiguration(getLabelFieldConfiguration(true, "boxSnippetEntityTitle", null, null));
		entityTitle.create();
		
		//entityTitle.addClickHandler(this);
		
		VerticalPanel spaceDetails = new VerticalPanel();
		spaceDetails.add(entityTitle);
		
		LabelField spaceTypeLbl = new LabelField();
		Entity spaceTypeEnt = getEntity().getPropertyByName(SpaceConstants.SPACETYPEID);
		if(spaceTypeEnt!=null){
			String spaceType = spaceTypeEnt.getPropertyByName(SpaceTypeConstants.NAME).toString();
			spaceTypeLbl.setFieldValue(spaceType);
			spaceTypeLbl.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			
			spaceTypeLbl.create();
			
			
			spaceDetails.add(spaceTypeLbl);
		}
		
		basePanel.add(icon);
		basePanel.add(spaceDetails);
		
		basePanel.setSpacing(5);
		
		//spaceTypeLbl.addClickHandler(this);
		
		basePanel.setCellVerticalAlignment(spaceTypeLbl, HasVerticalAlignment.ALIGN_MIDDLE);
		basePanel.setCellHorizontalAlignment(icon, HasHorizontalAlignment.ALIGN_LEFT);
		
		basePanel.setStylePrimaryName("boxSnippetEntityBasePanel");
	}
	
	private Configuration getIconConfig(String url) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		configuration.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, "noImageAvailable");
		return configuration;
	}

	private Configuration getLabelFieldConfiguration(boolean allowWordWrap,
		String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onClick(ClickEvent event) {
		
		ActionEvent actionEvent  = new ActionEvent();
		actionEvent.setEventType(ActionEvent.LOADENTITYHOME);
		actionEvent.setEventData(getEntity());
		AppUtils.EVENT_BUS.fireEvent(actionEvent);
		
		
	}


}
