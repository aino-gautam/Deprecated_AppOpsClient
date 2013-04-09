package in.appops.showcase.web.gwt.navigationmaze.client;

import in.appops.client.common.fields.StateField;
import in.appops.client.common.fields.slider.field.NumericRangeSliderField;
import in.appops.client.gwt.web.ui.maze.Maze;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author milind@ensarm.com
 */
public class NavigationMazeShowCase implements EntryPoint{

	@Override
	public void onModuleLoad() {
		
		Maze maze = new Maze();
		
		
		maze.addMazeImageWidget("image/test1.jpg", 5, 5);
		maze.addMazeImageWidget("image/test2.jpg", 200, 50);
		maze.addMazeImageWidget("image/test3.jpg", 500, 10);
		maze.addMazeImageWidget("image/test4.jpg", 800, 100);
		
		maze.init();
		
		StateField numericRangeSlider = new StateField();
		Configuration numericRangeSliderConfig = getNumericRangeSliderFieldConfiguration();
		numericRangeSlider.setConfiguration(numericRangeSliderConfig);
		try {
			numericRangeSlider.createField();
			numericRangeSlider.setStylePrimaryName("mainPanel");
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(maze);
		vp.add(numericRangeSlider);
		
		RootPanel.get().add(vp);
	}
	
	private Configuration getNumericRangeSliderFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(StateField.STATEFIELD_MODE, StateField.STATEFIELDMODE_ENUM);
		configuration.setPropertyByName(StateField.STATEFIELD_TYPE, StateField.STATEFIELDTYPE_NUMERICRANGE);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_MAXVALUE, 0.1);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_MINVALUE, 0.0);
		configuration.setPropertyByName(NumericRangeSliderField.NUMERIC_RANGESLIDER_STEPVALUE, 0.01);
		return configuration;
	}
}
