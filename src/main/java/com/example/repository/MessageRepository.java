package com.example.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.Optional;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{
    
    List<Message> findByPostedBy(int postedBy);

}