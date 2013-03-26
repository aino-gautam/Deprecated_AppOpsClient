/**
 * 
 */
package in.appops.client.gwt.web.ui;

import in.appops.platform.core.entity.Entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class Row extends AbsolutePanel implements MouseWheelHandler,ClickHandler{

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
	private Entity entity; //row is associated with entity.
	private LinkedHashSet<Widget> widgetSetForRow;
	private int scalingConstant = 200; // used for calculating opacity and scale
	private int startAngle = 30;
	private double currentAngle = startAngle * (Math.PI /180);
	private double radius; // in px
	public double elevation_angle = 15; // in pixels - elevation angle decision maker
	public double speed = 5	; // 
	private int zcenter = 200;
	private MediaViewer mediaViewer;
	private double rotationAngle =0;
	private boolean isSkewMode;
	private ImageSliderPopup imageSliderPopup = new ImageSliderPopup();
	//private LinkedHashSet<Widget> widgetSet;
	private ArrayList<ImageWidget> widgetList = new ArrayList<ImageWidget>();
	
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
		
		for (Widget widget:widgetSetForRow) {
			ImageWidget imageWidget = (ImageWidget) widget;
						
			widgetList.add(imageWidget);
		}
		
		widgetSpacing = Math.PI/(nextWidgetSet.size()/1.98);
		
		int index = 0;

		
		for (Widget widget:widgetSetForRow) {
			
			int left = (int) Math.round((Math.cos(startAngle + index* getWidgetSpacing() + speed)* radius + getxLeft()));
			int top = (int) Math.round((-Math.sin(startAngle + index* getWidgetSpacing() + speed)* elevation_angle + getyTop()));

			int z = (int) Math.round((Math.sin(startAngle + index* getWidgetSpacing() + speed)* radius + zcenter));

			double scale = scalingConstant/ (scalingConstant + Math.sin(startAngle + index * getWidgetSpacing() + speed ) * radius + zcenter);
						
			widget = scaleWidget(widget, scale,0,index);
				
			widget.addDomHandler(this, ClickEvent.getType());
			
			add(widget, left, top);
			
			index++;
		}
	}
	
	
	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		int move = event.getDeltaY(); 
		
		if(independent){
			
			int indexOfWidget = 0;
			
			if (move > 0) {
				currentAngle = currentAngle - (Math.PI / speed);
				
			} else {
				currentAngle = currentAngle + Math.PI / speed;
			}
			
			for (Widget widget : widgetSetForRow) {
				
				int newXpos = (int) Math.round(Math.cos(currentAngle*2 + indexOfWidget* getWidgetSpacing()+speed)* radius + getxLeft());
				int newYPos = (int) Math.round(-Math.sin(currentAngle*2 + indexOfWidget* getWidgetSpacing()+speed)* elevation_angle + getyTop());

				double scale = scalingConstant/ (scalingConstant + Math.sin(currentAngle*2 + indexOfWidget * getWidgetSpacing()+speed )* radius + zcenter);
							
				widget = scaleWheelWidget(widget, scale,indexOfWidget,move);
				
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
	
	public Widget scaleWheelWidget(Widget imageWidget,double scale,int index,int move){
		ImageWidget widget = (ImageWidget) imageWidget;
		String strNumber = Double.toString(scale).substring(2);
		int zIndex = Integer.parseInt(strNumber.substring(0, 1));
		widget.getElement().getStyle().setZIndex(zIndex);
		widget.setStylePrimaryName("imageWidget");
		
		widget.getElement().getStyle().setProperty("zoom", "scale(" + scale + ") ");
				
		if(isSkewMode()){
			//Frontmost widget will have perspective 600px.
			int perspective = 0;
			double rotationAngle = widget.getRotationAngle();
			
			if(move>0){
				
				if(rotationAngle==360)
					rotationAngle =36;
				else{
					rotationAngle+=36;
					if(rotationAngle ==360)
						rotationAngle =0;
				}
			}else{
				if(rotationAngle==0)
					rotationAngle = 324;
				
				else
					rotationAngle-=36;
			}
			if(zIndex ==9)
				perspective = 600;
			else
				perspective = (int) Math.floor(600*(zIndex-1)/9);
			
			widget.setRotationAngle(rotationAngle);
				
			widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ")  perspective("+perspective+"px) rotateY("+ (rotationAngle)+"deg)");
			widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ")  perspective("+perspective+"px) rotateY("+ (rotationAngle)+"deg)");
					
		}else{
			rotationAngle = 100-zIndex*8;
			
			widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
			widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
		}
		if(scale>=0.50){
			scale = 1;
		}
		
		DOM.setStyleAttribute(widget.getElement(), "opacity",String.valueOf(scale));
		return widget;
	}
	
	public void rotateRow(){
			int index = 0;
			
			for (Widget widget:widgetSetForRow) {
			
			int left = (int) Math.round((Math.cos(startAngle + index* getWidgetSpacing() + speed)* radius + getxLeft()));
			int top = (int) Math.round((-Math.sin(startAngle + index* getWidgetSpacing() + speed)* elevation_angle + getyTop()));

			int z = (int) Math.round((Math.sin(startAngle + index* getWidgetSpacing() + speed)* radius + zcenter));

			double scale = scalingConstant/ (scalingConstant + Math.sin(startAngle + index * getWidgetSpacing() + speed ) * radius + zcenter);
			
			widget = scaleWidget(widget, scale,0,index);
				
			
			add(widget, left, top);
			
			index++;
		}
	}
	
	public Widget scaleWidget(Widget imageWidget,double scale,double rotationiAngle,int index){
		ImageWidget widget = (ImageWidget) imageWidget;
		String strNumber = Double.toString(scale).substring(2);
		int zIndex = Integer.parseInt(strNumber.substring(0, 1));
		widget.getElement().getStyle().setZIndex(zIndex);
		widget.setStylePrimaryName("imageWidget");
		
		widget.getElement().getStyle().setProperty("zoom", "scale(" + scale + ") ");
	
		//angle calculated from widget on front.
		
		if(isSkewMode()){
			
			int perspective = 0;
						
			if(index==0)
				rotationAngle = 72;
			else if(rotationAngle>360){
				rotationAngle =rotationAngle-324;
				if(rotationAngle==360)
					rotationAngle=0;
			}else{
				rotationAngle+=36;
				if(rotationAngle==360)
					rotationAngle=0;
			}
		
			if(zIndex ==9)
				perspective = 600;
			else
				perspective = (int) Math.floor(600*(zIndex-1)/9);
			
			widget.setRotationAngle(rotationAngle);
				
			widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ")  perspective("+perspective+"px) rotateY("+ (rotationAngle)+"deg)");
			widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ")  perspective("+perspective+"px) rotateY("+ (rotationAngle)+"deg)");
					
		}else{
			rotationAngle = 100-zIndex*8;
			
			widget.getElement().getStyle().setProperty("MozTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
			widget.getElement().getStyle().setProperty("WebkitTransform", "scale(" + scale + ") rotateY("+ (rotationAngle)+"deg)");
		}
		
		if(scale>=0.50){
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

	public void setWidgetSetForRow(LinkedHashSet<Widget> widgetSetForRow) {
		this.widgetSetForRow = widgetSetForRow;
	}

	public MediaViewer getMediaViewer() {
		return mediaViewer;
	}

	public void setMediaViewer(MediaViewer mediaViewer) {
		this.mediaViewer = mediaViewer;
	}

	public boolean isSkewMode() {
		return isSkewMode;
	}

	public void setSkewMode(boolean isSkewMode) {
		this.isSkewMode = isSkewMode;
	}

	@Override
	public void onClick(ClickEvent event) {
		ImageWidget widget =(ImageWidget) event.getSource();
		
		if(!imageSliderPopup.isShowing()){
			imageSliderPopup.initialize(widgetList);
		}
					
		imageSliderPopup.showImage(widget);
		
		imageSliderPopup.center();
	}
	
	

}
