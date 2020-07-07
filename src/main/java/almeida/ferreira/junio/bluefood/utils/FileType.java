package almeida.ferreira.junio.bluefood.utils;

import lombok.Getter;

@Getter
public enum FileType {
	
	PNG("image/png", "png"),
	PJG("image/jpeg", "jpg");
	
	private String mimeType;
	private String extention;
	
	private FileType(String mimeType, String extention) {
		this.mimeType = mimeType;
		this.extention = extention;
	}
	
	private boolean sameOf(String mimeType) {
		return this.mimeType.equalsIgnoreCase(mimeType);
	}
	
	public static FileType of(String mimeType) {
		
		for(FileType type : values()) {
			if(type.sameOf(mimeType)) {
				return type;
			}
		}
		return null;
	}
}
