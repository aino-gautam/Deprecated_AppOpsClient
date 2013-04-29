package in.appops.client.common.snippet;

import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ServiceConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class BoxSnippet extends Composite implements Snippet,ClickHandler{
	
	private Image icon;
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
					
		//String blobId = getEntity().getProperty("bannerBlobId").getValue().toString();
		BlobDownloader blobDownloader = new BlobDownloader();
		icon = new Image(blobDownloader.getIconDownloadURL("irqSN52SzHwHksn9NQFKxI4fFpnxXb3xG8L%2FWWvfrys%3D"));
		
		entityTitle = new LabelField();
		entityTitle.setFieldValue(getEntity().getProperty("name").getValue().toString());
		entityTitle.setConfiguration(getLabelFieldConfiguration(true, "boxSnippetEntityTitle", null, null));
		try {
			entityTitle.createField();
		} catch (AppOpsException e) {
	
			e.printStackTrace();
		}
		
		basePanel.add(icon);
		basePanel.add(entityTitle);
		
		entityTitle.addClickHandler(this);
		
		basePanel.setCellVerticalAlignment(entityTitle, HasVerticalAlignment.ALIGN_MIDDLE);
		basePanel.setCellHorizontalAlignment(icon, HasHorizontalAlignment.ALIGN_LEFT);
		
		basePanel.setStylePrimaryName("boxSnippetEntityBasePanel");
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
