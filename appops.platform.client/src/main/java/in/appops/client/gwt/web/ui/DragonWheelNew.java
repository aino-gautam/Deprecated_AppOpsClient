/**
 * 
 */
package in.appops.client.gwt.web.ui;

import in.appops.client.gwt.web.ui.DragonWheel.DragonWheelWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
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
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class DragonWheelNew extends Composite implements MouseWheelHandler,MouseOverHandler,MouseOutHandler, MouseMoveHandler{
	
	private int xWheelCenter = 300;
	private int yWheelCenter = 200;
	private int zWheelcenter = 200;
	
	private int elevateAngle = 60 ;
	private int wheelSpeed = 30 ;
	private boolean elevatedView = false ;
	
	private Map<String , Cylinder> cylinderMap = new HashMap<String, Cylinder>();
	
	private int scalingConstant = 200; // used for calculating opacity and scale
	private double widget_gap = 150;
	private int startAngle = 30;
	private double currentAngle = startAngle * (Math.PI /180);
	private double radius = 180; // in ?
	private double radius_y = 70; // in pixels - elevation angle decision maker
	public double shift_v = 10;// Math.PI/1.2	; // 
	private double height = 0, width = 0.0;
	
	private AbsolutePanel parent ;
	
	public DragonWheelNew(){
		parent = new AbsolutePanel();
		parent.addDomHandler(this, MouseWheelEvent.getType());
		
		DOM.setStyleAttribute(parent.getElement(), "position", "absolute");
		initWidget(parent);
		parent.setStylePrimaryName("dragonWheel");
	}
	
	public int getElevateAngle() {
		return elevateAngle;
	}

	/**
	 * sets elevation angle
	 * @param elevateAngle
	 */
	public void setElevateAngle(int elevateAngle) {
		this.elevateAngle = elevateAngle;
	}

	public int getWheelSpeed() {
		return wheelSpeed;
	}

	/**
	 * Change speed of wheel between 1 to 100
	 * @param wheelSpeed
	 */
	public void setWheelSpeed(int wheelSpeed) {
		this.wheelSpeed = wheelSpeed;
	}

	public boolean isElevatedView() {
		return elevatedView;
	}

	/**
	 * Call this method to toggle the elevated view of the cylinder
	 * @param elevatedView true = elevated view
	 */
	public void setElevatedView(boolean elevatedView) {
		this.elevatedView = elevatedView;
	}
	
	public void addCylinder(Cylinder cylinder){
		cylinderMap.put(cylinder.getName(), cylinder);
	}

	public void removeCylinder(Cylinder cyl){
		cylinderMap.remove(cyl.getName());
	}
	
	public Cylinder getCylinder(String name){
		return cylinderMap.get(name);
	}

	@Override
	public void onMouseOut(MouseOutEvent arg0) {
		
	}

	@Override
	public void onMouseOver(MouseOverEvent arg0) {
		
	}

	@Override
	public void onMouseWheel(MouseWheelEvent arg0) {
		
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.MouseMoveHandler#onMouseMove(com.google.gwt.event.dom.client.MouseMoveEvent)
	 */
	@Override
	public void onMouseMove(MouseMoveEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 
	 */
	private void initWidgetPositions() {
		
		Cylinder topMost = getTopMost();
		
		widget_gap = Math.PI/(10/3);
/**		
		for ( Row row : topMost.getRowList())
		
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
		*/
	}
	

	public Cylinder getTopMost(){
		for (Cylinder cyl : cylinderMap.values()){
			if (cyl.getOrder() == 0)
				return cyl;
			else continue ;
		}
		return null ;
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

		 
}
