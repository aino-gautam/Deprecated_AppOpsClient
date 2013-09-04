package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationListDisplayer extends Composite  implements FieldEventHandler,ConfigEventHandler,ClickHandler{

	private VerticalPanel basePanel;
	private FlexTable configurationListTable;
	private Entity entity;
	private HashMap<Integer, Entity> hashMap = new HashMap<Integer, Entity>();
	private int i=0;
	public ConfigurationListDisplayer(){
		initialize();
		
	}

	public  void createUi(EntityList entityList) {
		
		String name;
		String value;
		int row= 1;
		int col=0;
		
		LabelField propertyNameField = createLabelField("Property Name","componentSectionHeaderLbl","");
		LabelField propertyValueField = createLabelField("Value(s)","componentSectionHeaderLbl","");
		LabelField valueField = createLabelField(" ","componentSectionHeaderLbl","");
		
		
		
		try{
			
			
			configurationListTable.setWidget(0, 0, propertyNameField);
			configurationListTable.setWidget(0, 1, propertyValueField);
			configurationListTable.setWidget(0, 2, valueField);
			configurationListTable.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			configurationListTable.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			
			configurationListTable.getRowFormatter().addStyleName(0, "FlexTable-headerBar"); 
			//HTMLTable.RowFormatter rf = configurationListTable.getRowFormatter();
			//rf.addStyleName(0, "FlexTable-OddRow");
			//configurationListTable.getRowFormatter().addStyleName(0, "configurationPropertyGrid");
			//configurationListTable.getCellFormatter().addStyleName(0,0,"configurationPropertyGrid");
			for(Entity entity:entityList){
				col = 0;
				name = entity.getPropertyByName("name");
				value = entity.getPropertyByName("value");
				
				configurationListTable.setWidget(row, col, createLabelField(name,"",""));
				
				configurationListTable.setWidget(row, col+1, createLabelField(value,"",""));
				configurationListTable.setWidget(row, col+2, createImageField(row));
				
				/*configurationListTable.getFlexCellFormatter().setRowSpan(row, col, 2);
				String[] values = value.split(",");
				col++;
				for(int i=0;i<values.length;i++){
				   configurationListTable.setWidget(row, col, createLabelField(values[i],"",""));
				   configurationListTable.setWidget(row, col+1, createImageField());
				}*/
				configurationListTable.getCellFormatter().setAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				configurationListTable.getCellFormatter().setAlignment(row, col+1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				configurationListTable.getCellFormatter().setAlignment(row, col+2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				//configurationListTable.getRowFormatter().addStyleName(row, "configurationPropertyGrid"); 
				configurationListTable.setCellSpacing(2);
				configurationListTable.addClickHandler(this);
				//configurationListTable.getRowFormatter().addStyleName(row, "configurationPropertyGrid");
				hashMap.put(row, entity);
				row++;
			}
			
			configurationListTable.setSize("950px", "100%");
			
			basePanel.add(configurationListTable);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void addSavedPropertyConfig(){
		entity.getPropertyByName("name");
		entity.getPropertyByName("value");
		
		
	}
	
	private Widget createImageField(int row) {
		   ImageField crossImageField = new ImageField();
		   
		   try{
			   crossImageField.setConfiguration(createImageFieldConfiguartion("images/cross.png","",String.valueOf(row)));
			   crossImageField.configure();
			   crossImageField.create();
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		return crossImageField;
	}

	private Configuration createImageFieldConfiguartion(String imagePath,
			String promary, String string3) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, imagePath);
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, string3);
			//configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, css);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	private LabelField createLabelField(String name, String primaryCss,
			String secondaryCss) {
		LabelField labelField = new LabelField();
		
		try{
			labelField.setConfiguration(createLabelConfiguration(name,primaryCss));
			labelField.configure();
			labelField.create();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return labelField;
	}

	private Configuration createLabelConfiguration(String text, String css) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, text);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, css);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;

	}

	private void initialize() {
		basePanel = new VerticalPanel();
		configurationListTable = new FlexTable();
		initWidget(basePanel);
		
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
		AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public EntityList getPropertyConfigList(Entity entity){
		
		try {
			Property<Serializable> componentProperty = (Property<Serializable>) entity.getProperty("id");
			Key<Serializable> key  = (Key<Serializable>) componentProperty.getValue();
			
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeId", key.getKeyValue());
			
			StandardAction action = new StandardAction(EntityList.class, "configuration.ConfigurationService.getConfigTypeFromKey", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList propertyConfigList   = result.getOperationResult();
						if(!propertyConfigList.isEmpty()){
							createUi(propertyConfigList);
						}
					}
					
				}
			});
		} catch (Exception e) {
		}
		
		return null;
		
	}
	
	
	public EntityList getDummyList(){
		Entity labelField = new Entity();
		
		
		labelField.setPropertyByName("name","Error Position");
		labelField.setPropertyByName("value","Bottom(default),Top,Inline");
		
		
		Entity dateLabelField = new Entity();
		dateLabelField.setPropertyByName("name","DisplayText");
		dateLabelField.setPropertyByName("value","Name,Title(default)");
		
		EntityList list = new EntityList();
		list.add(labelField);
		list.add(dateLabelField);
		return list;
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ImageField) {
					ImageField imageField = (ImageField) eventSource;
					int row= Integer.parseInt(imageField.getBaseFieldId());
					configurationListTable.removeRow(row);
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.COMPONENTSELECTED: {
				if (eventSource instanceof ConfigurationListDisplayer) {
					Entity entity=  (Entity) event.getEventData(); 
					
				}/*else if(eventSource instanceof ComponentListDisplayer){
					Entity entity=  (Entity) event.getEventData(); 
					getPropertyConfigList(entity);
				}*/
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onClick(ClickEvent event) {
	  Widget sender = (Widget) event.getSource();
	  
	    if(i % 2 == 0){
		  if(sender instanceof FlexTable){	 
			if(configurationListTable.getCellForEvent(event)!=null){
				int cellIndex = configurationListTable.getCellForEvent(event).getCellIndex();
	            int rowIndex = configurationListTable.getCellForEvent(event).getRowIndex();
	            Entity entity = hashMap.get(rowIndex);
	            ConfigEvent configEvent = new ConfigEvent(ConfigEvent.PROPERTYSELECTED, entity, this);
				configEvent.setEventSource(this);
				AppUtils.EVENT_BUS.fireEvent(configEvent);
			}
		  }
			
		}
	    i++;
	}
}
