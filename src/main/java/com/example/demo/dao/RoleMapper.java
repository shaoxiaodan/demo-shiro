package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.example.demo.domain.Role;

public interface RoleMapper {

	/*
	 * 第二步：根据用户，查询所有角色
	 * select * from user_role ur
	 * left join role r on ur.role_id = r.id
	 * where ur.user_id=3;
	 */
	@Select("select ur.role_id as id, r.name as name, r.description as description"
			+ " from user_role ur"
			+ " left join role r on ur.role_id = r.id"
			+ " where ur.user_id= #{userId}")
	@Results(
			value = {
					@Result(id=true, property = "id", column = "id"),
					@Result(property = "name", column = "name"),
					@Result(property = "description", column = "description"),
					@Result(property = "permissionList", column = "id",
					many = @Many(select = "com.example.demo.dao.PermissionMapper.findPermissionListByRoleId", fetchType = FetchType.DEFAULT))
			})
	List<Role> findRoleListByUserId(@Param("userId") int userId);
	
	
	
	
}
