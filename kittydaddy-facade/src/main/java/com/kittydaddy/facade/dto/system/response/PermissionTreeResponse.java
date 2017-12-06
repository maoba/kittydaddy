package com.kittydaddy.facade.dto.system.response;

import java.util.List;

/**
 * @author kitty daddy
 * 权限树返回[权限树目前只会有两层]
 */
public class PermissionTreeResponse {
     /**
      * 权限名称
      */
	 private String title;
     
	 /**
	  * 权限图标
	  */
     private String ico;
     
     /**
      * 权限对应的资源url
      */
     private String href;
     
     /**
      * 子目录
      */
     private List<PermissionTreeResponse> child;
     
	 public String getTitle() {
		return title;
	 }

	 public void setTitle(String title) {
		this.title = title;
	 }

	 public String getIco() {
		return ico;
	 }

	 public void setIco(String ico) {
	 	this.ico = ico;
	 }

	 public String getHref() {
		return href;
	 }

	 public void setHref(String href) {
		this.href = href;
	 }

	 public List<PermissionTreeResponse> getChild() {
		return child;
	 }

	 public void setChild(List<PermissionTreeResponse> child) {
		this.child = child;
	 }
}
