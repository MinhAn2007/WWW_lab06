package vn.edu.iuh.fit.lab06.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.lab06.models.PostComment;
import vn.edu.iuh.fit.lab06.repositories.PostCommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentServices {
    @Autowired
    private PostCommentRepository postCommentRepository;
    public void save(PostComment postComment){ postCommentRepository.save(postComment);}
    public Set<PostComment> findByPostId(long id){return postCommentRepository.findByPostId(id);}

    public Optional<PostComment> findById(long id){return postCommentRepository.findById(id);}

    public List<PostComment> getRepliesByCommentId(Long commentId) {
        return postCommentRepository.getRepliesByParentId(commentId);
    }
}
