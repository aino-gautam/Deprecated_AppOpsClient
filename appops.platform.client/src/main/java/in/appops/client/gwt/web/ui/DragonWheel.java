/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 06-Mar-2013
 *
 */
public class DragonWheel extends Composite implements MouseWheelHandler{
	
	private Map<Widget , DragonWheelWidget> dWidgetMap = new HashMap<Widget, DragonWheelWidget>();
	private AbsolutePanel parent ;
	private ArrayList<Widget> widgetList; 
	
	public DragonWheel(){
		parent = new AbsolutePanel();
		parent.addHandler(this, MouseWheelEvent.getType());
		parent.setSize("100%", "100%");
		initWidget(parent);
		setStyleName("dragonWheel");
	}
	/**
	 * 
	 */
	public void layOutDragonWheel() {
		
		initWidgetPositions();
		for(Widget w : dWidgetMap.keySet()){
			DragonWheelWidget dww = dWidgetMap.get(w);
			Widget wactual = dww.getWrapped();
			wactual.setPixelSize(dww.getWidth(), dww.getHeight());
			wactual.setVisible(true);
			parent.add(wactual );
			parent.add(new Label("TEsting with absolute panel"));
		}
		
		parent.add(new Label("TEsting with absolute panel") );
	}

	/**
	 * 
	 */
	private void initWidgetPositions() {
		
		if (widgetList.size() > 3) {
			
			for (Widget w : widgetList){
				DragonWheelWidget dww = getRandomPositionWidget (w, widgetList.size()); 
				dWidgetMap.put(w, dww);
			}
		}
	}
	
	private void initWidgetPosition(Widget w){
		
	}

	/**
	 * @param w
	 * @return
	 */
	private DragonWheelWidget getRandomPositionWidget(Widget w , int numWidgets) {
	
		int  left = Random.nextInt(getWheelWidth()) + 1;
		int  top = Random.nextInt(getWheelHeight()) + 1;
		int  z = Random.nextInt(numWidgets) + 1 ;
		
		DragonWheelWidget dww = new DragonWheelWidget(left, top, z, 100 , 100);
		dww.setWrapped(w);
		
		return dww;
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
			width = w ;
			height = h ;
		}

	}


	@Override
	public void onMouseWheel(MouseWheelEvent arg0) {
		System.out.println("Mouse x is " + arg0.getClientX() + " Y is - arg0.getClientY()") ;
	}

}
