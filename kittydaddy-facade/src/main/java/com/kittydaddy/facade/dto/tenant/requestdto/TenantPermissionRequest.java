package com.kittydaddy.facade.dto.tenant.requestdto;

import java.util.List;

/**
 * @author kitty daddy
 * 租户开通权限功能
 */
public class TenantPermissionRequest {
        /**
         * 租户id
         */
	    private Long tenantId;
      
	     /**
	      * 用户id
	      */
	    private Long userId;
	    
	    /**
	     * 权限id
	    */
        private List<Long> permissionIds;
        
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getTenantId() {
			return tenantId;
		}
	
		public void setTenantId(Long tenantId) {
			this.tenantId = tenantId;
		}

		public List<Long> getPermissionIds() {
			return permissionIds;
		}

		public void setPermissionIds(List<Long> permissionIds) {
			this.permissionIds = permissionIds;
		}
}
