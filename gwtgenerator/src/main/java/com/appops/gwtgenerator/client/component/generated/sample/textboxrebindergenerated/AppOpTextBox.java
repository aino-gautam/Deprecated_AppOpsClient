package com.appops.gwtgenerator.client.component.generated.sample.textboxrebindergenerated;

import java.io.Serializable;
import java.util.ArrayList;

import com.appops.gwtgenerator.client.component.presenter.Presenter;
import com.appops.gwtgenerator.client.config.annotation.Tag;
import com.appops.gwtgenerator.client.generator.Dynamic;

@Tag(tagname = "TextBox", library = "core")
public class AppOpTextBox extends com.google.gwt.user.client.ui.TextBox implements Dynamic {
	public AppOpTextBox() {
		super();
	}
	
	Presenter	presenter;
	
	@Override
	public Object im(String methodName, ArrayList<Serializable> parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Presenter getPresenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}
	
}
