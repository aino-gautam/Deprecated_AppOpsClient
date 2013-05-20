package in.appops.client.common.snippet;

import in.appops.client.common.components.MessageWithUserSnippet;
import in.appops.client.common.contactmodel.ContactSnippet;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.type.Type;

public class SnippetFactoryImpl implements SnippetFactory {

	
	@Override
	public Snippet getSnippetByName(String snippetName) {
		if (snippetName.equals("Book a table")) {
			return null;// return a book a table widget.
		}else if(snippetName.equals(SnippetConstant.MEDIASERVICEHOME)){
			MediaServiceHomeSnippet mediaServiceHomeSnippet = new MediaServiceHomeSnippet();
			return mediaServiceHomeSnippet;
		}else if(snippetName.equals(SnippetConstant.POSTVIEWSNIPPET)){
			PostViewSnippet postViewSnippet = new PostViewSnippet();
			return postViewSnippet;
		}else if(snippetName.equals(SnippetConstant.CALENDARSERVICEHOME)){
			CalendarServiceHomeSnippet calendarServiceHomeSnippet = new CalendarServiceHomeSnippet();
			return calendarServiceHomeSnippet;
		}else{
			HomeSnippet homeSnippet = new HomeSnippet();
			return homeSnippet;
		}
	}

	@Override
	public Snippet getSnippetByType(String snippetType) {
		if(snippetType.equals(SnippetConstant.LISTSNIPPET)){
			ListSnippet listSnippet = new ListSnippet();
			return listSnippet;
		}
		return null;
	}

	@Override
	public Snippet getSnippetByEntityType(Type entityType, String snippetType) {
		if(entityType==null){
			if(snippetType!=null){
				if(snippetType.equals(SnippetConstant.IMAGEUPLOAD)){
					IconSnippet iconSnippet = new IconSnippet();
					return iconSnippet;
				}

			}else
				return null;
		}else{
			String typename = entityType.getTypeName();
			if(typename.contains("."))
				typename = typename.substring(typename.lastIndexOf('.')+1).trim();

			if(typename != null){
				if(typename.equals(TypeConstants.REMINDER)){
					ReminderSnippet reminderSnippet = new ReminderSnippet();
					return reminderSnippet;
				}else if(typename.equals("SpaceserviceviewId")){
					ServiceIconSnippet serviceIconSnippet = new ServiceIconSnippet();
					serviceIconSnippet.setShowOnlyIcon(true);
					return serviceIconSnippet;
				}else if(typename.equals("SpaceactionsviewId")){
					ActionBoxSnippet actionBoxSnippet = new ActionBoxSnippet();
					return actionBoxSnippet;
				}else if(typename.equals("Post")){
					PostViewSnippet postViewSnippet = new PostViewSnippet();
					return postViewSnippet;
				}else if(typename.equals("Contact")){
					ContactSnippet contactSnippet = new ContactSnippet();
					return contactSnippet;
				}else if(typename.equals("Message")){
					MessageWithUserSnippet messageWithUserSnippet = new MessageWithUserSnippet();
					return messageWithUserSnippet;
				}else if(typename.equals("Space")){
					BoxSnippet spaceSnippet = new BoxSnippet();
					return spaceSnippet;
				}else if(typename.equals(TypeConstants.EVENT)){
					EventSnippet eventSnippet = new EventSnippet();
					return eventSnippet;
				}else{
					HomeSnippet homeSnippet = new HomeSnippet();
					return homeSnippet;
				}
			}
		}
			
		
		return null;
	}

}
