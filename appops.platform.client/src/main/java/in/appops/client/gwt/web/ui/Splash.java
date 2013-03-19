package in.appops.client.gwt.web.ui;

import in.appops.client.touch.Screen;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class Splash extends Composite implements ClickHandler{
	
	private LinkedHashMap<Widget , SplashWidget> splashWidgetMap = new LinkedHashMap<Widget, SplashWidget>();
	private LinkedHashMap<String, HashMap<Widget, Screen>> widgetMap;
	private AbsolutePanel parent ;

	
	double shift_v = 10;// Math.PI/1.2	; //
	
	
	private static final int SIZE = 350;
    private int a = SIZE / 2;
    private int b = a;
    private int r = a - SIZE / 10;

	private HorizontalPanel centerHp;
	private Image centerImage = new Image();;
	
	public Splash(){
		parent = new AbsolutePanel();
		initWidget(parent);
	}
	
	public void setMainImage(String imageUrl){
		centerImage.setUrl(imageUrl);
	}
	
	public void initialize(){
		parent.setStylePrimaryName("splash");
		centerHp = new HorizontalPanel();
		
		centerHp.add(centerImage);
		centerHp.setCellHorizontalAlignment(centerImage, HasHorizontalAlignment.ALIGN_CENTER);
		centerHp.setCellVerticalAlignment(centerImage, HasVerticalAlignment.ALIGN_MIDDLE);
		
		centerHp.setStylePrimaryName("splash-centerbutton");
		
		
		parent.add(centerHp,25, 25);
		centerImage.setStylePrimaryName("splash-centerImage");
		
		Timer refreshTimer = new Timer() {
		      @Override
		      public void run() {
		    	  centerHp.addStyleName("splash-centerbuttonturn");
		    	 
		      }
		    };
		    refreshTimer.schedule(1000);
		    
		    Timer refreshTimer2 = new Timer() {
			      @Override
			      public void run() {
			    	  createSplash();
			      }
			    };
			    refreshTimer2.schedule(3000);
		
	}
	
	public void createSplash(){
		initWidgetPositions();

		for(Widget w : splashWidgetMap.keySet()){
			SplashWidget sw = splashWidgetMap.get(w);
			
			sw.getWrapped().setVisible(true);
			sw.getWrapped().addDomHandler(this, ClickEvent.getType());
			parent.add(sw.getWrapped(), sw.getLeftOffset(),sw.getTopOffset());
			sw.getWrapped().addStyleName("splashedwidgetturn");

		}
	}
	
	
	private void initWidgetPositions(){
		
		a = centerHp.getOffsetWidth() / 3;
        b = centerHp.getOffsetHeight() / 3;
		
        int i=0;
        for(String name : widgetMap.keySet()){
        	HashMap<Widget, Screen> map = widgetMap.get(name);
        	for(Widget widget : map.keySet()){
	        	double t = 2 * Math.PI * i / widgetMap.size();
	            int x = (int) Math.round(a + r * Math.cos(t));
	            int y = (int) Math.round(b + r * Math.sin(t));
	            
	            System.out.println("widget No "+ i +" at X="+x +"Y="+y);
	            
	            SplashWidget sw = new SplashWidget(x,y);
				sw.setWrapped(widget);
				sw.setName(name);
				sw.setScreen(map.get(widget));
				
				splashWidgetMap.put(widget, sw);
				i++;
        	}
			
        }
	}

	public SplashWidget removeWidget(Widget w){
		return splashWidgetMap.remove(w);
	}
	
	public void addWidget(Widget w, String name, Screen screen){
		if(widgetMap == null)
			widgetMap = new LinkedHashMap<String, HashMap<Widget,Screen>>();
		
		HashMap<Widget, Screen> screenmap = new HashMap<Widget, Screen>();
		screenmap.put(w, screen);
		widgetMap.put(name, screenmap);
	}
	
	final class SplashWidget {
			
			public int getLeftOffset() {
				return xLeft;
			}
	
	
			public void setLeftOffset(int xLeft) {
				this.xLeft = xLeft;
			}
	
	
			public int getTopOffset() {
				return yTop;
			}
	
	
			public void setTopOffset(int yTop) {
				this.yTop = yTop;
			}
	
	
			public int getOrder() {
				return zOrder;
			}
	
	
			public void setOrder(int zOrder) {
				this.zOrder = zOrder;
			}
	
	
			public int getWidth() {
				return width;
			}
	
	
			public void setWidth(int width) {
				this.width = width;
			}
	
	
			public int getHeight() {
				return height;
			}
	
	
			public void setHeight(int height) {
				this.height = height;
			}
	
	
			public Widget getWrapped() {
				return wrapped;
			}
	
	
			public void setWrapped(Widget wrapped) {
				this.wrapped = wrapped;
			}

			public void setName(String name){
				this.name = name;
			}
			
			public void setScreen(Screen screen){
				this.screen = screen;
			}
			
			public Screen getScreen(){
				return this.screen;
			}
			
			public String getName(){
				return this.name;
			}
			
			int xLeft = 0 ;
			int yTop = 0 ;
			
			int zOrder = 1 ;
			
			int width = 100 ;
			int height = 100 ;
			
			Widget wrapped = null ;
			Screen screen;
			String name;
			
			SplashWidget(int x , int y){
				xLeft = x ;
				yTop = y ;
			}

		}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget)event.getSource();
		for(Widget w : splashWidgetMap.keySet()){
			if(source == w){
				SplashWidget sw = splashWidgetMap.get(w);
				
				PopupPanel popup = new PopupPanel(true);
				
				popup.setAnimationEnabled(true);
				popup.setGlassEnabled(true);
				
				Screen sc = sw.getScreen();
				sc.createScreen();
				popup.add(sc);
				break;
			}
		}
	}
}
