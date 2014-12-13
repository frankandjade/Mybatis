package com.apollo.mybatis.inter;
import java.util.List;

import com.apollo.mybatis.model.Article;
import com.apollo.mybatis.model.User;

public interface IUserOperator {    
	
	User selectUserByID(int id);
	
	List<User> selectUsers(String userName);

	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(int id);
	
	void deleteUser(User user);
	
	List<Article> getUserArticles(int id);
}