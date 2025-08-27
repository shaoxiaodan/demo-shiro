package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Permission;

public interface PermissionMapper {

	/*
	 * 第三步：根据角色，查询所有权限
	 * select * from role_permission rp
	 * left join permission p on rp.permission_id = p.id
	 * where rp.role_id = 3;
	 */
	@Select("select p.id as id, p.name as name, p.url as url"
			+ " from role_permission rp"
			+ " left join permission p on rp.permission_id = p.id"
			+ " where rp.role_id = #{roleId}")
	List<Permission> findPermissionListByRoleId(@Param("roleId") int roleId);
	
}
