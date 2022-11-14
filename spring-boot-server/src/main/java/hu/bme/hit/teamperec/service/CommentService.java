package hu.bme.hit.teamperec.service;

import java.util.UUID;

import hu.bme.hit.teamperec.data.dto.CommentDto;
import hu.bme.hit.teamperec.data.entity.Comment;
import hu.bme.hit.teamperec.data.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final CAFFService caffService;

    private final UserService userService;

    public Comment createComment(UUID caffId, CommentDto commentDto) {
        var comment = new Comment();
        var caff = caffService.getCaff(caffId);
        var user = userService.getUser(commentDto.commenter());

        comment.setCommentText(commentDto.commentText());
        comment.setCaff(caff);
        comment.setCommenter(user);

        return commentRepository.save(comment);
    }

    public void deleteComment(UUID commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        }
    }
}
