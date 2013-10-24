package in.appops.client.common.config.dsnip.event;

/**
 * Represents rules that control a snippet
 * @author nairutee
 *
 */
public class SnippetControllerRule extends EventActionRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String HAS_TRANSFORMATION = "Has_transformation";
	public static final String TRANSFORM_TO_SNIPPET = "Transform_to_snippet";
	public static final String TRANSFORM_TO_SNIPPET_INSTANCE = "Transform_to_snippet_instance";
	public static final String TRANSFORM_FROM_SNIPPET = "Transform_from_snippet";
	
	public Boolean hasTransformation() {
		return getPropertyByName(HAS_TRANSFORMATION);
	}

	public String getTransformToSnippet() {
		return getPropertyByName(TRANSFORM_TO_SNIPPET);
	}

	public String getTransformFromSnippet() {
		return getPropertyByName(TRANSFORM_FROM_SNIPPET);
	}
	
	public String getSnippetInstance() {
		return getPropertyByName(TRANSFORM_TO_SNIPPET_INSTANCE);
	}

}
