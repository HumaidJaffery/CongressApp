package com.quiz.together.Repository;

import com.quiz.together.entity.UserRoomRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoomRelationRepository extends JpaRepository<UserRoomRelation, Long> {

}
