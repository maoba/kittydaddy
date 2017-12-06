package com.kittydaddy.facade.dto.system;

import java.util.List;

/**
 * @author kitty daddy
 * 左侧导航菜单
 */
public class LeftMenusDto {
	 /**
     * 权限名称
     */
	 private String title;
    
	 /**
	  * 权限图标
	  */
    private String icon;
    
    /**
     * 权限对应的资源url
     */
    private String href;
    
    /**
     * 默认是否展开(true:展开 false:闭合)
     */
    private Boolean spread;
    
    /**
     * 子目录
     */
    private List<LeftMenusDto> children;

    
	public Boolean getSpread() {
		return spread;
	}

	public void setSpread(Boolean spread) {
		this.spread = spread;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<LeftMenusDto> getChildren() {
		return children;
	}

	public void setChildren(List<LeftMenusDto> children) {
		this.children = children;
	}
}
