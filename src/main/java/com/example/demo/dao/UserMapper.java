package com.example.demo.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.User;

public interface UserMapper {

	/*
	 * 第一步：
	 * select * from user u 
	 * left join user_role ur on u.id = ur.user_id //查询用户对应的角色映射关系
	 * where u.id=3; // 查询某个用户的角色对应的权限
	 */
	
	@Select("select * from user where username = #{username}")
	User findByUsername(@Param("username") String username);

	@Select("select * from user where id = #{userId}")
	User findById(@Param("userId") int id);

	@Select("select * from user where username = #{username} and password = #{pwd}")
	User findByUsernameAndPwd(@Param("username") String username, @Param("pwd") String pwd);

}
