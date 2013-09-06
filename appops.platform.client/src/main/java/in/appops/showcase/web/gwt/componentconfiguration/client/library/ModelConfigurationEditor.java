/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.io.Serializable;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com	
 *
 */
public class ModelConfigurationEditor extends Composite{

	private static final Serializable STRINGVAL_FIELD_ID = null;

	private Entity modelConfigInstance;
	private HorizontalPanel basePanel;

	private FlexTable queryParamFlex;
	private FlexTable opParamFlex;
	
	
	public ModelConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new HorizontalPanel();
			opParamFlex = new FlexTable();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createUi(){
		try{
			basePanel.setStylePrimaryName("fullWidth");
			
			VerticalPanel queryDetailHolder = getQryEditorUi();
			queryDetailHolder.setStylePrimaryName("fullWidth");
			
			VerticalPanel operationDetailHolder = getOpEditorUi();
			operationDetailHolder.setStylePrimaryName("fullWidth");
			
			basePanel.add(queryDetailHolder);
			basePanel.add(operationDetailHolder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private VerticalPanel getOpEditorUi() {
		VerticalPanel operationDetailHolder = new VerticalPanel();
		try{
			HorizontalPanel opNameHolder = new HorizontalPanel();
			
			LabelField qryNameLblFld = new LabelField();
			qryNameLblFld.setConfiguration(getQryNameLblFldConf(false));
			qryNameLblFld.configure();
			qryNameLblFld.create();
			
			TextField qryNamevalFld = new TextField();
			qryNamevalFld.setConfiguration(getQryNameValFldConf(true));
			qryNamevalFld.configure();
			qryNamevalFld.create();
			
			opNameHolder.add(qryNameLblFld);
			opNameHolder.add(qryNamevalFld);
			
			operationDetailHolder.add(opNameHolder);
			
			opParamFlex.setStylePrimaryName("fullWidth");
			operationDetailHolder.add(opParamFlex);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return operationDetailHolder;
	}

	private VerticalPanel getQryEditorUi() {
		VerticalPanel queryDetailHolder = new VerticalPanel();
		try{
			HorizontalPanel qryNameHolder = new HorizontalPanel();
			
			LabelField qryNameLblFld = new LabelField();
			qryNameLblFld.setConfiguration(getQryNameLblFldConf(true));
			qryNameLblFld.configure();
			qryNameLblFld.create();
			
			TextField qryNamevalFld = new TextField();
			qryNamevalFld.setConfiguration(getQryNameValFldConf(true));
			qryNamevalFld.configure();
			qryNamevalFld.create();
			
			qryNameHolder.add(qryNameLblFld);
			qryNameHolder.add(qryNamevalFld);
			
			queryDetailHolder.add(qryNameHolder);
			
			opParamFlex.setStylePrimaryName("fullWidth");
			queryDetailHolder.add(opParamFlex);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return queryDetailHolder;
	}

	private Configuration getQryNameValFldConf(boolean isQueryOperation) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Query name");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getQryNameLblFldConf(boolean isQieryCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Query name : ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
}
