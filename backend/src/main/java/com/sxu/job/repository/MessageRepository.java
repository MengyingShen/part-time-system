package com.sxu.job.repository;

import com.sxu.job.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE " +
           "(m.sender.id = :userId1 AND m.receiver.id = :userId2) OR " +
           "(m.sender.id = :userId2 AND m.receiver.id = :userId1) " +
           "ORDER BY m.createdAt")
    List<Message> findConversation(Long userId1, Long userId2);
    
    @Query("SELECT m FROM Message m WHERE m.conversationId = :conversationId ORDER BY m.createdAt")
    List<Message> findByConversationId(String conversationId);
    
    @Query("SELECT m FROM Message m WHERE " +
           "m.conversationId = :conversationId AND m.receiver.id = :userId AND m.isRead = false")
    List<Message> findUnreadMessages(String conversationId, Long userId);
    
    @Query("SELECT DISTINCT m.conversationId FROM Message m WHERE " +
           "(m.sender.id = :userId OR m.receiver.id = :userId) " +
           "ORDER BY (SELECT MAX(m2.createdAt) FROM Message m2 WHERE m2.conversationId = m.conversationId) DESC")
    Page<String> findUserConversations(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT m FROM Message m WHERE m.conversationId = :conversationId " +
           "ORDER BY m.createdAt DESC")
    Page<Message> findLatestMessageInConversation(@Param("conversationId") String conversationId, Pageable pageable);
}
