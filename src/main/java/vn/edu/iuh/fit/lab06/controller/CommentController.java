package vn.edu.iuh.fit.lab06.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.lab06.models.PostComment;
import vn.edu.iuh.fit.lab06.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.lab06.models.Post;
import vn.edu.iuh.fit.lab06.models.User;
import vn.edu.iuh.fit.lab06.repositories.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.iuh.fit.lab06.service.PostCommentServices;
import vn.edu.iuh.fit.lab06.service.UserServices;

import java.time.Instant;
import java.util.List;
import java.util.Set;
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommentServices postCommentServices;
    @Autowired
    private UserServices userServices;
    @GetMapping("/comment/{postId}")
    public String showCommentForm(@PathVariable Long postId, Model model) {
        System.out.println("t");

        Post parentPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Parent Post not found with id: " + postId));
        if (postRepository.existsById(postId)) {
            PostComment comment = new PostComment();
            comment.setPost(parentPost);
            model.addAttribute("comment", comment);
            System.out.println("t");
            return "comment/addComment";
        } else {
            return "redirect:/comment";
        }
    }

    @PostMapping("/addComment")
    public String createComment(@ModelAttribute("comment") PostComment comment,@RequestParam("postId") Long postId,@RequestParam("userId") Long userId) {
        comment.setCreatedAt(Instant.now());
        comment.setPublishedAt(Instant.now());
        comment.setPublished(true);
        comment.setUser(userServices.findById(userId).orElseThrow());
        comment.setPost(postRepository.findById(postId).orElseThrow());
        postCommentServices.save(comment);
        return "redirect:/posts/details/" + postId;
    }

    @GetMapping("/comments/{postId}")
    public String showComments(@PathVariable Long postId, Model model) {
        Set<PostComment> comments = postCommentServices.findByPostId(postId);
        model.addAttribute("comments", comments);


        return "comment/commentListing";
    }
}
