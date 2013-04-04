package in.appops.client.common.fields.suggestion;

import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.spacemanagement.constants.SpaceTypeConstants;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class AppopsSuggestion implements Suggestion{

	private Entity entity;
	private String display;
	
	AppopsSuggestion(Entity entity) {
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

	public void initialize() {
		String typeName = entity.getType().getTypeName();
		typeName = typeName.replace(".", "#");
		String[] splittedArray = typeName.split("#");
		String actualTypeProp = splittedArray[splittedArray.length-1];
		String name="";
		
		if(actualTypeProp.equals(TypeConstants.SPACE))
			name = entity.getProperty(SpaceTypeConstants.NAME).getValue().toString();
		else if(actualTypeProp.equals(TypeConstants.CONTACT))
			name = entity.getProperty(ContactConstant.NAME).getValue().toString();

		//name is the string which will be displayed while showing the suggestions

		setDisplay(name);
	}
}