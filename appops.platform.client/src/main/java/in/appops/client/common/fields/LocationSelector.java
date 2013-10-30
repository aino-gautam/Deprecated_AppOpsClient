package in.appops.client.common.fields;

import in.appops.client.common.core.EntityReceiver;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.shared.Configuration;

import java.util.List;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.HasMouseEvent;
import com.google.gwt.maps.client.event.MouseEventCallback;
import com.google.gwt.maps.client.geocoder.Geocoder;
import com.google.gwt.maps.client.geocoder.GeocoderCallback;
import com.google.gwt.maps.client.geocoder.GeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasGeocoder;
import com.google.gwt.maps.client.geocoder.HasGeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasGeocoderResult;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LocationSelector extends Composite implements Field,EntityReceiver, ClickHandler {

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	private VerticalPanel basePanel;
	private Entity entity;
	private LatLng latLng ;
	private MapField mapField;
	private TextField currentLocationTextField ;
	private PopupPanel popupPanelForMap;
	private PopupPanel popupPanelForMapSearch;
	private Image image ;
	private Image searchIconImage ;
	private Label currentLocationLabel ;
	private Image doneBtn;
	private Image chooseLocationBtn;
	private FlexTable  flexTable ;
	private HorizontalPanel hpPanel ;
	
	public static final String LOCATION_SELECTOR_POPUPPANEL = "locationSelectorPopupPanel";
	public static final String LOCATION_SELECTOR_CHOOSE_LOCATION_BTN = "chooseLocationBtn";
	public static final String LOCATION_SELECTOR_CURRENT_LOCATION_TEXTFIELD = "currentLocationField";
	public static final String LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE = "currentLocationImage";
	public static final String LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE_PRIMARYCSS = "currentLocationImagePrimaryCss";
	public static final String FEAR_IN_DOWN = "fadeInDown";
	public static final String LATLNG = "latLng";
	public static final String MAP_WIDTH = "mapWidth";
	public static final String MAP_HEIGHT = "mapHeight";
	public static final String MAP_ZOOM = "mapZoom";
	public static final String HAND_CURSOR = "appops-handCursor";
	public static final String CHANGE_LOCATION_IMAGE_URL = "changelocationUrl";
	public static final String DONE_SELECTION_IMAGE_URL = "doneSelctionUrl";
	public static final String WEB_LOCATION_SELECTOR="webLocationSelector";
	public static final String TOUCH_LOCATION_SELECTOR="touchLocationSelector";
	
	private String mapWidth;
	private String mapHeight;
	private boolean isMapMode = false;
	private static String currentSelectedLocation ; 
	
	public LocationSelector(){
		basePanel = new VerticalPanel();
		
		currentLocationLabel = new Label();
		image = new Image();
		basePanel.setWidth("100%");
		basePanel.setHeight("100%");
				
		initWidget(basePanel);
	}
	
	@Override
	public void create() {
		basePanel.clear();
		
	 if(!isMapMode){	
		 flexTable = new FlexTable(); 
		
		currentLocationTextField = new TextField();
		currentLocationTextField.setFieldValue("Current location");
		currentLocationTextField.setConfiguration(getTextFieldConfiguration(1, false, TextFieldConstant.TFTYPE_TXTBOX, getConfiguration().getPropertyByName(TextFieldConstant.BF_PCLS).toString(), null, null));
	
		try{
			currentLocationTextField.create();
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		if(getConfiguration().getPropertyByName(CHANGE_LOCATION_IMAGE_URL)!=null){
	        chooseLocationBtn = new  Image(getConfiguration().getPropertyByName(CHANGE_LOCATION_IMAGE_URL).toString());
		    chooseLocationBtn.setTitle("Change Location");
		}
	    
	    if(getConfiguration().getPropertyByName(DONE_SELECTION_IMAGE_URL)!=null)
	       doneBtn = new  Image(getConfiguration().getPropertyByName(DONE_SELECTION_IMAGE_URL).toString());
	       doneBtn.setTitle("Done Selection");

	    
	    if( getConfiguration().getPropertyByName(LOCATION_SELECTOR_CHOOSE_LOCATION_BTN)!=null){
	       chooseLocationBtn.setStylePrimaryName( getConfiguration().getPropertyByName(LOCATION_SELECTOR_CHOOSE_LOCATION_BTN).toString());
	       doneBtn.setStylePrimaryName( getConfiguration().getPropertyByName(LOCATION_SELECTOR_CHOOSE_LOCATION_BTN).toString());
	       chooseLocationBtn.addStyleName(HAND_CURSOR);
	       doneBtn.addStyleName(HAND_CURSOR);
	    }
		
	    HorizontalPanel currentLocationHpPanel = new HorizontalPanel();
		currentLocationHpPanel.setWidth("100%");
		currentLocationHpPanel.add(currentLocationTextField);
		currentLocationHpPanel.setCellWidth(currentLocationTextField, "40%");
		//currentLocationHpPanel.add(chooseLocationBtn);
		//currentLocationHpPanel.setCellWidth(chooseLocationBtn, "6%");
		currentLocationHpPanel.add(doneBtn);
		currentLocationHpPanel.setCellWidth(doneBtn, "50%");
		
		
		
		flexTable.setWidget(0, 0,currentLocationHpPanel);
		
		
		if(getConfiguration().getPropertyByName(LOCATION_SELECTOR_POPUPPANEL)!=null)
			currentLocationHpPanel.setStylePrimaryName(getConfiguration().getPropertyByName(LOCATION_SELECTOR_POPUPPANEL).toString());
		
		try{
			 if (AppEnviornment.getCurrentGeolocation() != null) {
//					LocationProvider.getInstance().getLocation(LocationProvider.WEB, this);
				GeoLocation geolocation = AppEnviornment.getCurrentGeolocation();
					
				if(geolocation != null){
					LatLng latLng = new LatLng(geolocation.getLatitude(), geolocation.getLongitude());
					this.latLng = latLng;
					createMapField();
				}
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		chooseLocationBtn.addClickHandler(new  ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//locationSelectorPopupPanel.hide();
				
			}
		});
		
		
		
	 }else{
		 
		 HorizontalPanel horizontalPanel = new HorizontalPanel();
		 
		 if(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE)!=null)		 
		    image.setUrl(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE).toString());
		 if(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE_PRIMARYCSS)!=null)		 
		    image.setStylePrimaryName(getConfiguration().getPropertyByName(LOCATION_SELECTOR_CURRENT_LOCATION_IMAGE_PRIMARYCSS).toString());
		 
		 horizontalPanel.add(image);
		 horizontalPanel.setCellWidth(image, "8%");
		 horizontalPanel.add(currentLocationLabel);
		 horizontalPanel.setCellWidth(currentLocationLabel, "80%");
		 horizontalPanel.setHeight("30%");
		 horizontalPanel.setWidth("100%");
		 basePanel.add(horizontalPanel);
		 
		 try{
			 if (AppEnviornment.getCurrentGeolocation() != null) {
//					LocationProvider.getInstance().getLocation(LocationProvider.WEB, this);
				GeoLocation geolocation = AppEnviornment.getCurrentGeolocation();
					
				if(geolocation != null){
					LatLng latLng = new LatLng(geolocation.getLatitude(), geolocation.getLongitude());
					this.latLng = latLng;
					createMapField();
				}
			 }
				
			}catch(Exception e){
				e.printStackTrace();
			}
		 image.addClickHandler(this);
	 }

	}
	
	
	public MapField createMapField(){
		
		mapField = new MapField(latLng);
		mapField.setMapHeight(getConfiguration().getPropertyByName(MAP_HEIGHT).toString());
		mapField.setMapWidth(getConfiguration().getPropertyByName(MAP_WIDTH).toString());
		mapField.setMapZoomParameter(Integer.parseInt(getConfiguration().getPropertyByName(MAP_ZOOM).toString()));
		mapField.addHandle(this);
		mapField.createMapUi();
		mapField.getAddressAndSet(latLng);
		
		
		if(!isMapMode){
			
			Timer timer = new Timer() {
				
				@Override
				public void run() {
					setSelectedLocation();
					
				}
			};timer.schedule(2000);
			flexTable.setWidget(2, 0,mapField);
			basePanel.add(flexTable);
			basePanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
			basePanel.setCellVerticalAlignment(flexTable, HasVerticalAlignment.ALIGN_MIDDLE);
			
			MouseEventCallback mapClickCallback = new MouseEventCallback() {
		          
				@Override
					public void callback(HasMouseEvent event) {
						latLng =(LatLng) event.getLatLng();
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
			Timer timer = new Timer() {
				
				@Override
				public void run() {
					currentLocationLabel.setText("");
					currentLocationLabel.setText(mapField.getChoosenAddress());
					setCurrentSelectedLocation(mapField.getChoosenAddress());
					
				}
			};timer.schedule(2000);
			
		}
		
		return mapField;
		
	}
	
	@Override
	public void clear() {
		textBox.setText("");
	}

	@Override
	public void reset() {
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
		currentLocationTextField.clear();
		currentLocationTextField.setFieldValue(mapField.getChoosenAddress());
		currentLocationTextField.reset();
		setCurrentSelectedLocation(mapField.getChoosenAddress());
		//locationSelectorPopupPanel.show();
		
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
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, visibleLines);
		configuration.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}

	
	public void displaySearchEdAddressIntoMapWidget(String address){
		final HasGeocoderRequest gRequest = new GeocoderRequest();
        gRequest.setAddress(address);
        
        final HasGeocoder geocoder = new Geocoder();
        geocoder.geocode(gRequest, new GeocoderCallback() {
          
          @Override
          public void callback(List<HasGeocoderResult> responses, String status) {
            if (status.equals("OK")) {
              final HasGeocoderResult gResult = responses.get(0);
              final HasLatLng gLatLng = gResult.getGeometry().getLocation();
              latLng = (LatLng) gLatLng; 
                mapField.getMarker().setPosition(latLng);
				mapField.getMapWidget().getMap().panTo(latLng);
				mapField.getAddressAndSet(gLatLng);
				 Timer timer = new Timer() {
						
						@Override
						public void run() {
							currentLocationLabel.setText("");
							currentLocationLabel.setText(mapField.getChoosenAddress());
							
						}
					};timer.schedule(1000);
				 
				
            } else {
              Window.alert("Geocoder failed with response : " + status);
            }
          }
        });
	}
	

	public LatLng getLatLng() {
		return latLng;
	}


	public void setLatLong(LatLng latLng) {
		this.latLng = latLng;
	}

	public void setCoordinates(Coordinates coords){
		
		latLng = new LatLng(coords.getLatitude(), coords.getLongitude());
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
		int eventType=event.getEventType();
		if(eventType == FieldEvent.LOCATION_RECIEVED){
			currentSelectedLocation=(String) event.getEventData();
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	
	public Image getDoneBtn() {
		return doneBtn;
	}

	public void setDoneBtn(Image doneBtn) {
		this.doneBtn = doneBtn;
	}

	public Label getCurrentLocationLabel() {
		return currentLocationLabel;
	}

	public void setCurrentLocationLabel(Label currentLocationLabel) {
		this.currentLocationLabel = currentLocationLabel;
	}

	public TextField getCurrentLocationTextField() {
		return currentLocationTextField;
	}

	public void setCurrentLocationTextField(TextField currentLocationTextField) {
		this.currentLocationTextField = currentLocationTextField;
	}

	public static String getCurrentSelectedLocation() {
		return currentSelectedLocation;
	}

	public static void setCurrentSelectedLocation(String currentSelectedLocation) {
		LocationSelector.currentSelectedLocation = currentSelectedLocation;
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityReceived(Entity entity) {
		GeoLocation geolocation = (GeoLocation) entity.getPropertyByName("geolocation");
		
		if(geolocation != null){
			LatLng latLng = new LatLng(geolocation.getLatitude(), geolocation.getLongitude());
			this.latLng = latLng;
			createMapField();
		}
		
	}

	@Override
	public void onEntityUpdated(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		  if(sender instanceof Image){
			if(sender.equals(image)){   
			MouseEventCallback mapClickCallback = new MouseEventCallback() {
		          
				@Override
					public void callback(HasMouseEvent event) {
					latLng =(LatLng) event.getLatLng();
					mapField.getMarker().setPosition(event.getLatLng());
					mapField.getMapWidget().getMap().panTo(event.getLatLng());
					mapField.getAddressAndSet(event.getLatLng());
					 
					 Timer timer = new Timer() {
							
							@Override
							public void run() {
								currentLocationLabel.setText("");
								currentLocationLabel.setText(mapField.getChoosenAddress());
								setCurrentSelectedLocation(mapField.getChoosenAddress());
								
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
					setCurrentSelectedLocation(mapField.getChoosenAddress());
					
				}
			};timer.schedule(2000);
			
			hpPanel = new HorizontalPanel(); 
			popupPanelForMap = new PopupPanel();
			
			searchIconImage = new Image("images/searchIconLight.png");
			popupPanelForMap.setAutoHideEnabled(true);
			hpPanel.add(searchIconImage);
			hpPanel.add(mapField);
			
			searchIconImage.setStylePrimaryName("searchIconForMap");
			//hpPanel.setCellVerticalAlignment(searchIconImage, HasVerticalAlignment.ALIGN_MIDDLE);
			//hpPanel.setCellHorizontalAlignment(searchIconImage, HasHorizontalAlignment.ALIGN_RIGHT);
			popupPanelForMap.add(hpPanel);
			//hpPanel.setBorderWidth(1);
			mapField.addStyleName("popupPanelForMap_false");
			popupPanelForMap.setStylePrimaryName(FEAR_IN_DOWN);
			//popupPanelForMap.addStyleName("popupPanelForMap_false");
			mapField.getAddressAndSet(latLng);
			popupPanelForMap.showRelativeTo(image);
			//TODO the search box css remain
			
			//searchIconImage.setAltText("›");
			//searchIconImage.setStylePrimaryName("button");
			searchIconImage.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					popupPanelForMapSearch = new PopupPanel();
					
					popupPanelForMapSearch.setAutoHideEnabled(true);
					//popupPanelForMapSearch.setStylePrimaryName("slidingWindow");
					popupPanelForMapSearch.setStylePrimaryName("fadeInRight");
					//popupPanelForMapSearch.addStyleName("searchBoxInMap");
					/*ToggleButton toggler = new ToggleButton(searchIconImage,
							searchIconImage);
					PinnedPanel pinnedPanel = new PinnedPanel(200, toggler, popupPanelForMapSearch,popupPanelForMapSearch);*/
					final TextBox textField = new TextBox();
					
					popupPanelForMapSearch.add(textField);
					//popupPanelForMapSearch.show();
					//popupPanelForMapSearch.center();
					popupPanelForMapSearch.showRelativeTo(searchIconImage);
					
					
					
					//popupPanelForMapSearch.setPopupPosition(searchIconImage.getAbsoluteLeft()-190, mapField.getAbsoluteTop()+50);
					
					textField.addKeyDownHandler(new KeyDownHandler() {
						
						@Override
						public void onKeyDown(KeyDownEvent event) {
							switch (event.getNativeKeyCode()) {
					        case KeyCodes.KEY_ENTER:
					           displaySearchEdAddressIntoMapWidget(textField.getText());
					           popupPanelForMapSearch.hide();
							}
							
						}
					});
					
					
					
				}
			});
			}
		  }
	}
	
	public void isCurrLocationLabelVisible(boolean visible) {
		currentLocationLabel.setVisible(visible);
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}