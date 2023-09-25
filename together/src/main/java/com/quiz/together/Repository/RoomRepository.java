package com.quiz.together.Repository;


import com.quiz.together.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Page<Room> findByIsPublic(boolean isPublic, Pageable pageable);
}
