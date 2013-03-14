package in.appops.client.common.fields;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.HasMouseEvent;
import com.google.gwt.maps.client.event.MouseEventCallback;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

public class LocationSelector extends Composite implements Field {

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	private VerticalPanel basePanel;
	private Entity entity;
	private LatLng latLng ;
	private MapField mapField;
	private TextField currentLocationTextField ;
	private PopupPanel locationSelectorPopupPanel;
	private PopupPanel popupPanelForMap;
	private Image image ;
	private Label currentLocationLabel ;
	public static final String LOCATION_SELECTOR_POPUPPANEL = "locationSelectorPopupPanel";
	public static final String LOCATION_SELECTOR_CHOOSE_LOCATION_BTN = "chooseLocationBtn";
	public static final String LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD = "currentLocationField";
	public static final String LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE = "currentLocationImage";
	public static final String LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE_PRIMARYCSS = "currentLocationImagePrimaryCss";
	public static final String LATLNG = "latLng";
	
	private String mapWidth;
	private String mapHeight;
	private boolean isMapMode = false;
	
	public LocationSelector(){
		basePanel = new VerticalPanel();
		currentLocationTextField = new TextField();
		locationSelectorPopupPanel = new PopupPanel();
		currentLocationLabel = new Label();
		image = new Image();
		basePanel.setWidth("100%");
		basePanel.setHeight("100%");
				
		initWidget(basePanel);
	}
	
	@Override
	public void createField() {
		// TODO will need a map + textbox to enter a location
	 if(!isMapMode){	
		currentLocationTextField.setFieldValue("Current location");
		//currentLocationTextField.setConfiguration(getTextFieldConfiguration(1, true, TextField.TEXTFIELDTYPE_TEXTBOX, "opptin-TextField", CURRENT_LOCATION_FIELD, null));
		currentLocationTextField.setConfiguration(getTextFieldConfiguration(1, true, TextField.TEXTFIELDTYPE_TEXTBOX, getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD).toString(), null, null));
		try{
			currentLocationTextField.createField();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    Button chooseLocationBtn = new  Button("Choose location");
	    if( getConfiguration().getPropertyByName(LOCATION_SELECTOR_CHOOSE_LOCATION_BTN)!=null)
	     chooseLocationBtn.setStylePrimaryName( getConfiguration().getPropertyByName(LOCATION_SELECTOR_CHOOSE_LOCATION_BTN).toString());
		
		HorizontalPanel currentLocationHpPanel = new HorizontalPanel();
		
		currentLocationHpPanel.add(currentLocationTextField);
		currentLocationHpPanel.add(chooseLocationBtn);
		locationSelectorPopupPanel.add(currentLocationHpPanel);
		locationSelectorPopupPanel.center();
		locationSelectorPopupPanel.show();
		locationSelectorPopupPanel.setGlassEnabled(true);
		locationSelectorPopupPanel.setAnimationEnabled(true);
		
		if(getConfiguration().getPropertyByName(LOCATION_SELECTOR_POPUPPANEL)!=null)
		  locationSelectorPopupPanel.setStylePrimaryName(getConfiguration().getPropertyByName(LOCATION_SELECTOR_POPUPPANEL).toString());
		
		try{
			mapField = new MapField(latLng);
			mapField.setMapHeight(mapHeight);
			mapField.setMapWidth(mapWidth);
			mapField.createMapUi();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		basePanel.add(mapField);
		basePanel.setCellHorizontalAlignment(mapField, HasHorizontalAlignment.ALIGN_CENTER);
		basePanel.setCellVerticalAlignment(mapField, HasVerticalAlignment.ALIGN_MIDDLE);
		mapField.getAddressAndSet(latLng);
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				setSelectedLocation();
				
			}
		};timer.schedule(2000);
		
		
		
		chooseLocationBtn.addClickHandler(new  ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				locationSelectorPopupPanel.hide();
				
			}
		});
		
		
		MouseEventCallback mapClickCallback = new MouseEventCallback() {
	          
			@Override
				public void callback(HasMouseEvent event) {
					
				mapField.getMarker().setPosition(event.getLatLng());
				mapField.getMapWidget().getMap().panTo(event.getLatLng());
				mapField.getAddressAndSet(event.getLatLng());
				 
				 Timer timer = new Timer() {
						
						@Override
						public void run() {
							setSelectedLocation();
							
						}
					};timer.schedule(1000);
				 
				}

			
		};
		Event.addListener(mapField.getMapWidget().getMap(), "click", mapClickCallback);
	 }else{
		 HorizontalPanel horizontalPanel = new HorizontalPanel();
		 
		 if(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE)!=null)		 
		    image.setUrl(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE).toString());
		 if(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE_PRIMARYCSS)!=null)		 
		    image.setStylePrimaryName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE_PRIMARYCSS);
		 
		 horizontalPanel.add(image);
		 horizontalPanel.setCellWidth(image, "20%");
		 horizontalPanel.add(currentLocationLabel);
		 horizontalPanel.setCellWidth(currentLocationLabel, "75%");
		 horizontalPanel.setHeight("30%");
		 horizontalPanel.setWidth("100%");
		 basePanel.add(horizontalPanel);
		 image.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				try{
					mapField = new MapField(latLng);
					mapField.setMapHeight(mapHeight);
					mapField.setMapWidth(mapWidth);
					mapField.createMapUi();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				MouseEventCallback mapClickCallback = new MouseEventCallback() {
			          
					@Override
						public void callback(HasMouseEvent event) {
							
						mapField.getMarker().setPosition(event.getLatLng());
						mapField.getMapWidget().getMap().panTo(event.getLatLng());
						mapField.getAddressAndSet(event.getLatLng());
						 
						 Timer timer = new Timer() {
								
								@Override
								public void run() {
									currentLocationLabel.setText("");
									currentLocationLabel.setText(mapField.getChoosenAddress());
									
								}
							};timer.schedule(1000);
						 
						}

					
				};
				Event.addListener(mapField.getMapWidget().getMap(), "click", mapClickCallback);
				
				
				Timer timer = new Timer() {
					
					@Override
					public void run() {
						currentLocationLabel.setText("");
						currentLocationLabel.setText(mapField.getChoosenAddress());
						
						
					}
				};timer.schedule(2000);
				popupPanelForMap = new PopupPanel();
				popupPanelForMap.setAutoHideEnabled(true);
				popupPanelForMap.add(mapField);
				mapField.getAddressAndSet(latLng);
				popupPanelForMap.showRelativeTo(image);
				
			}
		});
	 }

	}
	
	@Override
	public void clearField() {
		textBox.setText("");
	}

	@Override
	public void resetField() {
		textBox.setText(getFieldValue());
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
	
	
	private void setSelectedLocation() {
		currentLocationTextField.clearField();
		currentLocationTextField.setFieldValue(mapField.getChoosenAddress());
		currentLocationTextField.resetField();
		locationSelectorPopupPanel.show();
		
	}
	
	/**
	 * creates the configuration object for a {@link}TextField
	 * @param visibleLines int - the number of visibles lines ( 1 if textbox / passwordtextbox. For textarea > 1)
	 * @param readOnly boolean - true / false
	 * @param textFieldType - type of the textField ( textbox / passwordtextbox / textarea )
	 * @param primaryCss - String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss - (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId - (optional) String the debug id for the {@link}TextField
	 * @return
	 */
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextField.TEXTFIELD_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextField.TEXTFIELD_READONLY, readOnly);
		configuration.setPropertyByName(TextField.TEXTFIELD_TYPE, textFieldType);
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEBUGID, debugId);
		return configuration;
	}


	public LatLng getLatLng() {
		return latLng;
	}


	public void setLatLong(LatLng latLng) {
		this.latLng = latLng;
	}

	public String getMapWidth() {
		return mapWidth;
	}
	 /**
     * Set Width like "10px" or "100%" etc
     * @param mapWidth
     */
	public void setMapWidth(String mapWidth) {
		this.mapWidth = mapWidth;
	}

	public String getMapHeight() {
		return mapHeight;
	}
	 /**
     * Set Height like "10px" or "100%" etc
     * @param mapWidth
     */
	public void setMapHeight(String mapHeight) {
		this.mapHeight = mapHeight;
	}

	public boolean isMapMode() {
		return isMapMode;
	}

	public void setMapMode(boolean mapMode) {
		this.isMapMode = mapMode;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

}