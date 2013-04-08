package in.appops.client.gwt.web.ui.maze;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;

import java.util.ArrayList;

import com.emitrom.lienzo.client.core.event.NodeMouseClickEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseClickHandler;
import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Picture;
import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.client.widget.LienzoPanel;
import com.emitrom.lienzo.shared.core.types.Color;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.animation.client.AnimationScheduler.AnimationHandle;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class Maze extends LienzoPanel implements FieldEventHandler {

	private AnimationHandle animation;
	private Layer layer = new Layer();
	private boolean animate = true;
	//private LinkedHashMap<Widget , MazeImage> mazeImageWidgetMap = new LinkedHashMap<Widget, MazeImage>();
	private ArrayList<MazeImage> mazeImageList;
	
	static final int canvasHeight = 500;
	static final int canvasWidth = 1300;
	private double speed = 0.0 ;
	
	/**
	 * constructor. Registers the maze as a handler for the range field events.
	 */
	public Maze() {
		super(canvasWidth, canvasHeight);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	/**
	 * initializes the maze with the maze image positions and begins the animation
	 */
	public void init() {
		setBackgroundColor(ColorName.BLACK);
		add(layer);
		initMazeImagePositions();
		layer.draw();
		
		layer.addNodeMouseClickHandler(new NodeMouseClickHandler() {
			
			@Override
			public void onNodeMouseClick(NodeMouseClickEvent event) {
				setSpeed(0.0);
				animate();
				
			}
		});
		
		animate();
	}
	
	/**
	 * Applies the animation to every mazeImage from the list
	 */
	private void animate(){
		for(MazeImage mazeImage : mazeImageList){
			mazeImage.goCloser();
		}
	}
	
	/**
	 * Instantiates the mazeImage object and adds to a list
	 * @param imageUrl
	 */
	public void addMazeImageWidget(String imageUrl, double x, double y){
		if(mazeImageList == null)
			mazeImageList = new ArrayList<Maze.MazeImage>();
		
		MazeImage img = new MazeImage(imageUrl, false, null, x, y);
		mazeImageList.add(img);
	}
	
	/**
	 * sets the X and Y co ordinates for the mazeImages in the list and adds to the layer
	 */
	public void initMazeImagePositions(){
		for(MazeImage mazeImage : mazeImageList){
			mazeImage.setX(mazeImage.getLeftOffset());
			mazeImage.setY(mazeImage.getTopOffset());
			mazeImage.setClippedImageDestinationHeight(1);
			mazeImage.setClippedImageDestinationWidth(1);
			
			layer.add(mazeImage);
		}
	}
	
	public AnimationHandle getAnimation() {
		return animation;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	/*@Override
	public void onNodeMouseClick(NodeMouseClickEvent event) {
		if(event.getSource() instanceof MazeImage){
			MazeImage img = (MazeImage)event.getSource();
			
			for(MazeImage mazeImage : mazeImageList){
				layer.remove(mazeImage);
			}
			
			img.setScale(2.5);
			img.setDoubleScale(2.5);
			layer.add(img);
			layer.draw();
		}
	}*/
	
	public final class MazeImage extends Picture{

		private double scale = 0.001 ;
		private double width = 10 ;
		private double height = 10 ;
    	private double xLeft = 0 ;
		private double yTop = 0 ;
		private Widget wrapped = null ;
		
		public MazeImage(String url, boolean listening, String pictureCategory) {
			super(url, listening, pictureCategory);
		}
		
		public MazeImage(String url, boolean listening, String pictureCategory, double x, double y) {
			super(url, listening, pictureCategory);
			xLeft = x ;
			yTop = y ;
		}
		
		/**
		 * Applies the animation scheduler to scale the mazeImage
		 */
		public void goCloser(){
			animation = AnimationScheduler.get().requestAnimationFrame(new AnimationCallback() {
						@Override
						public void execute(double timestamp) {
							if (animate) {
								animateMazeImage();
							}
							layer.draw();
							AnimationScheduler.get().requestAnimationFrame(this);
							}
					}, layer.getCanvasElement());
		}
		
		/**
		 * Scales the mazeImage to provide the animation effect
		 */
		private void animateMazeImage() {
			
			double scale = this.getDoubleScale() + getSpeed() ;
			
			if(scale >= 3){
				layer.remove(this);
			}else{
				this.setScale(scale);
				this.setDoubleScale(scale);
			}
		}
		
		/**
		 * @param scale2
		 */
		public void setDoubleScale(double scale2) {
			scale = scale2 ;
		}

		public double getDoubleScale(){
			return scale ;
		}
		
		public double getWidth() {
			return width;
		}


		public void setWidth(double width) {
			this.width = width;
		}


		public double getHeight() {
			return height;
		}


		public void setHeight(double height) {
			this.height = height;
		}
		
    	public double getLeftOffset() {
			return xLeft;
		}

		public void setLeftOffset(double xLeft) {
			this.xLeft = xLeft;
		}

		public double getTopOffset() {
			return yTop;
		}

		public void setTopOffset(double yTop) {
			this.yTop = yTop;
		}
		
		public Widget getWrapped() {
			return wrapped;
		}


		public void setWrapped(Widget wrapped) {
			this.wrapped = wrapped;
		}
	}
	

	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	/**
	 * Handles the field event.
	 */
	@Override
	public void onFieldEvent(FieldEvent event) {
		int evttype = event.getEventType();
		switch (evttype) {
		case FieldEvent.EDITINITIATED:{
			double speed = (Double)event.getEventData();
			System.out.println("Speed increased: " +speed);
			setSpeed(speed);
			animate();
			break;
		}
		default:
			break;
		}
		
	}
}
