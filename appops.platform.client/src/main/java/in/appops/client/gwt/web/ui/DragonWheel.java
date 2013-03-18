/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 06-Mar-2013
 *
 */
public class DragonWheel extends Composite implements MouseWheelHandler,MouseOverHandler,MouseOutHandler{
	
	private LinkedHashMap<Widget , DragonWheelWidget> dWidgetMap = new LinkedHashMap<Widget, DragonWheelWidget>();
	private AbsolutePanel parent ;
	private ArrayList<Widget> widgetList; 
	
	private int xcenter = 300;
	private int ycenter = 200;
	private int zcenter = 200;
	private int scalingConstant = 200; // used for calculating opacity and scale
	private double widget_gap = 150;
	private int startAngle = 30;
	private double currentAngle = startAngle * (Math.PI /180);
	private double radius = 180; // in ?
	private double radius_y = 70; // in pixels - elevation angle decision maker
	double shift_v = 10;// Math.PI/1.2	; // 
	private double height = 0, width = 0.0;
	
	public DragonWheel(){
		parent = new AbsolutePanel();
		parent.addDomHandler(this, MouseWheelEvent.getType());
		
		DOM.setStyleAttribute(parent.getElement(), "position", "absolute");
		initWidget(parent);
		parent.setStylePrimaryName("dragonWheel");
	}
	
	/**
	 * Call this method after all initialisation for initial layout. It lays the widgets according to current positions
	 */
	public void layOutDragonWheel() {
		
		//radius_y = (80*radius)/radius;
		
		initWidgetPositions();
		int indexOfWidget = 0;
		
		for(Widget w : dWidgetMap.keySet()){
			DragonWheelWidget dww = dWidgetMap.get(w);
			
			Widget wactual = dww.getWrapped();
			wactual.setPixelSize(dww.getWidth(), dww.getHeight()); // 
			wactual.setVisible(true);
			
			wactual.addDomHandler(this, MouseOverEvent.getType());
			wactual.addDomHandler(this, MouseOutEvent.getType());
			
			// widget have different scale when they are in different position on the wheel
			double scale = scalingConstant/ (scalingConstant + Math.sin(startAngle + indexOfWidget * widget_gap + shift_v ) * radius + zcenter);
			
			DOM.setStyleAttribute(wactual.getElement(), "opacity", String.valueOf(scale));
			wactual.getElement().getStyle().setFontSize(scale, Unit.EM);
						
			parent.add(wactual,dww.getLeftOffset(),dww.getTopOffset());
			
			indexOfWidget++;
		}
	}

	/**
	 * 
	 */
	private void initWidgetPositions() {
		
		widget_gap = Math.PI/(widgetList.size()/3);
		
		if (widgetList.size() > 3) {
			
			for (int i = 0; i < widgetList.size(); i++) {
				Widget widget = widgetList.get(i);
				
				int left = (int) Math.round((Math.cos(startAngle + i * widget_gap + shift_v ) * radius + xcenter));
				int top = (int) Math.round((- Math.sin(startAngle + i * widget_gap + shift_v )* radius_y + ycenter));
				
				System.out.println("widget No "+ i +" at left="+left +"top="+top);
				
				int z = (int) Math.round((Math.sin(startAngle + i * widget_gap+shift_v) * radius + zcenter));
				
				DragonWheelWidget dww = new DragonWheelWidget(left, top, z,100, 100);
				dww.setWrapped(widget);

				dWidgetMap.put(widget, dww);
			}
		}
	}
	
	private void initWidgetPosition(Widget w){
		
	}

	/**
	 * @return
	 */
	
	public void setWidgetList(ArrayList<Widget> widgets){
		widgetList = widgets ;
	}
	

	public DragonWheelWidget removeWidget(Widget w){
		return dWidgetMap.remove(w);
	}
	
	public void addWidget(Widget w){
		
		initWidgetPosition(w);
	}
	
	
	final class DragonWheelWidget {
		
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


		int xLeft = 0 ;
		int yTop = 0 ;
		
		int zOrder = 1 ;
		
		int width = 100 ;
		int height = 100 ;
		
		Widget wrapped = null ;
		
		DragonWheelWidget(int x , int y , int z , int w , int h){
			xLeft = x ;
			yTop = y ;
			zOrder = z;
			width = w ;
			height = h ;
		}
	}


	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		int move = event.getDeltaY(); 
		int indexOfWidget = 0;
		
		if (move < 0) {
			// mouse wheel is moving north .
			currentAngle = currentAngle - Math.PI / shift_v;
		} else {
			// mouse wheel is moving south
			currentAngle = currentAngle + Math.PI / shift_v;
		}
		
		for (Map.Entry<Widget, DragonWheelWidget> e : dWidgetMap.entrySet()) {
			Widget widget = e.getKey();
			DragonWheelWidget dww = e.getValue();
			int newXpos = (int) Math.round(Math.cos(currentAngle + indexOfWidget* widget_gap+shift_v )* radius + xcenter);
			int newYPos = (int) Math.round(-Math.sin(currentAngle + indexOfWidget* widget_gap+shift_v)* radius_y + ycenter);

			double scale = scalingConstant/ (scalingConstant + Math.sin(currentAngle + indexOfWidget * widget_gap+shift_v )* radius + zcenter);
			
			DOM.setStyleAttribute(widget.getElement(), "opacity",String.valueOf(scale));
			widget.getElement().getStyle().setFontSize(scale, Unit.EM);
								
			dww.setLeftOffset(newXpos);
			dww.setTopOffset(newYPos);
			
			
			parent.add(widget, newXpos, newYPos);
			indexOfWidget++;
		}
	
	}
	@Override
	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();
		sender.setStylePrimaryName("wheelWidget");
		sender.getElement().getStyle().setHeight(height, Unit.PX);
		sender.getElement().getStyle().setWidth(width, Unit.PX);
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		sender.setStylePrimaryName("center");
		
		height = Double.parseDouble(sender.getElement().getStyle().getHeight().replace("px", ""));
		width = Double.parseDouble(sender.getElement().getStyle().getWidth().replace("px", ""));
		
		sender.getElement().getStyle().setHeight(10, Unit.PC);
		sender.getElement().getStyle().setWidth(10, Unit.PC);
		
		//sender.getElement().getStyle().setZIndex(99);
	}


}
