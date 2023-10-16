package com.quiz.together.Repository;

import com.quiz.together.Enum.UserStatus;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.User;
import com.quiz.together.entity.UserRoomRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRelationRepository extends JpaRepository<UserRoomRelation, String> {
    List<UserRoomRelation> findAllByRoom(Room room);

    List<UserRoomRelation> findAllByUser(User user);
}
