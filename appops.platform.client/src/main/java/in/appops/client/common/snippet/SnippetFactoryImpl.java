package in.appops.client.common.snippet;

import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.type.Type;
//import in.appops.showcase.web.gwt.listcomponent.client.ReminderSnippet;

public class SnippetFactoryImpl implements SnippetFactory {

	@Override
	public Snippet getSnippetByName(String snippetName) {
		if (snippetName.equals("Book a table")) {
			return null;// return a book a table widget.
		}
		return null;
	}

	@Override
	public Snippet getSnippetByType(String snippetType) {

		return null;
	}

	@Override
	public Snippet getSnippetByEntityType(Type entityType, String snippetType) {
		if(entityType==null){
			if(snippetType!=null){
				if(snippetType.equals("ImageUpload")){
					IconSnippet iconSnippet = new IconSnippet();
					return iconSnippet;
				}

			}else
				return null;
		}else{
			String typename = entityType.getTypeName();
			typename = typename.substring(typename.lastIndexOf('.')+1).trim();

			if(typename.equals(TypeConstants.REMINDER)){
				//ReminderSnippet reminderSnippet = new ReminderSnippet();
				//return reminderSnippet;
			}
		}
		
		return null;
	}

}
