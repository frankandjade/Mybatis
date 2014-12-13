package com.apollo.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.apollo.mybatis.inter.IUserOperator;
import com.apollo.mybatis.model.Article;
import com.apollo.mybatis.model.User;

public class Test {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader; 
	private static IUserOperator userOperator;
	
	/**
	 * 读Configuration.xml配置文件
	 */
	static{
		try{
			reader    = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSessionFactory(){
		return sqlSessionFactory;
	}

	public static void main(String[] args) {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			userOperator = session.getMapper(IUserOperator.class);
			List<Article> objs = userOperator.getUserArticles(1);
			for(Article obj : objs){
				sop(obj.getTitle() + "=====" + obj.getId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	private static void deleteUserByID(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			userOperator = session.getMapper(IUserOperator.class);
			userOperator.deleteUser(id);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	private static void updateUserID(int oldId,int newID) {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			userOperator = session.getMapper(IUserOperator.class);
			User user = userOperator.selectUserByID(oldId);
			user.setId(newID);
			userOperator.updateUser(user);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	private static void saveUser() {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			User user = new User("frank","小芝虎山路177号",24);
			IUserOperator userOprator = session.getMapper(IUserOperator.class);
			userOprator.saveUser(user);
			session.commit();
			//session.commit(true);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	private static void getUser(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			/*
			 * selectOne:查询一条数据
			 */
			//User user = (User) session.selectOne("com.apollo.mybatis.models.UserMapper.selectUserByID", 1);

			/*
			 *IUserOperator接口操作更好 
			 */
			IUserOperator userOperator = session.getMapper(IUserOperator.class);
			User user = userOperator.selectUserByID(id);
			sop(user.getUserAddress());
			sop(user.getUserName());
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unused")
	private static void getUserListByName(String name) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			/*
			 * selectOne:查询一条数据
			 */
			//User user = (User) session.selectOne("com.apollo.mybatis.models.UserMapper.selectUserByID", 1);

			/*
			 *IUserOperator接口操作更好 
			 */
			IUserOperator userOperator = session.getMapper(IUserOperator.class);
			User user = userOperator.selectUserByID(3);
			sop(user);
			sop(user.getUserName());
			
			List<User> users = userOperator.selectUsers(name);
			for(User obj : users){
				sop(obj.getUserAddress());
			}
		} finally {
			session.close();
		}
	}

	private static void sop(Object object) {
		System.out.println(object);
	}
}