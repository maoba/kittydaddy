package com.kittydaddy.service.system.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.StatusEnum;
import com.kittydaddy.facade.convert.system.PermissionConvert;
import com.kittydaddy.facade.dto.system.LeftMenusDto;
import com.kittydaddy.facade.dto.system.PermissionDto;
import com.kittydaddy.metadata.system.dao.PermissionEntityMapper;
import com.kittydaddy.metadata.system.dao.RolePermissionEntityMapper;
import com.kittydaddy.metadata.system.dao.UserRoleEntityMapper;
import com.kittydaddy.metadata.system.domain.PermissionEntity;
import com.kittydaddy.metadata.system.domain.RolePermissionEntity;
import com.kittydaddy.metadata.system.domain.UserRoleEntity;
import com.kittydaddy.metadata.tenant.dao.TenantEntityMapper;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;
import com.kittydaddy.service.system.PermissionService;
import com.kittydaddy.service.system.RolePermissionService;
/**
 * @author kitty daddy
 * 权限服务
 */
@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
    private PermissionEntityMapper permissionMapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleMapper;
	
	@Autowired
	private RolePermissionEntityMapper rolePermissionMapper;
	
	@Autowired
	private TenantEntityMapper tenantMapper;
	
	/**
	 * 角色权限服务
	 */
	@Autowired
	private RolePermissionService rolePermissionService;
	
	/**
	 * 查询左侧的菜单项
	 */
	@Override
	public List<LeftMenusDto> queryLeftMenus(String userId, String tenantId) {
		List<UserRoleEntity> userRoleEntities = userRoleMapper.queryRole(userId, tenantId);
		//查询获取角色权限实体类
		Set<RolePermissionEntity> rolePermissionEntitys = rolePermissionService.queryRolePermissionEntity(userRoleEntities);
		List<LeftMenusDto> leftMenus = buildLeftMenu(rolePermissionEntitys);
		return leftMenus;
	}
	
	/**
	 * 构建左侧的导航站点
	 * @param rolePermissionEntitys
	 * @return
	 */
	private List<LeftMenusDto> buildLeftMenu(Set<RolePermissionEntity> rolePermissionEntitys) {
		if (CollectionUtils.isEmpty(rolePermissionEntitys)) return null;
		List<LeftMenusDto> list = new ArrayList<LeftMenusDto>();
		Map<String,LeftMenusDto> map = new HashMap<String, LeftMenusDto>();//记录已经找到的父节点以及树信息
		for(RolePermissionEntity rPentity : rolePermissionEntitys){
			PermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(rPentity.getPermissionId());
			if(StringUtils.isNotEmpty(permissionEntity.getParentId())){//如果有父节点
				 if(!map.containsKey(permissionEntity.getParentId())){
					 PermissionEntity parentPermissionEntity = permissionMapper.selectByPrimaryKey(permissionEntity.getParentId());
					 LeftMenusDto leftMenusDto = PermissionConvert.convert2LeftMeusDto(parentPermissionEntity,null);
					 List<LeftMenusDto> children = new ArrayList<LeftMenusDto>();
					 LeftMenusDto childMenuDto = PermissionConvert.convert2LeftMeusDto(permissionEntity,null);
					 children.add(childMenuDto);
					 leftMenusDto.setChildren(children);
					 map.put(permissionEntity.getParentId(), leftMenusDto);
				 }else{
					 LeftMenusDto menuDto = map.get(permissionEntity.getParentId());
					 LeftMenusDto childMenuDto = PermissionConvert.convert2LeftMeusDto(permissionEntity,null);
					 menuDto.getChildren().add(childMenuDto);
					 map.put(permissionEntity.getParentId(), menuDto);					 
				 }
			}
		}
		
		//获取最终的树
		Set<String> parentIds = map.keySet();
		if(CollectionUtils.isNotEmpty(parentIds)){
			for(String permissionId : parentIds){
				list.add(map.get(permissionId));
			}
		}
		return list;
	}

	
	@Override
	public PageInfo<PermissionEntity> queryPermissionsByPage(String name,String permissionType, String tenantId, Integer pageIndex,Integer pageSize) {
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<PermissionEntity> entitys = permissionMapper.queryPermissionByPage(name,tenantId,permissionType);
		PageInfo<PermissionEntity> page = new PageInfo<PermissionEntity>(entitys);
		return page;
	}
	
	@Override
	public List<PermissionDto> queryCatalogPermissions() {
		List<PermissionEntity> entitys = permissionMapper.queryCatalogPermission();
		List<PermissionDto> permissionDtos = PermissionConvert.convertEntitys2Dtos(entitys);
		return permissionDtos;
	}

	@Override
	public List<Map<String, Object>> queryPermissionTreeList(String tenantId) {
		List<Map<String,Object>> permissionMaps = new ArrayList<Map<String,Object>>();
		
		List<PermissionEntity> permissionEntities = permissionMapper.queryPermissionByTenantId(tenantId);
		if(CollectionUtils.isNotEmpty(permissionEntities)){
			if(CollectionUtils.isEmpty(permissionEntities)) return permissionMaps;
			for(PermissionEntity permissionEntity : permissionEntities){
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				map.put("id", permissionEntity.getId());
				map.put("pId", permissionEntity.getParentId()==null?"":permissionEntity.getParentId());
				map.put("name", permissionEntity.getModuleName());
				if(permissionEntity.getParentId()==null){
					map.put("open", true);
					map.put("isParent", true);
				}
				permissionMaps.add(map);
			}
		}
		return permissionMaps;
	}

	@Override
	public void saveUpdatePermission(Map<String, Object> params) {
		String moduleName = params.get("moduleName")==null?"":params.get("moduleName").toString();
		String permissionCode = params.get("permissionCode")==null?"": params.get("permissionCode").toString();
		String permissionUrl = "";
		if(params.containsKey("permissionUrl")) {
			permissionUrl = params.get("permissionUrl")==null?"": params.get("permissionUrl").toString();
		}
		
		Integer permissionType = 0;
		if(params.containsKey("permissionType")){
			permissionType = params.get("permissionType") == null?0:Integer.parseInt(params.get("permissionType").toString());
		}
		
		String permissionICO = params.get("permissionICO") == null?"":params.get("permissionICO").toString();
		String tenantId = params.get("tenantId")==null?"":params.get("tenantId").toString();
		String parentId = params.get("parentId")==null?"":params.get("parentId").toString();
		Integer terminalType = Integer.parseInt(params.get("terminalType").toString());
		String parentName = "";
		if(StringUtils.isNoneEmpty(parentId)){
			PermissionEntity parentPermission = permissionMapper.selectByPrimaryKey(parentId);
			parentName = parentPermission.getModuleName();
		}
		if(params.get("id")==null){//新增
			PermissionEntity permissionEntity = new PermissionEntity();
			permissionEntity.setCreateTime(new Date());
			permissionEntity.setModuleCode(permissionCode);
			permissionEntity.setModuleName(moduleName);
			permissionEntity.setParentId(parentId);
			permissionEntity.setParentName(parentName);
			permissionEntity.setPermissionCode(permissionCode);
			permissionEntity.setPermissionICO(permissionICO);
			permissionEntity.setPermissionType(permissionType);
			permissionEntity.setPermissionUrl(permissionUrl);
			permissionEntity.setStatus(StatusEnum.VALID.getValue());
			permissionEntity.setTerminalType(terminalType);
			permissionEntity.setTenantId(tenantId);
			permissionMapper.insert(permissionEntity);
			
		}else{//更新
			PermissionEntity oldPermission = permissionMapper.selectByPrimaryKey(params.get("id").toString());
			oldPermission.setModuleCode(permissionCode);
			oldPermission.setModuleName(moduleName);
			oldPermission.setParentId(parentId);
			oldPermission.setParentName(parentName);
			oldPermission.setPermissionCode(permissionCode);
			oldPermission.setPermissionType(permissionType);
			oldPermission.setPermissionUrl(permissionUrl);
			oldPermission.setStatus(StatusEnum.VALID.getValue());
			oldPermission.setTerminalType(terminalType);
			oldPermission.setTenantId(tenantId);
			oldPermission.setUpdateTime(new Date());
			permissionMapper.updateByPrimaryKey(oldPermission);
		}
	}///kvcontent/kvcontentList.html

	@Override
	public void deleteRelativeEntityById(String permissionId) {
		//根据id删除权限的实体类
		permissionMapper.deleteByPrimaryKey(permissionId);
		//根据id删除角色权限关系数据
		rolePermissionMapper.deleteByPermissionId(permissionId);
	}

	@Override
	public PermissionEntity queryPermissionById(String permissionId) {
		PermissionEntity entity = permissionMapper.selectByPrimaryKey(permissionId);
		if(null!=entity){
			TenantEntity tenant = tenantMapper.selectByPrimaryKey(entity.getTenantId());
			if(null!=tenant){
				entity.setTenantName(tenant.getName());
			}
		}
		return entity;
	}
	
}
