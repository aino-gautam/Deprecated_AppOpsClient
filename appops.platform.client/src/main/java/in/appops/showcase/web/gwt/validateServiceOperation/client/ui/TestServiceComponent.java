package in.appops.showcase.web.gwt.validateServiceOperation.client.ui;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.InterfaceDescriptor;
import in.appops.platform.core.shared.OperationDescriptor;
import in.appops.platform.core.shared.ServiceDescriptor;
import in.appops.showcase.web.gwt.hellopojo.shared.HelloPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;


public class TestServiceComponent extends Composite implements ClickHandler{
	
	private VerticalPanel mainPanel = null;
	private FlexTable flexTable = null;
	private Label serviceName = null;
	private Label defaultInterfaceNameLbl = null;
	private Label defaultInterfaceNameValueLbl = null;
	private Label interfaceNameLbl = null;
	private Label methodNameLbl = null;
	private Label paramsLbl = null;
	private Label paramsNeededLbl = null;
	private SuggestBox serviceNameBox = null;
	private SuggestBox interfaceNameBox = null;
	private SuggestBox methodNameBox = null;
	private int row = 2, col = 2;
	private Button goBtn = null;
	private HashMap<String, Object> dataForTestMap =null;
	private HashMap<String, String> paramMap =null;
	private ServiceDescriptor descriptor = null;
	private MultiWordSuggestOracle interfaceOracle = null;
	private MultiWordSuggestOracle methodOracle = null;
	private MultiWordSuggestOracle serviceOracle = null;
	
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
	public TestServiceComponent() {
		initializeComponents();
		getServiceList();
	}

	private void initializeComponents() {
		try {
			mainPanel = new VerticalPanel();
			flexTable = new FlexTable();
			
			serviceName = new Label("Select Service name: ");
			serviceName.setStylePrimaryName("subHeaderLbl");
			
			defaultInterfaceNameLbl = new Label("Default Interface name:");
			defaultInterfaceNameLbl.setStylePrimaryName("subHeaderLbl");
			
			defaultInterfaceNameValueLbl = new Label();
			
			interfaceNameLbl = new Label("Select Interface name: ");
			interfaceNameLbl.setStylePrimaryName("subHeaderLbl");
			
			methodNameLbl = new Label("Select Method name: ");
			methodNameLbl.setStylePrimaryName("subHeaderLbl");
			
			paramsLbl = new Label("Parameters required:");
			paramsLbl.setStylePrimaryName("subHeaderLbl");
			
			paramsNeededLbl = new Label();
			
			interfaceOracle = new MultiWordSuggestOracle();
			methodOracle = new MultiWordSuggestOracle();
			serviceOracle = new MultiWordSuggestOracle();
			
			interfaceNameBox = new SuggestBox(interfaceOracle);
			interfaceNameBox.setStylePrimaryName("suggestion_list");
			
			methodNameBox = new SuggestBox(methodOracle);
			methodNameBox.setStylePrimaryName("suggestion_list");
			methodNameBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
				
				public void onSelection(SelectionEvent<Suggestion> event) {
					String selectedMethodName = methodNameBox.getText();
					createParametersUI(selectedMethodName);
				}
			});

			serviceNameBox = new SuggestBox(serviceOracle);
			serviceNameBox.setStylePrimaryName("suggestion_list");
			serviceNameBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
				
				public void onSelection(SelectionEvent<Suggestion> event) {
					String selectedServiceName = serviceNameBox.getText();
					getServiceInformation(selectedServiceName);
				}
			});
			
			goBtn = new Button("Go >>");
			goBtn.addClickHandler(this);
			paramMap = new HashMap<String, String>();
			dataForTestMap = new HashMap<String, Object>();
			initWidget(mainPanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createBasicUI() {
		mainPanel.clear();
		flexTable.clear();
		
		flexTable.setCellSpacing(3);
		
		flexTable.setWidget(row, col, serviceName);
		col = col + 2;
		flexTable.setWidget(row, col, serviceNameBox);
		
		row = row + 2;
		col = 2;
		
		mainPanel.add(flexTable);
		mainPanel.add(goBtn);
	}
	
	private void createUI() {
		
		flexTable.setWidget(row, col, defaultInterfaceNameLbl);
		col = col + 2;
		flexTable.setWidget(row, col, defaultInterfaceNameValueLbl);
		
		row = row + 2;
		col = 2;
		
		flexTable.setWidget(row, col, interfaceNameLbl);
		col = col + 2;
		flexTable.setWidget(row, col, interfaceNameBox);
		
		row = row + 2;
		col = 2;
		
		flexTable.setWidget(row, col, methodNameLbl);
		col = col + 2;
		flexTable.setWidget(row, col, methodNameBox);
		
		row = row + 2;
		col = 2;
		
		flexTable.setWidget(row, col, paramsLbl);
		col = col + 2;
	}
	
	@SuppressWarnings("unchecked")
	private void getServiceInformation(String seriveName) {
		try {
			Map map = new HashMap();
			map.put("serviceName", seriveName);
			
			StandardAction action = new StandardAction(Entity.class, "getDescriptor", map);
			dispatch.execute(action, new AsyncCallback<Result>() {
				
				
				public void onFailure(Throwable caught) {
					Window.alert("operation failed ");
					caught.printStackTrace();
				}
				
				
				public void onSuccess(Result result) {
					if(result.getOperationResult()!=null){
						descriptor = (ServiceDescriptor) result.getOperationResult();
						
						String defaultInterfaceName = null;//descriptor.getDefaultInterfaceName();
						
						if(defaultInterfaceName.contains(".")){
							String[] defaultInterfaceNameArr = defaultInterfaceName.split("\\.");
							defaultInterfaceName = defaultInterfaceNameArr[defaultInterfaceNameArr.length-1];
						}
						defaultInterfaceNameValueLbl.setText(defaultInterfaceName);
						
						HashMap<String, InterfaceDescriptor> interfaceMap = descriptor.getInterfaceDescriptors();
						if(interfaceMap.isEmpty())
							methodOracle.add("No interface available");
						else{
							for(String intrfNameKey: interfaceMap.keySet()){
								InterfaceDescriptor interfaceDescriptor = interfaceMap.get(intrfNameKey);
								String interfaceName = interfaceDescriptor.getName();
								
								if(interfaceName.contains(".")){
									String[] interfaceNameArr = interfaceName.split("\\.");
									interfaceName = interfaceNameArr[interfaceNameArr.length-1];
								}
								interfaceOracle.add(interfaceName);
								
								HashMap<String, OperationDescriptor> methodMap = interfaceDescriptor.getMethodDescriptors();
								if(methodMap.isEmpty())
									methodOracle.add("No method available");
								else{
									for(String methodNameKey: methodMap.keySet()){
										OperationDescriptor methodDescriptor = methodMap.get(methodNameKey);
										String methodName = methodDescriptor.getName();
										methodOracle.add(methodName);
									}
								}
							}
						}
						createUI();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createParametersUI(String selectedMethodName) {
		try {
			InterfaceDescriptor interfaceDescriptor = null;
			for(String intrfcNameKey : descriptor.getInterfaceDescriptors().keySet()){
				if(intrfcNameKey.contains(interfaceNameBox.getText())){
					interfaceDescriptor = descriptor.getInterfaceDescriptors().get(intrfcNameKey);
					break;
				}
			}
			if(interfaceDescriptor!=null){
				OperationDescriptor methodDescriptor = interfaceDescriptor.getMethodDescriptors().get(methodNameBox.getText());
				LinkedHashMap<String, String> paramMap = methodDescriptor.getParameters();
				if(!paramMap.isEmpty()){
					String allParams = "";
					int paramSize = paramMap.size();
					for(String paramNameKey : paramMap.keySet()){
						String str = paramNameKey+" of type "+ paramMap.get(paramNameKey);
						if(paramSize==1)
							allParams = allParams + str;
						else{
							allParams = allParams + str +", ";
							paramSize--;
						}
					}
					paramsNeededLbl.setText("( "+allParams+" )");
					flexTable.setWidget(row, col, paramsNeededLbl);
					row = row + 2;

					flexTable.setWidget(row, col, createAddParamUI(paramMap));
					row = row + 2;

				}else{
					paramsNeededLbl.setText("---");
					flexTable.setWidget(row, col, paramsNeededLbl);
					row = row + 2;
					col = 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getServiceList(){
		try {
			Map map = new HashMap();
			map.put("serviceName", "descriptor");
			StandardAction action = new StandardAction(Entity.class, "getAllServiceNames", map);
			dispatch.execute(action, new AsyncCallback<Result>() {
				
				
				public void onFailure(Throwable caught) {
					Window.alert("operation failed ");
					caught.printStackTrace();
				}
				
				
				public void onSuccess(Result result) {
					if(result.getOperationResult()!=null){
						Entity resultEntity = (Entity) result.getOperationResult();
						ArrayList<String> listOfServices = resultEntity.getPropertyByName("listOfServices");
						
						if(!listOfServices.isEmpty()){
							for(String servicename : listOfServices)
								serviceOracle.add(servicename);
						}
						createBasicUI();
					}
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HorizontalPanel createAddParamUI(final LinkedHashMap<String, String> parameterMap) {
		final HorizontalPanel panel = new HorizontalPanel();
		try {
			panel.setSpacing(8);
			panel.add(new Label("Parameter name: "));
			
			final TextBox paramNameTxt = new TextBox();
			panel.add(paramNameTxt);
			
			panel.add(new Label("Value: "));
			final TextBox paramValueTxt = new TextBox();
			panel.add(paramValueTxt);
			
			panel.add(new Label("Type: "));
			final ListBox listBox = new ListBox();
			listBox.addItem("--Select Type--");
			listBox.addItem("Integer");
			listBox.addItem("Long");
			listBox.addItem("Double");
			listBox.addItem("String");
			
			panel.add(listBox);
			
			listBox.addChangeHandler(new ChangeHandler() {
				
				public void onChange(ChangeEvent event) {
					ListBox listBox = (ListBox) event.getSource();
					if(paramNameTxt.getText().equals("") || paramValueTxt.getText().equals("") || paramValueTxt.getText().isEmpty()){
						PopupPanel popupPanel = new PopupPanel(true);
						popupPanel.add(new Label("Please enter value..!!"));
						popupPanel.setStylePrimaryName("notificationPopup");
						popupPanel.center();
					}else if(listBox.getSelectedIndex()!=0)
							paramMap.put(paramNameTxt.getText(),paramValueTxt.getText());
					else{
						PopupPanel popupPanel = new PopupPanel(true);
						popupPanel.setStylePrimaryName("notificationPopup");
						popupPanel.add(new Label("Please select type..!!"));
						popupPanel.center();
					}
				}
			});
			
			final Button addMoreBtn = new Button("Add more");
			panel.add(addMoreBtn);
			addMoreBtn.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					if(paramValueTxt.getText().equals("") || listBox.getSelectedIndex()==0){
						PopupPanel popupPanel = new PopupPanel(true);
						popupPanel.add(new Label("Please enter value..!!"));
						popupPanel.setStylePrimaryName("notificationPopup");
						popupPanel.center();
					}else{
						panel.remove(addMoreBtn);
						flexTable.setWidget(row, col, createAddParamUI(parameterMap));
						row = row + 2;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return panel;
	}

	
	public void onClick(ClickEvent event) {
		try {
			Button btn = (Button) event.getSource();
			if(btn.equals(goBtn)){
				if(serviceNameBox.getText().equals("") || interfaceNameBox.getText().equals("") || methodNameBox.getText().equals("")){
					PopupPanel popupPanel = new PopupPanel(true);
					popupPanel.add(new Label("Please select Service/Interface/Method name..!!"));
					popupPanel.setStylePrimaryName("notificationPopup");
					popupPanel.center();
				}else{
					String serviceName = serviceNameBox.getText();
					String methodName = methodNameBox.getText();
					
					dataForTestMap.put("Interface Name", interfaceNameBox.getText());
					dataForTestMap.put("Method Name", methodNameBox.getText());
					dataForTestMap.put("Parameters Map", paramMap);
					printData();
					
					callServiceToTestOperation(serviceName,methodName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void callServiceToTestOperation(String serviceName, String methodName) {
		try {
			Map map = new HashMap();
			
			if(!paramMap.isEmpty()){
				map.put("serviceName", serviceName);
				for(String name : paramMap.keySet())
					map.put(name, paramMap.get(name));
				//TODO how to pass parameters to the operation??
				//TODO entity creation??
			}else
				map.put("serviceName", serviceName);
			StandardAction action = new StandardAction(HelloPojo.class, methodName, map);
			dispatch.execute(action, new AsyncCallback<Result>() {
				
				
				public void onFailure(Throwable caught) {
					Window.alert("operation failed ");
					caught.printStackTrace();
				}
				
				
				public void onSuccess(Result result) {
					if(result.getOperationResult()!=null){
						PopupPanel popupPanel = new PopupPanel(true);
						popupPanel.add(new Label("Got result...tested successfully..!!!"));
						popupPanel.setStylePrimaryName("notificationPopup");
						popupPanel.center();
						
						Entity resultEntity = (Entity) result.getOperationResult();
						// TODO needs to be discussed what will be the return value?
					}
					dataForTestMap.clear();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printData() {
		try {
			String interfaceName = (String) dataForTestMap.get("Interface Name");
			System.out.println("Interface name: "+interfaceName);

			String methodName = (String) dataForTestMap.get("Method Name");
			System.out.println("Method name: "+methodName);

			HashMap<String, ArrayList<String>> paramMap = (HashMap<String, ArrayList<String>>) dataForTestMap.get("Parameters Map");
			if(!paramMap.isEmpty()){
				for(String type : paramMap.keySet())
					System.out.println("Value: "+ paramMap.get(type)+"  Type: "+type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
