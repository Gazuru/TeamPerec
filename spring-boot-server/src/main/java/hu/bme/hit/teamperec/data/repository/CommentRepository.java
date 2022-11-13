package hu.bme.hit.teamperec.data.repository;

import java.util.UUID;

import hu.bme.hit.teamperec.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
