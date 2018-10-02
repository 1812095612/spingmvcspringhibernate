package com.qiaosoftware.surveyproject.entity.manager;

public class Res {
	
	private Integer resId;
	
	//servletPath
	private String resName;
	
	//权限码
	private Integer resCode;
	
	//权限位
	private Integer resPos;
	
	private boolean publicRes = false;
	
	public Res() {
		
	}

	public Res(Integer resId, String resName, Integer resCode, Integer resPos,
			boolean publicRes) {
		super();
		this.resId = resId;
		this.resName = resName;
		this.resCode = resCode;
		this.resPos = resPos;
		this.publicRes = publicRes;
	}

	@Override
	public String toString() {
		return "Res [resId=" + resId + ", resName=" + resName + ", resCode="
				+ resCode + ", resPos=" + resPos + ", publicRes=" + publicRes
				+ "]";
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Integer getResCode() {
		return resCode;
	}

	public void setResCode(Integer resCode) {
		this.resCode = resCode;
	}

	public Integer getResPos() {
		return resPos;
	}

	public void setResPos(Integer resPos) {
		this.resPos = resPos;
	}

	public boolean isPublicRes() {
		return publicRes;
	}

	public void setPublicRes(boolean publicRes) {
		this.publicRes = publicRes;
	}

}
