package in.appops.client.common.util;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwtphonegap.client.PhoneGap;

public class BlobDownloader {
	
	private static final PhoneGap phoneGap = GWT.create( PhoneGap.class );
	
	 public String getImageDownloadURL(String blobId){
		 if(phoneGap.isPhoneGapDevice()){
			 return "http://server.ensarm.com:4080/Trumart/"+"download?blobId="+blobId;
		 }
		 else
			 return GWT.getHostPageBaseURL()+"download?blobId="+blobId;
		 
	 }
	 public String getIconDownloadURL(String blobId){
		 if(phoneGap.isPhoneGapDevice()){
			 return "http://server.ensarm.com:4080/Trumart/"+"download?blobId=i_"+blobId;
		 }
		 else
			 return GWT.getHostPageBaseURL()+"download?blobId=i_"+blobId;
	 }

	 public String getThumbNailDownloadURL(String blobId){
		 if(phoneGap.isPhoneGapDevice()){
			 return "http://server.ensarm.com:4080/Trumart/"+"download?blobId=t_"+blobId;
		 }
		 else
			 return GWT.getHostPageBaseURL()+"download?blobId=t_"+blobId;
	 }
}
