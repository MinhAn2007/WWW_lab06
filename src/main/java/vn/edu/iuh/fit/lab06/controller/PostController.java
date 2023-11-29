package vn.edu.iuh.fit.lab06.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.lab06.models.Post;
import vn.edu.iuh.fit.lab06.repositories.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        System.out.println(posts);
        model.addAttribute("posts", posts);
        return "post/postListing";
    }

    @GetMapping("/details/{postId}")
    public String getPostById(@PathVariable Long postId, Model model) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        model.addAttribute("post", post);
        return "post/postDetails";
    }
}
