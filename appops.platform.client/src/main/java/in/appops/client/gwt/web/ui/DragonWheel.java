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
@SuppressWarnings("deprecation")
public class DragonWheel extends Composite implements MouseWheelHandler,MouseOverHandler,MouseOutHandler,ClickHandler{
	
	private LinkedHashMap<Widget , DragonWheelWidget> dWidgetMap = new LinkedHashMap<Widget, DragonWheelWidget>();
	private AbsolutePanel parent ;
	private ArrayList<Widget> widgetList; 
	
	private int xcenter=300;
	private int ycenter=200;
	private int zcenter=200;
	private int fl=200;
	private double widget_gap = 0.0;
	private int startAngle=0;
	private double currentAngle=startAngle * Math.PI /180;
	private double radius=200;
	private double radius_y =150;
	double shift_v = Math.PI/2 ;
	private Widget prevSelectedWidget;
	private double top = 0,left = 0.0;
	
	public DragonWheel(){
		parent = new AbsolutePanel();
		parent.addDomHandler(this, MouseWheelEvent.getType());
		
		DOM.setStyleAttribute(parent.getElement(), "position", "absolute");
		initWidget(parent);
		parent.setStylePrimaryName("dragonWheel");
	}
	
	/**
	 * 
	 */
	public void layOutDragonWheel() {
		
		//radius_y = (80*radius)/radius;
		
		initWidgetPositions();
		int indexOfWidget = 0;
		
		for(Widget w : dWidgetMap.keySet()){
			DragonWheelWidget dww = dWidgetMap.get(w);
			
			Widget wactual = dww.getWrapped();
			wactual.setPixelSize(dww.getWidth(), dww.getHeight());
			wactual.setVisible(true);
			
			wactual.addDomHandler(this, ClickEvent.getType());
			//wactual.addDomHandler(this, MouseOutEvent.getType());
			
			double scale = fl/ (fl + Math.sin(startAngle + indexOfWidget * widget_gap+shift_v ) * radius + zcenter);
			
			DOM.setStyleAttribute(wactual.getElement(), "opacity",String.valueOf(scale));
			wactual.getElement().getStyle().setFontSize(scale, Unit.EM);
						
			parent.add(wactual,dww.getLeftOffset(),dww.getTopOffset());
			
			indexOfWidget++;
		}
	}

	/**
	 * 
	 */
	private void initWidgetPositions() {
		widget_gap = Math.PI/(widgetList.size()/2);
		
		if (widgetList.size() > 3) {
			
			for (int i = 0; i < widgetList.size(); i++) {
				Widget widget = widgetList.get(i);
				int left = (int) Math.round((Math.cos(startAngle + i * widget_gap+shift_v ) * radius + xcenter));
				int top = (int) Math.round((-Math.sin(startAngle + i * widget_gap+shift_v )* radius_y + ycenter));
				
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
	private int getWheelWidth() {
		return super.getOffsetWidth();
	}
	
	private int getWheelHeight(){
		return super.getOffsetHeight();
	}

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
			currentAngle = currentAngle - Math.PI / 180;
		} else {
			// mouse wheel is moving south
			currentAngle = currentAngle + Math.PI /180;
		}
		
		for (Map.Entry<Widget, DragonWheelWidget> e : dWidgetMap.entrySet()) {
			Widget widget = e.getKey();
			DragonWheelWidget dww = e.getValue();
			int newXpos = (int) Math.round(Math.cos(currentAngle + indexOfWidget* widget_gap+shift_v )* radius + xcenter);
			int newYPos = (int) Math.round(-Math.sin(currentAngle + indexOfWidget* widget_gap+shift_v)* radius_y + ycenter);
			int z = (int) Math.round(Math.sin(currentAngle + indexOfWidget* widget_gap+shift_v)* radius + zcenter);

			double scale = fl/ (fl + Math.sin(currentAngle + indexOfWidget * widget_gap+shift_v )* radius + zcenter);
			
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
	}
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
			
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if(prevSelectedWidget ==null){
			prevSelectedWidget = (Widget) event.getSource();
			 
			top = Double.parseDouble(prevSelectedWidget.getElement().getStyle().getTop().replace("px", ""));
			left = Double.parseDouble(prevSelectedWidget.getElement().getStyle().getLeft().replace("px", ""));
		}else{
			prevSelectedWidget.setStylePrimaryName("wheelWidget");
			prevSelectedWidget.getElement().getStyle().setTop(top, Unit.PX);
			prevSelectedWidget.getElement().getStyle().setLeft(left, Unit.PX);
			
			prevSelectedWidget = sender;
			
			top = Double.parseDouble(prevSelectedWidget.getElement().getStyle().getTop().replace("px", ""));
			left = Double.parseDouble(prevSelectedWidget.getElement().getStyle().getLeft().replace("px", ""));
			
		}
			prevSelectedWidget.setStylePrimaryName("center");
			
			//need to change the calculation..
			prevSelectedWidget.getElement().getStyle().setTop(ycenter, Unit.PX);
			prevSelectedWidget.getElement().getStyle().setLeft(xcenter, Unit.PX);
			
	}

}
