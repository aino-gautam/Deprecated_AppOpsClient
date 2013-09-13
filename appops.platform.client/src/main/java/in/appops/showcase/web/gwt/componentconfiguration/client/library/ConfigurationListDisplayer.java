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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationListDisplayer extends Composite  implements FieldEventHandler,ConfigEventHandler,ClickHandler{

	private VerticalPanel basePanel;
	private ScrollPanel propertyConfListScrollPanel;
	private FlexTable configurationListTable;
	private Entity entity;
	private HashMap<Long, HashMap<String, EntityList>> hashMap = new HashMap<Long, HashMap<String, EntityList>>();
	private HashMap<Integer, HashMap<String, EntityList>> afterDeletionOfRowHashMap = new HashMap<Integer, HashMap<String, EntityList>>();
	private HashMap<Integer, HashMap<String, EntityList>> beforeDeletionOfRowHashMap = new HashMap<Integer, HashMap<String, EntityList>>();
	
	private HashMap<Long, HashMap<Long, Integer>> rowVsRowListSizeMap = new HashMap<Long, HashMap<Long,Integer>>();
	private int i=0;
	private final String PROPERTYLISTROW_CSS = "componentListRow";
	private final String FLEXTABEL_HEADER = "FlexTable-headerBar";
	private Long rowEntityId;
	private int cellIndex ;
    int rowIndex;
    int maxRow;
    private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	public ConfigurationListDisplayer(){
		basePanel = new VerticalPanel();
		
		propertyConfListScrollPanel = new ScrollPanel(basePanel);
		initWidget(propertyConfListScrollPanel);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
		AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		
	}
	
	public void createUi(ArrayList<Entity> configList){
		basePanel.clear();
		String name;
		String value;
		Boolean isDefault;
		int row= 1;
		int col=0;
		
		LabelField propertyNameField = createLabelField("Property Name","componentSectionHeaderLbl","");
		LabelField propertyValueField = createLabelField("Value(s)","componentSectionHeaderLbl","");
		LabelField valueField = createLabelField(" ","componentSectionHeaderLbl","");
		
		try{
			if(configList != null){
				HashMap<String, EntityList> configTypeHashMap = new HashMap<String, EntityList>();
				
				for(Entity configTypeEntity:configList){
					
					
					name = configTypeEntity.getPropertyByName("keyname");
					
					if(configTypeHashMap.containsKey(name)){
						
						EntityList existingConfigTypeList =  configTypeHashMap.get(name);
						existingConfigTypeList.add(configTypeEntity);
						configTypeHashMap.put(name, existingConfigTypeList);
					}else{
						EntityList arrayList = new EntityList();
						arrayList.add(configTypeEntity);
						configTypeHashMap.put(name, arrayList);
					}
					
					
					
				}
								
			configurationListTable.setWidget(0, 0, propertyNameField);
			configurationListTable.setWidget(0, 1, propertyValueField);
			configurationListTable.setWidget(0, 2, valueField);
			configurationListTable.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			configurationListTable.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
			
			configurationListTable.getRowFormatter().addStyleName(0, FLEXTABEL_HEADER); 
			 
			
			Iterator iterator2 = configTypeHashMap.entrySet().iterator();
			while(iterator2.hasNext()){
				HashMap<String, EntityList> configTypeHashMapForStore  = new HashMap<String, EntityList>();
				Map.Entry mapEntry = (Map.Entry) iterator2.next();
				String keyname= (String) mapEntry.getKey();
				configurationListTable.setWidget(row, col, createLabelField(keyname,"compRegisterLabelCss",""));
				EntityList typesList = (EntityList) mapEntry.getValue();
				configTypeHashMapForStore.put(keyname, typesList);
				HashMap<Long, Integer>rowVsListSize = new HashMap<Long, Integer>();
				
				VerticalPanel configValuePanel = new VerticalPanel();
				VerticalPanel imagePanel = null ;
				ImageField imageField = null;
				configurationListTable.setWidget(row, col+1, configValuePanel);
				
				
				for(Entity entity : typesList){
					Key<Serializable> key = (Key<Serializable>) entity.getProperty("id").getValue();
					Long configTypeId=  (Long) key.getKeyValue();
					rowVsListSize.put(configTypeId, typesList.size());
					col = 0;
					if(entity.getPropertyByName("keyname")!=null){
					   name = entity.getPropertyByName("keyname");
					}else{
						name = "-";
					}
					
					if(entity.getPropertyByName("keyvalue")!=null){
						value = entity.getPropertyByName("keyvalue");
					}else{
						value = "-";
					}
					
					if(entity.getPropertyByName("isdefault")!=null){
					  isDefault = entity.getPropertyByName("isdefault");
					  if(isDefault){
						  if(entity.getPropertyByName("keyvalue")!=null)
						   value = value+" "+" (default)";
					  }
					}
					
					
					//configurationListTable.getFlexCellFormatter().setRowSpan(row, col+1,typesList.size());
					configValuePanel.add(createLabelField(value,"compRegisterLabelCss",""));
					imagePanel =  new VerticalPanel();
					imageField = (ImageField) createImageField(configTypeId);
					
					configurationListTable.setWidget(row, col+2, imagePanel);
					
					configurationListTable.getCellFormatter().setAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_TOP);
					configurationListTable.getCellFormatter().setAlignment(row, col+1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					configurationListTable.getCellFormatter().setAlignment(row, col+2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					
																	
					configurationListTable.getRowFormatter().setStylePrimaryName(row, PROPERTYLISTROW_CSS);
					configurationListTable.setBorderWidth(1);
					//configurationListTable.getRowFormatter().addStyleName(row, "configurationPropertyGrid");
					hashMap.put(configTypeId, configTypeHashMapForStore);
					beforeDeletionOfRowHashMap.put(row,configTypeHashMapForStore );
					/*maintain the row v/s hash map of the initial row v/s list size*/
					rowVsRowListSizeMap.put(configTypeId, rowVsListSize);
					
					
				}
				row++;
				imagePanel.add(imageField);
			}
			configurationListTable.addClickHandler(this);
			maxRow = row-1;
											
			basePanel.add(configurationListTable);
			propertyConfListScrollPanel.setStylePrimaryName("propertyConfListScrollPanel");
			configurationListTable.setStylePrimaryName("configurationListTable");
		}else{
			showNotificationForListEmpty();
			
			
		}
	
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}
	/**
	 * This method creates the grid ui of propertyConfig
	 * @param configPropertyEntity
	 */
	public  void createUi(Entity configPropertyEntity) {
		basePanel.clear();
			String name;
			String value;
			Boolean isDefault;
			int row= 1;
			int col=0;
			
			LabelField propertyNameField = createLabelField("Property Name","componentSectionHeaderLbl","");
			LabelField propertyValueField = createLabelField("Value(s)","componentSectionHeaderLbl","");
			LabelField valueField = createLabelField(" ","componentSectionHeaderLbl","");
			
			try{
				if(configPropertyEntity.getPropertyByName("configtypes")!=null){
					ArrayList<Entity> entityList = configPropertyEntity.getPropertyByName("configtypes");
					HashMap<String, EntityList> configTypeHashMap = new HashMap<String, EntityList>();
					
					for(Entity configTypeEntity:entityList){
						
						
						name = configTypeEntity.getPropertyByName("keyname");
						
						if(configTypeHashMap.containsKey(name)){
							
							EntityList existingConfigTypeList =  configTypeHashMap.get(name);
							existingConfigTypeList.add(configTypeEntity);
							configTypeHashMap.put(name, existingConfigTypeList);
						}else{
							EntityList arrayList = new EntityList();
							arrayList.add(configTypeEntity);
							configTypeHashMap.put(name, arrayList);
						}
						
						
						
					}
									
				configurationListTable.setWidget(0, 0, propertyNameField);
				configurationListTable.setWidget(0, 1, propertyValueField);
				configurationListTable.setWidget(0, 2, valueField);
				configurationListTable.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				configurationListTable.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				
				configurationListTable.getRowFormatter().addStyleName(0, FLEXTABEL_HEADER); 
				 
				
				Iterator iterator2 = configTypeHashMap.entrySet().iterator();
				while(iterator2.hasNext()){
					HashMap<String, EntityList> configTypeHashMapForStore  = new HashMap<String, EntityList>();
					Map.Entry mapEntry = (Map.Entry) iterator2.next();
					String keyname= (String) mapEntry.getKey();
					configurationListTable.setWidget(row, col, createLabelField(keyname,"compRegisterLabelCss",""));
					EntityList typesList = (EntityList) mapEntry.getValue();
					configTypeHashMapForStore.put(keyname, typesList);
					HashMap<Long, Integer>rowVsListSize = new HashMap<Long, Integer>();
					
					VerticalPanel configValuePanel = new VerticalPanel();
					VerticalPanel imagePanel = null ;
					ImageField imageField = null;
					configurationListTable.setWidget(row, col+1, configValuePanel);
					
					
					for(Entity entity : typesList){
						Key<Serializable> key = (Key<Serializable>) entity.getProperty("id").getValue();
						Long configTypeId=  (Long) key.getKeyValue();
						rowVsListSize.put(configTypeId, typesList.size());
						col = 0;
						if(entity.getPropertyByName("keyname")!=null){
						   name = entity.getPropertyByName("keyname");
						}else{
							name = "-";
						}
						
						if(entity.getPropertyByName("keyvalue")!=null){
							value = entity.getPropertyByName("keyvalue");
						}else{
							value = "-";
						}
						
						if(entity.getPropertyByName("isdefault")!=null){
						  isDefault = entity.getPropertyByName("isdefault");
						  if(isDefault){
							  if(entity.getPropertyByName("keyvalue")!=null)
							   value = value+" "+" (default)";
						  }
						}
						
						
						//configurationListTable.getFlexCellFormatter().setRowSpan(row, col+1,typesList.size());
						configValuePanel.add(createLabelField(value,"compRegisterLabelCss",""));
						imagePanel =  new VerticalPanel();
						imageField = (ImageField) createImageField(configTypeId);
						
						configurationListTable.setWidget(row, col+2, imagePanel);
						
						configurationListTable.getCellFormatter().setAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_TOP);
						configurationListTable.getCellFormatter().setAlignment(row, col+1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
						configurationListTable.getCellFormatter().setAlignment(row, col+2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
						
																		
						configurationListTable.getRowFormatter().setStylePrimaryName(row, PROPERTYLISTROW_CSS);
						configurationListTable.setBorderWidth(1);
						//configurationListTable.getRowFormatter().addStyleName(row, "configurationPropertyGrid");
						hashMap.put(configTypeId, configTypeHashMapForStore);
						beforeDeletionOfRowHashMap.put(row,configTypeHashMapForStore );
						/*maintain the row v/s hash map of the initial row v/s list size*/
						rowVsRowListSizeMap.put(configTypeId, rowVsListSize);
						
						
					}
					row++;
					imagePanel.add(imageField);
				}
				configurationListTable.addClickHandler(this);
				maxRow = row-1;
												
				basePanel.add(configurationListTable);
				propertyConfListScrollPanel.setStylePrimaryName("propertyConfListScrollPanel");
				configurationListTable.setStylePrimaryName("configurationListTable");
			}else{
				showNotificationForListEmpty();
				
				
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	
	private void showNotificationForListEmpty() {
		basePanel.clear();
		LabelField notificationMessageField = createLabelField("No configurations available ","componentSectionHeaderLbl","");
		basePanel.add(notificationMessageField);
		basePanel.setCellHorizontalAlignment(notificationMessageField, HasHorizontalAlignment.ALIGN_CENTER);
		propertyConfListScrollPanel.setStylePrimaryName("propertyConfListScrollPanel");
		configurationListTable.setStylePrimaryName("configurationListTable");
		
	}
	private Widget createImageField(Long row) {
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

	public void initialize() {
		configurationListTable = new FlexTable();
		
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public EntityList getConfigTypeList(Entity entity){
		
		try {
			
			ImageField imageField = new ImageField();
			 imageField.setConfiguration(getImageConfiguration());
			 imageField.configure();
			 imageField.create();
			 basePanel.add(imageField);
			 basePanel.setCellVerticalAlignment(imageField, HasVerticalAlignment.ALIGN_BOTTOM);
			Property<Serializable> componentProperty = (Property<Serializable>) entity.getProperty("id");
			Key<Serializable> key  = (Key<Serializable>) componentProperty.getValue();
			
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeId", key.getKeyValue());
			
			StandardAction action = new StandardAction(Entity.class, "configuration.ConfigurationService.getConfigTypeFromKey", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Entity propertyConfig   = result.getOperationResult();
						if(propertyConfig!=null){
							createUi(propertyConfig);
						}
					}else{
						showNotificationForListEmpty();
						
					}
					
				}
			});
		} catch (Exception e) {
		}
		
		return null;
		
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
					rowEntityId= Long.parseLong(imageField.getBaseFieldId());
					
					EntityList typesList = null;
					HashMap<String, EntityList> nameVsListValues = hashMap.get(rowEntityId);
					Iterator iterator = nameVsListValues.entrySet().iterator();
					while(iterator.hasNext()){
							
						Map.Entry mapEntry = (Map.Entry) iterator.next();
					    typesList = (EntityList) mapEntry.getValue();
				    }	 
					  deletePropertyConfig(typesList,rowIndex);	
				}
					
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			
		}
		
	}

	@SuppressWarnings("unchecked")
	private void deletePropertyConfig(EntityList typesList, final int row) {
		
		try {
			
			
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeEntList", typesList);
			
			StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.deleteConfigurationTypeList", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList configTypeEntList   = result.getOperationResult();
						if(configTypeEntList!=null){
							  notificationPopup("Configuration property deleted..");
							 configurationListTable.removeRow(rowIndex);
							 HashMap<Integer, HashMap<String, EntityList>> hashMap = new HashMap<Integer, HashMap<String, EntityList>>();
							
									for(int count = rowIndex+1;count<=maxRow;count++){	
										
										 HashMap<String, EntityList> value = beforeDeletionOfRowHashMap.get(count);
										 afterDeletionOfRowHashMap.put(count-1, value);
									}
									
									for(int count = rowIndex-1 ;count<rowIndex;count--){
										if(count>0){
											
											HashMap<String, EntityList> value = beforeDeletionOfRowHashMap.get(count);
											afterDeletionOfRowHashMap.put(count, value);
										}else{
											break;
										}
									}
								
						}
					}
					
				}

				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	private void notificationPopup(String messageStr) {
		try {
			LabelField popupLbl = new LabelField();
			popupLbl.setConfiguration(getLabelFieldConf(messageStr,POPUP_LBL_PCLS,null,null));
			popupLbl.configure();
			popupLbl.create();
					
			PopupPanel popup = new PopupPanel();
			popup.setAnimationEnabled(true);
			popup.setAutoHideEnabled(true);
			popup.setGlassEnabled(true);
			popup.setGlassStyleName(POPUPGLASSPANELCSS);
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.setPopupPosition(542, 70);
			popup.show();
		} catch (Exception e) {
			
		}
		
	}
	/**
	 * Creates the table name label field configuration object and return.
	 * @param displayText
	 * @param primaryCss
	 * @param dependentCss
	 * @param propEditorLblPanelCss
	 * @return Configuration instance
	 */
	private Configuration getLabelFieldConf(String displayText , String primaryCss , String dependentCss ,String propEditorLblPanelCss){
		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, dependentCss);
			conf.setPropertyByName(LabelFieldConstant.BF_BASEPANEL_PCLS, propEditorLblPanelCss);
		} catch (Exception e) {
			
		}
		return conf;
	}
	
	private Configuration getImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID,"images/defaultLoader.gif");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,"appops-listBoxLoaderPcls");
			
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			/*case ConfigEvent.COMPONENTSELECTED: {
				if (eventSource instanceof ConfigurationListDisplayer) {
					Entity entity=  (Entity) event.getEventData(); 
					
				}else if(eventSource instanceof ComponentListDisplayer){
					HashMap<String, Entity> map = (HashMap<String, Entity>) event.getEventData();
					Entity compEntity   = map.get("component");
					//Entity entity=  (Entity) event.getEventData(); 
					getPropertyConfigList(compEntity);
				}
				break;
			}*/
			case ConfigEvent.SAVEDCONFIGENTITY :{
				if(eventSource instanceof ConfPropertyEditor){
					//TODO:not implemented yet
					EntityList confEntityList = (EntityList) event.getEventData();
					//addNewRowInGrid(confEntityList);
				}
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addNewRowInGrid(EntityList confEntityList) {
		 
		for(Entity entity:confEntityList){
			
        }
		
	}

	@Override
	public void onClick(ClickEvent event) {
	  Widget sender = (Widget) event.getSource();
	  
	    if(i % 2 == 0){
		  if(sender instanceof FlexTable){	 
			if(configurationListTable.getCellForEvent(event)!=null){
				 cellIndex = configurationListTable.getCellForEvent(event).getCellIndex();
	             rowIndex = configurationListTable.getCellForEvent(event).getRowIndex();
	           
	              int value = (int)rowIndex;
	              HashMap<String, EntityList> storedConfigTypeMap;
	              if(afterDeletionOfRowHashMap.size()>0){
	                  storedConfigTypeMap = afterDeletionOfRowHashMap.get(value);
	              }else{
	            	  storedConfigTypeMap = beforeDeletionOfRowHashMap.get(value);
	              }
	            
	            if(cellIndex < 2){
		            ConfigEvent configEvent = new ConfigEvent(ConfigEvent.PROPERTYSELECTED, storedConfigTypeMap, this);
					configEvent.setEventSource(this);
					AppUtils.EVENT_BUS.fireEvent(configEvent);
	            }else if(cellIndex == 2){
	            	
					     	
					
	            }
			}
		  }
			
		}
	    i+=2;
	}
}
