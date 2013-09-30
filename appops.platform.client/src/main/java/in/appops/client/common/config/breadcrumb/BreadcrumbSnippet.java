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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Kamlakar
 * reworked by : mahesh@ensarm.com
 */
public class BreadcrumbSnippet extends Composite implements FieldEventHandler{

	private HorizontalPanel baseHp;
	private Entity actionEntity;
	
	private Configuration breadCrumbSnippetConfig;
	
	private LinkField hyperLinkField= new LinkField();
	private ImageField image= new ImageField();
	private HandlerRegistration fieldEventHandler= null;

	private Logger logger = Logger.getLogger("BreadcrumbSnippetField");

	public BreadcrumbSnippet() {
		initialize();
	}

	private void initialize() {
		try{
			baseHp= new HorizontalPanel();
			
			if(fieldEventHandler ==null)
				fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			
			initWidget(baseHp);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE,"[BreadcrumbSnippetField]::Exception In initialize  method :"+e);
		}
	}

	public void create(){

		image.setConfiguration(getImageConfiguration());
		image.configure();
		image.create();

		hyperLinkField.setConfiguration(getHyperLinkConfiguration(actionEntity.getPropertyByName("name").toString()));
		hyperLinkField.configure();
		hyperLinkField.create();

		baseHp.add(hyperLinkField);
		baseHp.add(image);
		baseHp.setStylePrimaryName("breadcrumbComponent");

	}

	private Configuration getHyperLinkConfiguration(String label) {
		Configuration configuration = new Configuration();
		try {
		//	configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_HYPERLINK);
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
					if(source.equals(image)){
						showBreadcrumbPopup(source.getAbsoluteLeft(), source.getAbsoluteTop()+source.getOffsetHeight());
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showBreadcrumbPopup(int left, int top){
		try{
		
		EntityList childlist= actionEntity.getPropertyByName("child");
		
		if(!childlist.isEmpty()){
			BreadcrumbPopup popup=new BreadcrumbPopup(childlist);
			popup.setPopupPosition(left, top);
			popup.createUI();
			popup.show();
		}
		}catch (Exception e) {
			logger.log(Level.SEVERE,"[BreadcrumbSnippetField]::Exception In showBreadcrumbPopup  method :"+e);
		}
	}

	public void setActionEntity(Entity entity1) {
		actionEntity=entity1;
	}

	/**
	 * @return the breadCrumbSnippetConfig
	 */
	public Configuration getBreadCrumbSnippetConfig() {
		return breadCrumbSnippetConfig;
	}

	/**
	 * @param breadCrumbSnippetConfig the breadCrumbSnippetConfig to set
	 */
	public void setBreadCrumbSnippetConfig(Configuration breadCrumbSnippetConfig) {
		this.breadCrumbSnippetConfig = breadCrumbSnippetConfig;
	}


}
