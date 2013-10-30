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

import java.io.Serializable;
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
	private int levelNo;

	public static final String SNIPPETCONFIG = "snippetConfig";
	public static final String QUERYNAME = "queryName";
	public static final String ISBINDEDTOSTATICLIST = "isBindToStaticList";
	public static final String STATICLIST = "staticList";
	public static final String SHOWACTIONLINK = "showActionLink";
	public static final String BINDEDENTITY = "bindedEntity";
	public static final String ACTIONIMGCONFIG = "actionImageConfig";
	public static final String BASEPCSS = "basePrimaryCss";
	
	private final Serializable ACTNIMGCSS = "breadcrumbActnImgCss";
	private final String BRSPRIMARY_LINK_CSS = "breadcrumbLink";
	private final String BRS_LINK_BASE_CSS = "breadcrumbLinkBase";
	
	private String PROPTOBINDCHILD = "child";
	
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
		try{

			image.setConfiguration(getImageConfiguration());
			image.configure();
			image.create();
			
			hyperLinkField.setEntity(actionEntity);
			hyperLinkField.setConfiguration(getHyperLinkConfiguration());
			hyperLinkField.configure();
			hyperLinkField.create();
	
			baseHp.add(hyperLinkField);
			baseHp.add(image);
			baseHp.setStylePrimaryName("breadcrumbComponent");

		/*	if(Boolean.parseBoolean(breadCrumbSnippetConfig.getPropertyByName(SHOWACTIONLINK).toString())){
				hyperLinkField.setConfiguration(getHyperLinkConfiguration(actionEntity.getPropertyByName("name").toString()));
				hyperLinkField.configure();
				hyperLinkField.create();
				baseHp.add(hyperLinkField);
			}

			Configuration actionImgConfig = breadCrumbSnippetConfig.getPropertyByName(ACTIONIMGCONFIG);
			image.setConfiguration(actionImgConfig);
			image.configure();
			image.create();
			baseHp.add(image);

			baseHp.setStylePrimaryName(breadCrumbSnippetConfig.getPropertyByName(BASEPCSS).toString());*/

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Configuration getHyperLinkConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(LinkFieldConstant.BF_PCLS, BRSPRIMARY_LINK_CSS);
			configuration.setPropertyByName(LinkFieldConstant.BC_BASEPANEL_PCLS, BRS_LINK_BASE_CSS);
			configuration.setPropertyByName(LinkFieldConstant.BF_BINDPROP, "name");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[BreadcrumbSnippetField]::Exception In getHyperLinkConfiguration  method :"+e);
		}
		return configuration;

	}

	private Configuration getImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/breadcrumArrowUnfilled.png");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS, ACTNIMGCSS);
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
			EntityList childlist= actionEntity.getPropertyByName(PROPTOBINDCHILD);
			if(!childlist.isEmpty()){
				BreadcrumbPopup popup=new BreadcrumbPopup(childlist);
				popup.setLevelNo(levelNo);
				popup.setPopupPosition(left, top);
				popup.createUI();
				popup.show();
			}
		}catch (Exception e) {
			logger.log(Level.SEVERE,"[BreadcrumbSnippetField]::Exception In showBreadcrumbPopup  method :"+e);
		}
	}

	public void setActionEntity(Entity entity) {
		actionEntity=entity;
	}
	
	public Entity getActionEntity() {
		return actionEntity;
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

	public void setLevelNo(int counter) {
		this.levelNo = counter;
	}


}
