package vn.edu.iuh.fit.lab06.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.lab06.models.PostComment;

import java.util.List;
import java.util.Set;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Set<PostComment> findByPostId(long id);

    List<PostComment> getRepliesByParentId(Long commentId);
}