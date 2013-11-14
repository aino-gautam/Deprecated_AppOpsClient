package in.appops.client.common.snippet;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.BlobDownloader;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ActionBoxSnippet extends Composite implements Snippet, ClickHandler{

	private FocusPanel basePanel;
	private Entity spaceActionEntity;
	private String type;
	private Configuration configuration;
	private ActionContext actionContext;
	
	/**
	 * constructor
	 */
	public ActionBoxSnippet(){
		basePanel = new FocusPanel();
		basePanel.addClickHandler(this);
		initWidget(basePanel);
	}
	
	/**
	 * initializes the widget
	 */
	@Override
	public void initialize() {
		ImageField snippetImage = new ImageField();
		LabelField snippetLabel = new LabelField();
		HorizontalPanel snippetPanel = new HorizontalPanel();
		
		String url = "";
		
		if(getEntity().getProperty("blobId") != null ){
			BlobDownloader blobDownloader = new BlobDownloader();
			String blobId = getEntity().getProperty("blobId").getValue().toString();
			url = blobDownloader.getImageDownloadURL(blobId);
		}
		
		Configuration imageConfig = getImageFieldConfiguration(url, "boxSnippetImage");
		snippetImage.setConfiguration(imageConfig);
		
		Configuration labelConfig = getLabelFieldConfiguration(true, "boxSnippetLabel", null, null);
		snippetLabel.setConfiguration(labelConfig);
		snippetLabel.setFieldValue(getEntity().getProperty("name").getValue().toString());
		try {
			snippetImage.create();
			snippetLabel.configure();
			snippetLabel.create();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		snippetPanel.add(snippetImage);
		snippetPanel.add(snippetLabel);
		
		snippetPanel.setCellWidth(snippetImage, "25%");
		snippetPanel.setCellWidth(snippetLabel, "75%");
		
		snippetPanel.setCellVerticalAlignment(snippetLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		snippetPanel.setStylePrimaryName("boxSnippetPanel");
		
		basePanel.add(snippetPanel);
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	
	@Override
	public Entity getEntity() {
		return this.spaceActionEntity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.spaceActionEntity = entity;
	}

	@Override
	public String getType() {
		return this.type;
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
		return this.configuration;
	}

	@Override
	public ActionContext getActionContext() {
		return this.actionContext;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		this.actionContext = actionContext;
	}

	@Override
	public void onClick(ClickEvent event) {
		ActionEvent actionEvent = getActionEvent();
		AppUtils.EVENT_BUS.fireEvent(actionEvent);
		
	}
	
	/**
	 * formulates the action event with the widget name, converts to json and sets as the action event data, sets the action type as transformwidget
	 * @return
	 */
	private ActionEvent getActionEvent() {
		InitiateActionContext context = new InitiateActionContext();
		context.setType(new MetaType("ActionContext"));
		
		context.setSpace(AppEnviornment.getCurrentSpace());
		//context.setAction(getEntity().getProperty("name").getValue().toString());
		context.setActionEntity(getEntity());
		JSONObject token = EntityToJsonClientConvertor.createJsonFromEntity(context);
		
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setEventType(ActionEvent.TRANSFORMWIDGET);			
		actionEvent.setEventData(token.toString());
		
		return actionEvent;
	}

}
