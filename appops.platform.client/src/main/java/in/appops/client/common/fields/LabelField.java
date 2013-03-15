package in.appops.client.common.fields;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

/**
 * Field class to represent a {@link Label}
 * @author nairutee
 *
 */
public class LabelField extends Label  implements Field{

	private Configuration configuration;
	private String fieldValue;
	private Label label;
	
	public static final String LABELFIELD_WORDWRAP = "labelFieldWordWrap";
	public static final String LABELFIELD_PRIMARYCSS = "labelFieldPrimaryCss";
	public static final String LABELFIELD_DEPENDENTCSS = "labelFieldDependentCss";
	public static final String LABELFIELD_DEBUGID = "labelFieldDebugId";
	
	public LabelField() {
		
	}
	
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	@Override
	public void createField() throws AppOpsException{
		if(getConfiguration() == null)
			throw new AppOpsException(" LabelField configuration unavailable");
		
		//label = new Label();
		this.setText(getFieldValue());
		this.setWordWrap((Boolean) getConfiguration().getPropertyByName(LABELFIELD_WORDWRAP));
		if(getConfiguration().getPropertyByName(LABELFIELD_PRIMARYCSS) != null)
			this.setStylePrimaryName(getConfiguration().getPropertyByName(LABELFIELD_PRIMARYCSS).toString());
		if(getConfiguration().getPropertyByName(LABELFIELD_DEPENDENTCSS) != null)
			this.addStyleName(getConfiguration().getPropertyByName(LABELFIELD_DEPENDENTCSS).toString());
		if(getConfiguration().getPropertyByName(LABELFIELD_DEBUGID) != null)
			this.ensureDebugId(getConfiguration().getPropertyByName(LABELFIELD_DEBUGID).toString());
	}
	
	/**
	 * clears the field if it has any values
	 */
	@Override
	public void clearField() {
		this.setText("");
	}
	
	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void resetField() {
		this.setText(getFieldValue());
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
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

}
