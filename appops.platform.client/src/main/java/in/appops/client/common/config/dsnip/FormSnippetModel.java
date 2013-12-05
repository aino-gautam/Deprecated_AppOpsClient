package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.dsnip.event.form.FormEventRuleMap;
import in.appops.client.common.config.dsnip.event.form.InvokeActionRule;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The model class for the {@link FormSnippetModel}
 * @author nairutee
 *
 */
public class FormSnippetModel extends HTMLSnippetModel {
	
	public static final String CONFIG_FORMEVENTRULEMAP = "formEventRuleMap";
	
	public FormEventRuleMap getFormEventRuleMap(){
		if(configuration.getConfigurationValue(CONFIG_FORMEVENTRULEMAP) != null) {
			return (FormEventRuleMap) configuration.getConfigurationValue(CONFIG_FORMEVENTRULEMAP);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void invokeAction(final InvokeActionRule invokeActionRule){
		
		StandardAction action = new StandardAction(Entity.class, invokeActionRule.getOperation(), invokeActionRule.getProcessedParamerMap());
		dispatch.execute(action, new AsyncCallback<Result>() {

			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Window.alert(invokeActionRule.getFailureMsg());
			}

			public void onSuccess(Result result) {
				Window.alert(invokeActionRule.getSuccessMsg());
			}
		});
	}
}
