/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
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
public class ModelConfigurationEditor extends Composite implements ConfigEventHandler{

	private Entity modelConfigType;
	private HorizontalPanel basePanel;

	private FlexTable queryParamFlex;
	private FlexTable opParamFlex;
	
	public static final String QUERYMODE = "queryMode";
	public static final String OPERATIONMODE = "operationMode";
	
	private int opRow,queryRow ;
	
	private final String MODELLBLCSS = "modelLblCss";
	
	public ModelConfigurationEditor(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new HorizontalPanel();
			initWidget(basePanel);
			opParamFlex = new FlexTable();
			queryParamFlex = new FlexTable();
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createUi(){
		try{
			basePanel.setStylePrimaryName("fullWidth");
			
			VerticalPanel queryDetailHolder = getQryEditorUi();
			queryDetailHolder.addStyleName("fullWidth");
			
			VerticalPanel operationDetailHolder = getOpEditorUi();
			operationDetailHolder.addStyleName("fullWidth");
			
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
			operationDetailHolder.setStylePrimaryName("opDetailHolderCss");

			LabelField qryNameLblFld = new LabelField();
			qryNameLblFld.setConfiguration(getQryNameLblFldConf(false));
			qryNameLblFld.configure();
			qryNameLblFld.create();
			
			TextField qryNamevalFld = new TextField();
			qryNamevalFld.setConfiguration(getQryNameValFldConf(false));
			qryNamevalFld.configure();
			qryNamevalFld.create();
			
			opNameHolder.add(qryNameLblFld);
			opNameHolder.add(qryNamevalFld);
			
			operationDetailHolder.add(opNameHolder);
			
			LabelField opParamLblFld = new LabelField();
			opParamLblFld.setConfiguration(getQryParamLblFldConf(false));
			opParamLblFld.configure();
			opParamLblFld.create();
			
			operationDetailHolder.add(opParamLblFld);
			
			opParamFlex.setStylePrimaryName("fullWidth");
			operationDetailHolder.add(opParamFlex);
			
			SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
			snipPropValEditor.setSnipPropValEditorId(OPERATIONMODE);
			opParamFlex.setWidget(opRow, 0, snipPropValEditor);
			opRow++;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return operationDetailHolder;
	}

	private VerticalPanel getQryEditorUi() {
		VerticalPanel queryDetailHolder = new VerticalPanel();
		try{
			queryDetailHolder.setStylePrimaryName("queryDetailHolderCss");
			
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
			
			LabelField qryParamLblFld = new LabelField();
			qryParamLblFld.setConfiguration(getQryParamLblFldConf(true));
			qryParamLblFld.configure();
			qryParamLblFld.create();
			
			queryDetailHolder.add(qryParamLblFld);

			queryParamFlex.setStylePrimaryName("fullWidth");
			queryDetailHolder.add(queryParamFlex);
			
			SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
			//snipPropValEditor.setSnipPropValEditorId(QUERYMODE);
			queryParamFlex.setWidget(queryRow, 0, snipPropValEditor);
			queryRow++;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return queryDetailHolder;
	}

	private Configuration getQryParamLblFldConf(boolean isQueryParamCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			if(isQueryParamCall)
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Query param : ");
			else
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Operation param : ");
		
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, MODELLBLCSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getQryNameValFldConf(boolean isQueryOperationCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			
			if(isQueryOperationCall)
				configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Query name");
			else
				configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Operation name");
		
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, MODELLBLCSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getQryNameLblFldConf(boolean isQueryCall) {
		Configuration configuration = null;
		try{
			configuration = new Configuration();
			if(isQueryCall)
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Query name : ");
			else
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Operation name : ");
			
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, MODELLBLCSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try{
			if(event.getEventType() == ConfigEvent.SAVEPROPVALUEADDWIDGET){
				SnippetPropValueEditor snipPropValEditorSelected = (SnippetPropValueEditor) event.getEventSource();
				if(snipPropValEditorSelected.getSnipPropValEditorId().equals(OPERATIONMODE)){
					saveOperationParam();
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(OPERATIONMODE);
					snipPropValEditor.setSnipPropValEditorId(OPERATIONMODE);
					opParamFlex.setWidget(opRow, 0, snipPropValEditor);
					opRow++;
				}
				else{
					saveQueryParam();
					SnippetPropValueEditor snipPropValEditor = new SnippetPropValueEditor(QUERYMODE);
					snipPropValEditor.setSnipPropValEditorId(QUERYMODE);
					queryParamFlex.setWidget(queryRow, 0, snipPropValEditor);
					queryRow++;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveQueryParam() {
		try{
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveOperationParam() {
		try{
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
