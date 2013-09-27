package in.appops.client.common.config.breadcrumb;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LinkField;
import in.appops.client.common.config.field.LinkField.LinkFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BreadcrumbSnippetField implements FieldEventHandler{

	private Entity actionEntity;
	private LinkField hyperLinkField= new LinkField();
	private ImageField image= new ImageField();
	private HorizontalPanel hPanel= new HorizontalPanel();
	private String label;
	private HandlerRegistration fieldEventHandler= null;

	private Logger logger = Logger.getLogger(getClass().getName());

	public BreadcrumbSnippetField(String label) {
		this.label=label;
		if(fieldEventHandler ==null)
			fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	public HorizontalPanel create(){

		image.setConfiguration(getImageConfiguration());
		image.configure();
		image.create();

		hyperLinkField.setConfiguration(getHyperLinkConfiguration(label));
		hyperLinkField.configure();
		hyperLinkField.create();

		hPanel.add(hyperLinkField);
		hPanel.add(image);
		hPanel.setSpacing(2);
		hPanel.setStylePrimaryName("breadcrumbComponent");

		return hPanel;
	}

	private Configuration getHyperLinkConfiguration(String label) {
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[BreadcrumbSnippetField]:: In getHyperLinkConfiguration  method ");
			configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_HYPERLINK);
			configuration.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");
			configuration.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, label);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[BreadcrumbSnippetField]::Exception In getHyperLinkConfiguration  method :"+e);
		}
		return configuration;

	}

	private Configuration getImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			logger.log(Level.INFO,"[BreadcrumbSnippetField]:: In getImageConfiguration  method ");
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/breadcrumArrowUnfilled.png");

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[BreadcrumbSnippetField]::Exception In getImageConfiguration  method :"+e);
		}
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventSource() instanceof ImageField){
				if(event.getEventType() == FieldEvent.CLICKED){
					ImageField source = (ImageField) event.getEventSource();
					showBreadcrumbPopup(source.getAbsoluteLeft(), source.getAbsoluteTop()+source.getOffsetHeight());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showBreadcrumbPopup(int left, int top){
		
		
		EntityList childlist= actionEntity.getPropertyByName("ChildList");
		
		BreadcrumbPopUp popup=new BreadcrumbPopUp(childlist);
		popup.setPopupPosition(left, top);
		popup.createUI();
		popup.show();
	}

	public void setActionEntity(Entity entity1) {
		actionEntity=entity1;
	}


}
