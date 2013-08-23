package in.appops.client.common.config.field.suggestion;

import java.util.logging.Level;
import java.util.logging.Logger;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class AppopsSuggestion implements Suggestion{

	private Entity entity;
	private String display;
	private Logger logger = Logger.getLogger(getClass().getName());

	public AppopsSuggestion() {
		// TODO Auto-generated constructor stub
	}
	
	public AppopsSuggestion(String display) {
		this.display = display;
	}
	
	public AppopsSuggestion(Entity entity) {
		this.entity = entity;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public String getDisplayString() {
		return this.display;
	}

	@Override
	public String getReplacementString() {
		return null;
	}
	
	public Entity getEntity() {
		return entity;
	}

	public void setPropertToDisplay(String entProp) {
		try {
			logger.log(Level.INFO, "[AppopsSuggestion] ::In setPropertToDisplay method ");
			String name = entity.getProperty(entProp).getValue().toString();
			//name is the string which will be displayed while showing the suggestions
			setDisplay(name);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestion] ::Exception in setPropertToDisplay method :"+e);
		}
	}
}