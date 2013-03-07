package in.appops.client.common.fields;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

/**
 * Field class to represent a {@link TextBox}, {@link TextArea} or {@link PasswordTextBox}
 * @author nairutee
 *
 */
public class TextField extends Composite implements Field, FocusHandler, ValueChangeHandler{

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	private PasswordTextBox passwordTextBox;
	private TextArea textArea;
	private String fieldType;
	private String tempFieldValue;
	
	
	public static final String TEXTFIELD_VISIBLELINES = "textFieldVisibleLines";
	public static final String TEXTFIELD_READONLY = "textFieldReadOnly";
	public static final String TEXTFIELD_PRIMARYCSS = "textFieldPrimaryCss";
	public static final String TEXTFIELD_DEPENDENTCSS = "textFieldDependentCss";
	public static final String TEXTFIELD_DEBUGID = "textFieldDebugId";
	public static final String TEXTFIELD_TYPE = "textFieldType";
	
	public static final String TEXTFIELDTYPE_TEXTBOX = "textbox";
	public static final String TEXTFIELDTYPE_PASSWORDTEXTBOX = "passowrdTextbox";
	public static final String TEXTFIELDTYPE_TEXTAREA = "textarea";
	
	public TextField(){
		
	}
	
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	public void createField() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("TextField configuration unavailable");
		
		Integer visibleLines = (Integer) getConfiguration().getPropertyByName(TEXTFIELD_VISIBLELINES);
		fieldType = getConfiguration().getPropertyByName(TEXTFIELD_TYPE).toString();
		if(visibleLines == 1){
			if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_TEXTBOX)){
				textBox = new TextBox();
				textBox.setText(getFieldValue());
				textBox.setReadOnly((Boolean) getConfiguration().getPropertyByName(TEXTFIELD_READONLY));
				if(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS) != null)
					textBox.setStylePrimaryName(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS) != null)
					textBox.addStyleName(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID) != null)
					textBox.ensureDebugId(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID).toString());
				
				initWidget(textBox);
			}else if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_PASSWORDTEXTBOX)){
				passwordTextBox = new PasswordTextBox();
				passwordTextBox.setText(getFieldValue());
				passwordTextBox.setReadOnly((Boolean) getConfiguration().getPropertyByName(TEXTFIELD_READONLY));
				if(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS) != null)
					passwordTextBox.setStylePrimaryName(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS) != null)
					passwordTextBox.addStyleName(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID) != null)
					passwordTextBox.ensureDebugId(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID).toString());
				
				initWidget(passwordTextBox);
			}
		}else{
			textArea = new TextArea();
			textArea.setVisibleLines(visibleLines);
			textArea.setText(getFieldValue());
			textArea.setReadOnly((Boolean) getConfiguration().getPropertyByName(TEXTFIELD_READONLY));
			
			if(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS) != null)
				textArea.setStylePrimaryName(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS).toString());
			if(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS) != null)
				textArea.addStyleName(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS).toString());
			if(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID) != null)
				textArea.ensureDebugId(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID).toString());
			
			initWidget(textArea);
		}
	}
	
	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void resetField() {
		if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_TEXTBOX))
			textBox.setText(getFieldValue());
		else if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_PASSWORDTEXTBOX))
			passwordTextBox.setText(getFieldValue());
		else
			textArea.setText(getFieldValue());
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

	/**
	 * clears the field if it has any values
	 */
	@Override
	public void clearField() {
		if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_TEXTBOX))
			textBox.setText("");
		else if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_PASSWORDTEXTBOX))
			passwordTextBox.setText("");
		else
			textArea.setText("");
	}

	@Override
	public void onFocus(FocusEvent event) {
		if(event.getSource() instanceof TextBox){
			
		} else if(event.getSource() instanceof TextArea){
			
		} else if(event.getSource() instanceof PasswordTextBox){
			
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent event) {
		if(event.getSource() instanceof TextBox){
			
		} else if(event.getSource() instanceof TextArea){
			
		} else if(event.getSource() instanceof PasswordTextBox){
			
		}
	}

	public String getTempFieldValue() {
		return tempFieldValue;
	}

	public void setTempFieldValue(String tempFieldValue) {
		this.tempFieldValue = tempFieldValue;
	}
}
