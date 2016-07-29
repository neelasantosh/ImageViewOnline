package com.example.onlineimageviewer;

public class ImageData {

	private String imageTitle;
	private String imageURL;
	private String imageFileName;

	public ImageData(String imageTitle, String imageURL, String imageFileName) {
		super();
		this.imageTitle = imageTitle;
		this.imageURL = imageURL;
		this.imageFileName = imageFileName;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	@Override
	public String toString() {
		return imageTitle;
	}

}
