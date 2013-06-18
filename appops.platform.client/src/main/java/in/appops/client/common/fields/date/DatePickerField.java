package in.appops.client.common.fields.date;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.DateOnlyPicker;
import in.appops.client.common.fields.Field;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DatePickerField extends Composite implements Field, ClickHandler {
	
	private HorizontalPanel dtPickFieldBase;
	private VerticalPanel dtPickPopupBase;
	private PopupPanel dtPickPopup;
	private TextBox dtPickBox;
	private ToggleButton dtPickTrigger;
	private AppopsDatePicker dtPicker;
	
	public DatePickerField(){
		initialize();
		
		
		
		/*		textbox=new TextBox();
		textbox.setStylePrimaryName("appops-TextField-default");
		textbox.addFocusHandler(this);
*/		//initWidget(dtPickBox);
	}
	
	private void initialize() {
		dtPickFieldBase = new HorizontalPanel();
		dtPickBox=new TextBox();
		dtPickTrigger = new ToggleButton();
		dtPickPopupBase = new VerticalPanel();
		dtPickPopup = new PopupPanel(true);
		dtPicker = new AppopsDatePicker();
	}
	
	public void configure() {
		
	}
	
	@Override
	public void create() throws AppOpsException {
		dtPickFieldBase.setStylePrimaryName(getDatePickFieldPrimCss());
		dtPickBox.setStylePrimaryName(getDatePickBoxPrimCss());
		dtPickFieldBase.add(dtPickBox);
		
		dtPickTrigger.setStylePrimaryName("appops-dtPickTrigger");
		dtPickFieldBase.add(dtPickTrigger);
		
		NodeList<com.google.gwt.dom.client.Element> nodeList = dtPickFieldBase.getElement().getElementsByTagName("td");
	    Node td1Node = nodeList.getItem(0);
	    Element td1Element = (Element) Element.as(td1Node);
	    td1Element.setClassName("appops-dtPicker-border-box");

	    Node td2Node = nodeList.getItem(1);
	    Element td2Element = (Element) Element.as(td2Node);
	    td2Element.setClassName("appops-dtPicker-border-box");
		
	    dtPicker.setWidth("100%");
	    dtPickPopup.setVisible(false);
	    dtPickPopup.setAnimationEnabled(true);
	    dtPickPopup.setWidget(dtPicker);
	    
		initWidget(dtPickFieldBase);
		dtPickTrigger.addClickHandler(this);
		dtPicker.addValueChangeHandler(new ValueChangeHandler(){
			@Override
			public void onValueChange(ValueChangeEvent event) {
				java.util.Date date = (java.util.Date) event.getValue();
		        String dateString = DateTimeFormat.getFormat("dd/MM/yyyy").format(date);
		        dtPickBox.setText(dateString);
		        dtPickPopup.hide();
		       
		     }});
		
		dtPickPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				dtPickTrigger.setDown(false);
				
			}
		});
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns the primary style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldPrimCss() {
		String primaryCss = "appops-dtPickFieldPrim";
//		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS) != null) {
//			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS).toString();
//		}
		return primaryCss;
	}

	/**
	 * Returns the dependent style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldDepCss() {
		String depCss = "appops-dtPickFieldDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS) != null) {
//			depCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS).toString();
//		}
		return depCss;
	}

	/**
	 * Returns the primary style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickBoxPrimCss() {
		String primaryCss = "appops-dtPickBoxPrim";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS) != null) {
//			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS).toString();
//		}
		return primaryCss;
	}
	
	/**
	 * Returns the dependent style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickBoxDepCss() {
		String dependentCss = "appops-dtPickBoxDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS) != null) {
//			dependentCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS).toString();
//		}
		return dependentCss;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(dtPickTrigger)) {
			if(dtPickTrigger.isDown()) {
				dtPickPopup.showRelativeTo(dtPickBox);
			} else if(!dtPickTrigger.isDown()) {

			}
			
		}
		
	}
	
	
	
}
