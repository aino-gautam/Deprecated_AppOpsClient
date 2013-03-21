/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class Row extends AbsolutePanel implements MouseWheelHandler{

	private int rowHeight ; 
	private int rowWidth ;
	private double widgetSpacing ;
	private int numWidgetsVisible ;
	private boolean independent = false ;
	private int xLeft = 0 ; 
	private int yTop = 0 ;
	private String name ;
	private String displayName ;
	private int rowPosition ;
	private Cylinder parentCylinder = null ;
	private LinkedHashSet<Widget> nextWidgetSet ;
	
	private int scalingConstant = 200; // used for calculating opacity and scale
	private int startAngle = 30;
	private double currentAngle = startAngle * (Math.PI /180);
	private double radius = 200; // in px
	public double elevation_angle = 40; // in pixels - elevation angle decision maker
	public double speed = 10	; // 
	private int zcenter = 200;
	private LinkedHashMap<Widget , DragonWheelWidget> dWidgetMap = new LinkedHashMap<Widget, DragonWheelWidget>();
	
	public Row() {
		addDomHandler(this, MouseWheelEvent.getType());
		setStylePrimaryName("rowPanel");
	}
	
	public Row(String name){
		this.name = name;
		addDomHandler(this, MouseWheelEvent.getType());
		setStylePrimaryName("rowPanel");
	}
	
	public int getRowPosition() {
		return rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int getRowWidth() {
		return rowWidth;
	}

	public void setRowWidth(int rowWidth) {
		this.rowWidth = rowWidth;
	}

	public double getWidgetSpacing() {
		return widgetSpacing;
	}

	public void setWidgetSpacing(int widgetSpacing) {
		this.widgetSpacing = widgetSpacing;
	}

	public int getNumWidgetsVisible() {
		return numWidgetsVisible;
	}

	public void setNumWidgetsVisible(int numWidgetsVisible) {
		this.numWidgetsVisible = numWidgetsVisible;
	}

	public boolean isIndependent() {
		return independent;
	}

	public void setIndependent(boolean independent) {
		this.independent = independent;
	}

	public int getxLeft() {
		return xLeft;
	}

	public void setxLeft(int xLeft) {
		this.xLeft = xLeft;
	}

	public int getyTop() {
		return yTop;
	}

	public void setyTop(int yTop) {
		this.yTop = yTop;
	}

	public Cylinder getParentCylinder() {
		return parentCylinder;
	}

	public void setParentCylinder(Cylinder parentCylinder) {
		this.parentCylinder = parentCylinder;
	}
	
	public void spinRow(){
		
	}
	
	/**
	 * Method will get next set of widgets and place it in the row.
	 */
	public void initializeRow() {
		
		WheelWidgetProvider provider = new WheelWidgetProviderImpl();
		nextWidgetSet = provider.getNextWidgetSet(this);

		Object[] widgets = nextWidgetSet.toArray();
		
		//widget spacing will be half the no of widgets in the row bydefault there are 10 widgets in the row. 
		
		widgetSpacing = Math.PI/(nextWidgetSet.size()/1.98);
		

		for (int index = 0; index < widgets.length; index++) {
			Widget widget = (Widget) widgets[index];

			int left = (int) Math.round((Math.cos(startAngle + index* getWidgetSpacing() + speed)* radius + getxLeft()));
			int top = (int) Math.round((-Math.sin(startAngle + index* getWidgetSpacing() + speed)* elevation_angle + getyTop()));

			int z = (int) Math.round((Math.sin(startAngle + index* getWidgetSpacing() + speed)* radius + zcenter));

			double scale = scalingConstant/ (scalingConstant + Math.sin(startAngle + index * getWidgetSpacing() + speed ) * radius + zcenter);

			widget = scaleWheelWidget(widget, scale);

			DragonWheelWidget dww = new DragonWheelWidget(left, top, z, 100,100);
			dww.setWrapped(widget);

			dWidgetMap.put(widget, dww);
			
			add(widget, left, top);
		}
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		int move = event.getDeltaY(); 
		
		if(independent){
			
			int indexOfWidget = 0;
			
			if (move < 0) {
				// mouse wheel is moving north .
				currentAngle = currentAngle - (Math.PI / speed);
				
			} else {
				// mouse wheel is moving south
				currentAngle = currentAngle + Math.PI / speed;
				
			}
			
			for (Map.Entry<Widget, DragonWheelWidget> e : dWidgetMap.entrySet()) {
				Widget widget = e.getKey();
				DragonWheelWidget dww = e.getValue();
				int newXpos = (int) Math.round(Math.cos(currentAngle*2 + indexOfWidget* getWidgetSpacing()+speed)* radius + getxLeft());
				int newYPos = (int) Math.round(-Math.sin(currentAngle*2 + indexOfWidget* getWidgetSpacing()+speed)* elevation_angle + getyTop());

				double scale = scalingConstant/ (scalingConstant + Math.sin(currentAngle*2 + indexOfWidget * getWidgetSpacing()+speed )* radius + zcenter);
				
				widget = scaleWheelWidget(widget, scale);
				
				
				dww.setLeftOffset(newXpos);
				dww.setTopOffset(newYPos);
				
				add(widget, newXpos, newYPos);
				
				indexOfWidget++;
			}
		}else{
			getParentCylinder().rotate(move);
		}
		
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


	public int getScalingConstant() {
		return scalingConstant;
	}

	public void setScalingConstant(int scalingConstant) {
		this.scalingConstant = scalingConstant;
	}

	public int getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(int startAngle) {
		this.startAngle = startAngle;
	}

	public double getCurrentAngle() {
		return currentAngle;
	}

	public void setCurrentAngle(double currentAngle) {
		this.currentAngle = currentAngle;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getElevation_angle() {
		return elevation_angle;
	}

	public void setElevation_angle(double elevation_angle) {
		this.elevation_angle = elevation_angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getZcenter() {
		return zcenter;
	}

	public void setZcenter(int zcenter) {
		this.zcenter = zcenter;
	}

	public void setWidgetSpacing(double widgetSpacing) {
		this.widgetSpacing = widgetSpacing;
	}

	public LinkedHashSet<Widget> getNextWidgetSet() {
		return nextWidgetSet;
	}

	public void setNextWidgetSet(LinkedHashSet<Widget> nextWidgetSet) {
		this.nextWidgetSet = nextWidgetSet;
	}

	public LinkedHashMap<Widget, DragonWheelWidget> getdWidgetMap() {
		return dWidgetMap;
	}

	public void setdWidgetMap(LinkedHashMap<Widget, DragonWheelWidget> dWidgetMap) {
		this.dWidgetMap = dWidgetMap;
	}
	
	
	public Widget scaleWheelWidget(Widget widget,double scale){
		
		widget.getElement().getStyle().setProperty("zoom", "scale(" + scale + ")");
		widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") skewX(" + (scale) + "deg)");
		widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ")");
				
		if(scale>=0.98)
			scale = 1;
		
		DOM.setStyleAttribute(widget.getElement(), "opacity",String.valueOf(scale));
		return widget;
	}

}
