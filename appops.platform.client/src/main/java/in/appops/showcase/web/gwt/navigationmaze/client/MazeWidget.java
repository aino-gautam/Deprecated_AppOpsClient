package in.appops.showcase.web.gwt.navigationmaze.client;

import java.util.ArrayList;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * 
 * @author milind@ensarm.com
 */
public class MazeWidget extends Composite{

	private Canvas canvas;
	private final int canvasHeight = 500;
	private final int canvasWidth = 800;

	private final int maxRadius = 100;

	private ArrayList<Context2d> listOfMazes = null;
	private VerticalPanel mainPanel = new VerticalPanel();

	public MazeWidget() {
		initWidget(mainPanel);
		listOfMazes = new ArrayList<Context2d>();
		initializeWidget();
	}

	public void initializeWidget() {

		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			RootPanel.get().add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}

		canvas.setWidth(canvasWidth + "px");
		canvas.setCoordinateSpaceWidth(canvasWidth);

		canvas.setHeight(canvasHeight + "px");      
		canvas.setCoordinateSpaceHeight(canvasHeight);

		mainPanel.add(canvas);
		mainPanel.setCellHorizontalAlignment(canvas, HasHorizontalAlignment.ALIGN_CENTER);

		/*final Timer timer = new Timer() {           
			@Override
			public void run() {
				drawSomethingNew();
			}
		};
		timer.scheduleRepeating(3000);*/
		drawSomethingNew();
	}

	/*public void drawSomethingNew() {
		final Context2d context = canvas.getContext2d();

		// Get random coordinates and sizing
		int rndX = Random.nextInt(canvasWidth);
		int rndY = Random.nextInt(canvasHeight);  

		// Get a random color and alpha transparency
		int rndRedColor = Random.nextInt(255);
		int rndGreenColor = Random.nextInt(255);
		int rndBlueColor = Random.nextInt(255);
		double rndAlpha = Random.nextDouble();

		CssColor randomColor = CssColor.make("rgba(" + rndRedColor + ", " + rndGreenColor + "," + rndBlueColor + ", " + rndAlpha + ")");

		context.setFillStyle(randomColor);
		context.setGlobalAlpha(1.0);

		int radius = Random.nextInt(maxRadius);

		if(radius>=maxRadius-10){
			Timer alphaTimer = new Timer() {
				@Override
				public void run() {
					double currentAlpha = context.getGlobalAlpha();
					currentAlpha = currentAlpha - 0.3;
					context.setGlobalAlpha(currentAlpha);
					context.setStrokeStyle(CssColor.make("rgba(0,0,0,"+currentAlpha+")"));
				}
			};
			alphaTimer.schedule(800);
			alphaTimer.scheduleRepeating(700);
		}else{
			//context.fillRect(rndX, rndY, rndWidth, rndHeight);
			context.beginPath();
			context.arc(rndX, rndY, radius, 0, Math.PI * 2.0, true);
			context.closePath();
			context.fill();
		}
	}*/

	public void drawSomethingNew() {
		int number = 5;
		for(int i=0; i<number; i++){
			Context2d context = canvas.getContext2d();
			context.beginPath();
			
			// Get a random color and alpha transparency
			int rndRedColor = Random.nextInt(255);
			int rndGreenColor = Random.nextInt(255);
			int rndBlueColor = Random.nextInt(255);
			double rndAlpha = Random.nextDouble();

			CssColor randomColor = CssColor.make("rgba(" + rndRedColor + ", " + rndGreenColor + "," + rndBlueColor + ", " + rndAlpha + ")");
			context.setFillStyle(randomColor);

			int radius = Random.nextInt(maxRadius);

			// Get random coordinates and sizing
			int rndX = Random.nextInt(canvasWidth);
			int rndY = Random.nextInt(canvasHeight);  
			//context.fillRect(rndX, rndY, rndWidth, rndHeight);
			context.arc(rndX, rndY, radius, 0, Math.PI * 2.0, true);
			
			context.fill();
			context.closePath();
			
			listOfMazes.add(context);
			
			//redraw(context, rndX, rndY,radius);
		}
	}

	/**
	 * Redraw the circle UI
	 * @param context
	 * @param rndX
	 * @param rndY
	 * @param radius
	 */
	private void redraw(Context2d context, int rndX, int rndY, int radius) {
		while(radius<=maxRadius){
			int rndRedColor = Random.nextInt(150);
			int rndGreenColor = Random.nextInt(150);
			int rndBlueColor = Random.nextInt(150);
			double rndAlpha = Random.nextDouble();

			CssColor randomColor = CssColor.make("rgba(" + rndRedColor + ", " + rndGreenColor + "," + rndBlueColor + ", " + rndAlpha + ")");
			context.setFillStyle(randomColor);

			context.fill();
			context.save();
			radius = radius + 5;
		}
	}

	public void refreshMaze() {
		try {
			for(int i=0; i<listOfMazes.size(); i++){
				Context2d context = listOfMazes.get(i);
				int rndRedColor = Random.nextInt(150);
				int rndGreenColor = Random.nextInt(150);
				int rndBlueColor = Random.nextInt(150);
				double rndAlpha = Random.nextDouble();

				CssColor randomColor = CssColor.make("rgba(" + rndRedColor + ", " + rndGreenColor + "," + rndBlueColor + ", " + rndAlpha + ")");
				context.setFillStyle(randomColor);
				
				context.setStrokeStyle("black");
				context.fill();
				context.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
