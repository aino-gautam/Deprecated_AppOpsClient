package in.appops.client.common.components;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author nitish@ensarm.com
 * TODO - This will enhanced/changed as required.
 */
public class ActionWidget extends Composite implements Configurable{
	private FocusPanel basePanel;
	private HorizontalPanel actionWidgetPanel;
	private Label actionLabel;
	private Anchor actionLink;
	private Button actionButton;
	private ImageField actionImage;
	private Entity actionEntity;
	
	private Configuration configuration;
	private ActionWidgetType widgetType;

	/******** ActionWidget Related Constants **********/
	public enum ActionWidgetType{
		LABEL, LINK, BUTTON
	};

	public enum ActionWidgetConfiguration{
		PRIMARY_CSS("primaryCss"),
		DEPEDENT_CSS("dependentcss");
		
		private final String value;       

	    private ActionWidgetConfiguration(String value) {
	        this.value= value;
	    }

	    public String toString(){
	       return value;
	    }
	};
	
	/************ Constructors *************************/
	
	public ActionWidget(ActionWidgetType widgetType) {
		this.widgetType = widgetType;
		
		intializeWidget();
		initWidget(basePanel);
	}

	/*********** Member Methods  *************/
	private void intializeWidget() {
		basePanel = new FocusPanel();
		actionWidgetPanel = new HorizontalPanel();
		actionImage =  new ImageField();
		if(widgetType == ActionWidgetType.LABEL){
			actionLabel = new Label();
		} else if(widgetType == ActionWidgetType.LINK){
			actionLink = new Anchor();
		} else if(widgetType == ActionWidgetType.BUTTON){
			actionButton = new Button();
		}		
	}

	public void createUi() {
		try{
			basePanel.add(actionWidgetPanel);
			
			if(widgetType == ActionWidgetType.LABEL){
				actionWidgetPanel.add(actionImage);
			}
			Widget actionWidget = (widgetType == ActionWidgetType.LABEL ? actionLabel : (widgetType == ActionWidgetType.LINK ? actionLink : actionButton));
			actionWidgetPanel.add(actionWidget);
			actionWidgetPanel.setCellVerticalAlignment(actionWidget, HasVerticalAlignment.ALIGN_MIDDLE);

			/********** You can set here any required things like CSS etc from Configuration ******/
			if(configuration != null){
				applyConfiguration();
			}
			if(widgetType == ActionWidgetType.LABEL){
				actionImage.createField();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setWidgetText(String text) {
		if(getWidgetType() == ActionWidgetType.LABEL){
			actionLabel.setText(text);
		} else if(getWidgetType() == ActionWidgetType.LINK){
			actionLink.setText(text);
		} else if(getWidgetType() == ActionWidgetType.BUTTON){
			actionButton.setText(text);
		}
	}
	
	public String getWidgetText() {
		if(getWidgetType() == ActionWidgetType.LABEL){
			return actionLabel.getText();
		} else if(getWidgetType() == ActionWidgetType.LINK){
			return actionLink.getText();
		} else if(getWidgetType() == ActionWidgetType.BUTTON){
			return actionButton.getText();
		}
		return null;
	}

	public void addClickHandler(ClickHandler handler) {
		basePanel.addClickHandler(handler);
	}
	
	private void applyConfiguration() {
		//TODO Apply Configurations here

		String primaryCss = configuration.getPropertyByName(ActionWidgetConfiguration.PRIMARY_CSS.toString()).toString();
		//String dependentCss = configuration.getPropertyByName(ActionWidgetConfiguration.DEPEDENT_CSS.toString()).toString();
		
		if(widgetType == ActionWidgetType.LABEL){
			if(primaryCss != null && !primaryCss.equals("")){
				actionLabel.setStylePrimaryName(primaryCss);
			}
		} else if(widgetType == ActionWidgetType.LINK){
			if(primaryCss != null && !primaryCss.equals("")){
				actionLink.setStylePrimaryName(primaryCss);
			}
		} else if(widgetType == ActionWidgetType.BUTTON){
			if(primaryCss != null && !primaryCss.equals("")){
				actionButton.setStylePrimaryName(primaryCss);
			}
		}
		
		if(widgetType == ActionWidgetType.LABEL){
			String url = "";
			if(actionEntity.getProperty("blobId") != null ){
				BlobDownloader blobDownloader = new BlobDownloader();
				String blobId = actionEntity.getProperty("blobId").getValue().toString();
				url = blobDownloader.getImageDownloadURL(blobId);
			}
			Configuration imageConfig = getImageFieldConfiguration(url, "appops-intelliThoughtActionImage");
			actionImage.setConfiguration(imageConfig);
			basePanel.setStylePrimaryName("appops-intelliThoughtActionPanel");
		}

	}


	/********* Setters Getters *****************/

	public ActionWidgetType getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(ActionWidgetType widgetType) {
		this.widgetType = widgetType;
	}

	public Entity getActionEntity() {
		return actionEntity;
	}

	public void setActionEntity(Entity actionEntity) {
		this.actionEntity = actionEntity;
	}
	
	/************* Overridden method here ******************/
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event != null){
			AppUtils.EVENT_BUS.fireEvent(event);
		}
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}
}
