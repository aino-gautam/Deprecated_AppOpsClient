package in.appops.client.common.fields;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class TimePicker extends Composite implements FocusHandler, BlurHandler, ClickHandler, KeyDownHandler, CloseHandler<PopupPanel> {
    private VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	private ListBox hoursListBox = new ListBox();
	private ListBox minListBox = new ListBox();
	private ListBox secListBox = new ListBox();
	private TextBox textBox = new TextBox();
	private Button selectTimeButton = new Button("->"); 
	private PopupPanel popupPanel;
	private String value; 
	
	public TimePicker() {
		
		popupPanel = new PopupPanel(true);
		popupPanel.setWidget(createTimePickerPanel());
		popupPanel.addAutoHidePartner(textBox.getElement());
		initWidget(textBox);
		
		textBox.addFocusHandler(this);
		textBox.addBlurHandler(this);
		textBox.addClickHandler(this);
		textBox.addKeyDownHandler(this);
		popupPanel.addCloseHandler(this);
		
		DOM.setStyleAttribute(textBox.getElement(), "width", "240px");
	}

	private HorizontalPanel createTimePickerPanel() {
		HorizontalPanel subHorizontalPanel = new HorizontalPanel();
		Label lblHour= new Label("(hh):");
		Label lblMinute= new Label("(mm):");
		Label lblSecond= new Label("(ss)");
		subHorizontalPanel.add(hoursListBox);
		subHorizontalPanel.add(lblHour);
		populateTimeListbox(hoursListBox,23);
		subHorizontalPanel.add(minListBox);
		subHorizontalPanel.add(lblMinute);
		populateTimeListbox(minListBox,59);
		subHorizontalPanel.add(secListBox);
		subHorizontalPanel.add(lblSecond);
		populateTimeListbox(secListBox,59);
		
		subHorizontalPanel.add(selectTimeButton);
		
		DOM.setStyleAttribute(lblHour.getElement(), "marginTop", "5px");
		DOM.setStyleAttribute(lblMinute.getElement(), "marginTop", "5px");
		DOM.setStyleAttribute(lblSecond.getElement(), "marginTop", "5px");
		
		selectTimeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				 value = hoursListBox.getItemText(hoursListBox.getSelectedIndex())+":"+ minListBox.getItemText(minListBox.getSelectedIndex())+":"+ secListBox.getItemText(secListBox.getSelectedIndex());
				 textBox.setText(value);
			}
		});
		
		return subHorizontalPanel; 
		
		
	}
	
    private void populateTimeListbox(ListBox listBox,Integer lastNumber) {
		
		listBox.clear();
		for(Integer i=0;i<=lastNumber;i++){
			
			if(i<10)
				listBox.addItem("0"+i.toString(), "0"+i.toString());
			else
				listBox.addItem(i.toString(), i.toString());
		}
	}

	 public void showDatePicker() {
		
		 popupPanel.showRelativeTo(this);
    }
	
	@Override
	public void onClose(CloseEvent<PopupPanel> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		switch (event.getNativeKeyCode()) {
        case KeyCodes.KEY_ENTER:
        case KeyCodes.KEY_TAB:
        	 showDatePicker();
          // Deliberate fall through
        case KeyCodes.KEY_ESCAPE:
        case KeyCodes.KEY_UP:
          hideTimePicker();
          break;
        case KeyCodes.KEY_DOWN:
          showDatePicker();
          break;
      }
		
	}

	private void hideTimePicker() {
		popupPanel.hide();
		
	}

	private void updateTimeFromTextBox() {
		textBox.setText("");
		
	}

	@Override
	public void onClick(ClickEvent event) {
		 showDatePicker();
		
	}

	@Override
	public void onBlur(BlurEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFocus(FocusEvent event) {
		// showDatePicker();
		
	}
	
}
