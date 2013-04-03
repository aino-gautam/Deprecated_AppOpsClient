package in.appops.client.common.snippet;

import in.appops.platform.core.entity.type.Type;

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
		// TODO Auto-generated method stub
		return null;
	}

}
