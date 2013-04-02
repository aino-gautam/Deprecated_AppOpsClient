package in.appops.client.common.snippet;

import in.appops.platform.core.entity.type.Type;

public interface SnippetFactory {
	public Snippet getSnippetByName(String snippetName);

	public Snippet getSnippetByType(String snippetType);

	public Snippet getSnippetByEntityType(Type entityType, String snippetType);

}
