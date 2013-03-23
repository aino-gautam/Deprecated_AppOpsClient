/**
 * 
 */
package in.appops.client.gwt.web.ui;

import in.appops.platform.core.entity.Entity;

import java.util.LinkedHashSet;
import java.util.Set;

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
	private Entity entity; //row is basically one album entity.
	private Set<Widget> widgetSetForRow;
	private int scalingConstant = 250; // used for calculating opacity and scale
	private int startAngle = 30;
	private double currentAngle = startAngle * (Math.PI /180);
	private double radius=250; // in px
	public double elevation_angle = 25; // in pixels - elevation angle decision maker
	public double speed = 10	; // 
	private int zcenter = 250;
	private MediaViewer mediaViewer;
	
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
	public void initWidgetPositions(MediaViewer mediaViewer,LinkedHashSet<Widget> nextWidgetSet) {

		//widget spacing will be half the no of widgets in the row bydefault there are 10 widgets in the row. 
		widgetSetForRow = nextWidgetSet;
		
		widgetSpacing = Math.PI/(nextWidgetSet.size()/1.98);
		
		int index = 0;

		
		for (Widget widget:widgetSetForRow) {
			
			int left = (int) Math.round((Math.cos(startAngle + index* getWidgetSpacing() + speed)* radius + getxLeft()));
			int top = (int) Math.round((-Math.sin(startAngle + index* getWidgetSpacing() + speed)* elevation_angle + getyTop()));

			int z = (int) Math.round((Math.sin(startAngle + index* getWidgetSpacing() + speed)* radius + zcenter));

			double scale = scalingConstant/ (scalingConstant + Math.sin(startAngle + index * getWidgetSpacing() + speed ) * radius + zcenter);
			
			double  deltaX = 317 - left;
			double  deltaY= 75 - top;
			
			double angleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
			
					 
			//angleInDegrees = Math.abs(Math.atan2(75-50, 317-303) - Math.atan2(top-50, left-303));
			
			System.out.println("-----------index ="+index+"-------------left="+left+"-----------------top="+top +"angle======"+angleInDegrees);

			
			widget = scaleWheelWidget(widget, scale,index,angleInDegrees);
				
			
			add(widget, left, top);
			
			index++;
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
			
			for (Widget widget : widgetSetForRow) {
				
				int newXpos = (int) Math.round(Math.cos(currentAngle*2 + indexOfWidget* getWidgetSpacing()+speed)* radius + getxLeft());
				int newYPos = (int) Math.round(-Math.sin(currentAngle*2 + indexOfWidget* getWidgetSpacing()+speed)* elevation_angle + getyTop());

				double scale = scalingConstant/ (scalingConstant + Math.sin(currentAngle*2 + indexOfWidget * getWidgetSpacing()+speed )* radius + zcenter);
				
				double  deltaX = newXpos - 317;
				double  deltaY= newYPos - 75;
						
				
				double angleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
				
				widget = scaleWheelWidget(widget, scale,indexOfWidget,angleInDegrees);
				
				
				add(widget, newXpos, newYPos);
				
				indexOfWidget++;
			}
		}else{
			if(parentCylinder!=null)
			parentCylinder.rotate(move);
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

	
	
	public Widget scaleWheelWidget(Widget widget,double scale,int index,double rotationAngle){
		String strNumber = Double.toString(scale).substring(2);
		int zIndex = Integer.parseInt(strNumber.substring(0, 1));
		widget.getElement().getStyle().setZIndex(zIndex);
		widget.setStylePrimaryName("imageWidget");
		
		widget.getElement().getStyle().setProperty("zoom", "scale(" + scale + ") ");
		//widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") perspective("+ (zIndex*600/9)+"px) rotateY("+ (100-zIndex*8)+"deg)");
	//	widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ") perspective("+ (zIndex*600/9)+"px) rotateY("+ (100-zIndex*8)+"deg)");
		//widget.getElement().getStyle().setProperty("MozTransform", "rotate("+30+"deg) perspective( 600px )");
		
		//int rotationAngle  = (int) (100-zIndex*8);
		
		widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
		widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
		
		/*if(rotationAngle<16){
			widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") rotateX("+ (rotationAngle)+"deg)");
			widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ") rotateX("+ (rotationAngle)+"deg)");
		}else{
			widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
			widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
		}*/
		
		
		if(scale>=0.98){
			scale = 1;
		}
		
		DOM.setStyleAttribute(widget.getElement(), "opacity",String.valueOf(scale));
		return widget;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Set<Widget> getWidgetSetForRow() {
		return widgetSetForRow;
	}

	public void setWidgetSetForRow(Set<Widget> widgetSetForRow) {
		this.widgetSetForRow = widgetSetForRow;
	}

	public MediaViewer getMediaViewer() {
		return mediaViewer;
	}

	public void setMediaViewer(MediaViewer mediaViewer) {
		this.mediaViewer = mediaViewer;
	}
	
	

}
