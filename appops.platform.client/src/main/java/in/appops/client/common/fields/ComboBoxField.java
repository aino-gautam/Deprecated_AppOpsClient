package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
/**
 * This is combobox field, which accepts entitylist and will populate the combobox.
 * @author milind@ensarm.com
 */
public class ComboBoxField extends Composite implements Field, ChangeHandler{

	private Configuration configuration;
	private String fieldValue;
	private ListBox listBox;

	public static final String STATEFIELD_PRIMARYCSS = "stateFieldPrimaryCss";
	public static final String STATEFIELD_DEPENDENTCSS = "stateFieldDependentCss";
	public static final String STATEFIELD_DEBUGID = "stateFieldDebugId";

	public ComboBoxField(){

	}

	@Override
	public void createField() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("Statefield configuration unavailable");
		
		listBox = new ListBox(false);
		initWidget(listBox);		
		populateList(null, listBox); // need to provide or fetch the entitylist to be populated
		for(int i=0; i<=listBox.getItemCount();i++){
			String value = listBox.getItemText(i);
			if(getFieldValue().equalsIgnoreCase(value)){
				listBox.setSelectedIndex(i);
				break;
			}
		}
		if(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS) != null)
			listBox.setStylePrimaryName(getConfiguration().getPropertyByName(STATEFIELD_PRIMARYCSS).toString());
		if(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS) != null)
			listBox.addStyleName(getConfiguration().getPropertyByName(STATEFIELD_DEPENDENTCSS).toString());
		if(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID) != null)
			listBox.ensureDebugId(getConfiguration().getPropertyByName(STATEFIELD_DEBUGID).toString());
	}

	@Override
	public void clearField() {

	}

	@Override
	public void resetField() {
		// TODO Auto-generated method stub

	}

	/**
	 * populates the list box with values that the user can select from
	 * @param entityList - list of entities
	 */
	public void populateList(EntityList entityList, ListBox listbox){
		for(Entity entity : entityList){
			String item = entity.getPropertyByName(null); // we need to figure out how this property name will be provided
			listbox.addItem(item);
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
		return this.getFieldValue();
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onChange(ChangeEvent event) {
		if( event.getSource() instanceof ListBox){

		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub

	}
}