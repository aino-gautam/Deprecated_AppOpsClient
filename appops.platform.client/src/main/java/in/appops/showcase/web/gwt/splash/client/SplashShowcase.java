package in.appops.showcase.web.gwt.splash.client;


import in.appops.client.gwt.web.ui.Splash;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class SplashShowcase implements EntryPoint {
	
		@Override
		public void onModuleLoad() {
			Splash splash = new Splash();
			//ContactDetailsScreen sc = new ContactDetailsScreen();
			
			splash.addWidget(getWidgets("Security"), "Security", null);
			splash.addWidget(getWidgets("Contacts"), "Contacts", null);
			splash.addWidget(getWidgets("Widget settings"), "Widget settings", null);
			splash.setMainImage("imgaes/opptin.png");
			splash.initialize();
			
			RootPanel.get("nameFieldContainer").add(splash);
			/*splash.setWidth("100%") ;*/
			splash.setHeight("100%") ;
		}
		
		public Widget getWidgets(String str){
			
				HorizontalPanel hPanel = new HorizontalPanel();
				Label lbl = new Label(str) ; 
				hPanel.add(lbl);
				hPanel.setStylePrimaryName("splashedwidget");
				hPanel.setCellHorizontalAlignment(lbl, HasHorizontalAlignment.ALIGN_CENTER);
				hPanel.setCellVerticalAlignment(lbl, HasVerticalAlignment.ALIGN_MIDDLE);
				
			return hPanel ;
		}
	}

