package nl.pubsong.operations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository 
extends CrudRepository<User, Long> {
	public User findByuserName(@Param("userName")String userName);
}