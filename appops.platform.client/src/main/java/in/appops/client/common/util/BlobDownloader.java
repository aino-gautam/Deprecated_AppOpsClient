package in.appops.client.common.util;

import com.google.gwt.core.client.GWT;

public class BlobDownloader {
	
	 public String getImageDownloadURL(String blobId){
		return GWT.getHostPageBaseURL()+"download?"+blobId;
		 
	 }
	 public String getIconDownloadURL(String blobId){
		 return "i_"+GWT.getHostPageBaseURL()+"download?"+blobId;
		 
	 }

	 public String getThumbNailDownloadURL(String blobId){
		 return "t_"+GWT.getHostPageBaseURL()+"download?"+blobId;
		 
	 }
}
