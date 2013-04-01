package in.appops.client.common.components;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.MultiUploader;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class WebMediaAttachWidget extends MediaAttachWidget {
	
	public WebMediaAttachWidget(){
		super();
	}

	@Override
	void createUi() {
		HorizontalPanel mediaOptionsPanel = new HorizontalPanel();
		mediaOptionsPanel.setStylePrimaryName("appops-webMediaAttachment");
		mediaOptionsPanel.setBorderWidth(1);
		initWidget(mediaOptionsPanel);
		
		MultiUploader multiUploader = getMultiUploader();
		mediaOptionsPanel.add(multiUploader);
		
	}
	
	public MultiUploader getMultiUploader(){
		MultiUploader fileUploader = new MultiUploader(FileInputType.BROWSER_INPUT);
		fileUploader.avoidRepeatFiles(true);
		
		fileUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			
			@Override
			public void onFinish(IUploader uploader) {
				
			}
		});
		
		return fileUploader;
	}

}
