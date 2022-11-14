package hu.bme.hit.teamperec.service;

import java.util.UUID;

import hu.bme.hit.teamperec.data.dto.CommentDto;
import hu.bme.hit.teamperec.data.entity.Comment;
import hu.bme.hit.teamperec.data.repository.CommentRepository;
import hu.bme.hit.teamperec.data.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final CAFFService caffService;

    private final UserService userService;

    public CommentResponse createComment(UUID caffId, CommentDto commentDto) {
        var comment = new Comment();
        var caff = caffService.getCaffById(caffId);
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(username);

        comment.setCommentText(commentDto.commentText());
        comment.setCaff(caff);
        comment.setCommenter(user);

        return toResponse(commentRepository.save(comment));
    }

    public void deleteComment(UUID commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        }
    }

    private CommentResponse toResponse(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getCommenter().getId(), comment.getCommentText());
    }
}
