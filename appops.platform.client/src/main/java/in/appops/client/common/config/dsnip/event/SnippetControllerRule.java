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
	
	public static final String HAS_TRANSFORMATION = "HasTransformation";
	public static final String TRANSFORM_TO_SNIPPET = "TransforToSnippet";
	public static final String TRANSFORM_TO_SNIPPET_INSTANCE = "TransformToSnippetInstance";
	
	public boolean hasTransformation() {
		return getPropertyByName(HAS_TRANSFORMATION);
	}

	public String getTransformSnippet() {
		return getPropertyByName(TRANSFORM_TO_SNIPPET);
	}
	
	public String getSnippetInstance() {
		return getPropertyByName(TRANSFORM_TO_SNIPPET_INSTANCE);
	}

}
