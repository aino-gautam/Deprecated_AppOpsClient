package in.appops.client.common.fields;

import java.util.Date;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateOnlyPicker extends Composite implements FocusHandler{

	private VerticalPanel   vpPanel = new VerticalPanel();
	private DatePicker		datePicker              = new DatePicker();
	private PopupPanel popupPanel;
	private TextBox textbox;
	private VerticalPanel   vpBase = new VerticalPanel();
	
	public DateOnlyPicker(){
		textbox=new TextBox();
		textbox.addFocusHandler(this);
		popupPanel=new PopupPanel(true);
		popupPanel.setVisible(false);
		popupPanel.setAnimationEnabled(true);
		popupPanel.setAutoHideEnabled(true);
		popupPanel.setWidget(createDateTimePicker());
		popupPanel.addAutoHidePartner(textbox.getElement());
		initWidget(textbox);
	}
	
	
	
	public VerticalPanel createDateTimePicker(){
		
		
		
		datePicker.addValueChangeHandler(new ValueChangeHandler(){

			@Override
			public void onValueChange(ValueChangeEvent event) {
				java.util.Date date = (java.util.Date) event.getValue();
		        String dateString = DateTimeFormat.getFormat("dd-MM-yyyy").format(date);
		        textbox.setText(dateString);
		        popupPanel.hide();
		     }});
		
		vpBase.add(textbox);
		vpPanel.add(datePicker);
		datePicker.setWidth("100%");
		
		return vpPanel;
	}
	
	
	
	

		
	public TextBox getTextbox() {
		return textbox;
	}

	public void setTextbox(TextBox textbox) {
		this.textbox = textbox;
	}

	@Override
	public void onFocus(FocusEvent event) {
		Date today = new Date();
        String todayString  = DateTimeFormat.getFormat("MM-dd-yyyy").format(today);
        textbox.setText(todayString);
		//popupPanel.setVisible(true);
		popupPanel.showRelativeTo(this);
		
		
	}
}
