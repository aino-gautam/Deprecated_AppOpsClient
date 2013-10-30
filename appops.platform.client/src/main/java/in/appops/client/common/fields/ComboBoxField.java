package in.appops.client.common.fields;

import java.util.HashMap;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.calendar.constant.ReminderTypeConstant;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
/**
 * This is combobox field, which accepts entitylist and will populate the combobox.
 * @author milind@ensarm.com
 */
public class ComboBoxField extends Composite implements Field, ChangeHandler,ClickHandler{

	private Configuration configuration;
	private String fieldValue;
	private ListBox listBox;
	private EntityList entityList;
	private Entity entity;
	private HashMap<String, Object> nameVsEntity ;
	public static final String STATEFIELD_PRIMARYCSS = "stateFieldPrimaryCss";
	public static final String STATEFIELD_DEPENDENTCSS = "stateFieldDependentCss";
	public static final String STATEFIELD_DEBUGID = "stateFieldDebugId";
	public static final String ComboBoxField_EntityType = "entityType";
	public static final String ComboBoxField_EntityPropertyName = "propertyName";
	public static final String ComboBoxField_EntityList = "list";

	public ComboBoxField(){

	}

	@Override
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("Statefield configuration unavailable");
		if(getConfiguration().getPropertyByName(ComboBoxField_EntityList)!=null){
		   entityList=getConfiguration().getPropertyByName(ComboBoxField_EntityList);
		
		listBox = new ListBox(false);
		initWidget(listBox);		
		populateList(entityList, listBox); // need to provide or fetch the entitylist to be populated
		for(int i=0; i<listBox.getItemCount();i++){
			String value = listBox.getItemText(i);
			if(getFieldValue().equalsIgnoreCase(value)){
				listBox.setSelectedIndex(i);
				break;
			}
		}
		}
		if(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS) != null)
			listBox.setStylePrimaryName(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS).toString());
		if(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS) != null)
			listBox.addStyleName(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS).toString());
		if(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID) != null)
			listBox.ensureDebugId(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID).toString());
		listBox.addChangeHandler(this);
		listBox.addClickHandler(this);
	}

	@Override
	public void clear() {

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	/**
	 * populates the list box with values that the user can select from
	 * @param entityList - list of entities
	 */
	public void populateList(EntityList entityList, ListBox listbox){
		//listbox.addItem("--"+getConfiguration().getPropertyByName(ComboBoxField_EntityPropertyName).toString()+"--");
		//fieldValue =getConfiguration().getPropertyByName(ComboBoxField_EntityPropertyName).toString(); 
		nameVsEntity = new HashMap<String, Object>();
		for(Entity entity : entityList){
		   if(getConfiguration().getPropertyByName(ComboBoxField_EntityPropertyName).equals(ReminderTypeConstant.TYPE)){	
			String item = entity.getPropertyByName(ReminderTypeConstant.TYPE); // we need to figure out how this property name will be provided
			nameVsEntity.put(item, entity);
			listbox.addItem(item);
			fieldValue = listbox.getItemText(listbox.getSelectedIndex());
		   }else if(getConfiguration().getPropertyByName(ComboBoxField_EntityPropertyName).equals("unit")){
			   String item = entity.getPropertyByName("unit"); // we need to figure out how this property name will be provided
				nameVsEntity.put(item, entity);
				listbox.addItem(item);
				fieldValue = listbox.getItemText(listbox.getSelectedIndex());
		   }
		}

	}

	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onChange(ChangeEvent event) {
		if( event.getSource() instanceof ListBox){
			fieldValue =listBox.getItemText(listBox.getSelectedIndex()); 
            String name=listBox.getItemText(listBox.getSelectedIndex());
            entity = (Entity) nameVsEntity.get(name);
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub

	}

	public HashMap<String, Object> getNameVsEntity() {
		return nameVsEntity;
	}

	public void setNameVsEntity(HashMap<String, Object> nameVsEntity) {
		this.nameVsEntity = nameVsEntity;
	}

	public EntityList getEntityList() {
		return entityList;
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void onClick(ClickEvent event) {
		if( event.getSource() instanceof ListBox){
			fieldValue =listBox.getItemText(listBox.getSelectedIndex()); 
            String name=listBox.getItemText(listBox.getSelectedIndex());
            entity = (Entity) nameVsEntity.get(name);
		}
		
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}