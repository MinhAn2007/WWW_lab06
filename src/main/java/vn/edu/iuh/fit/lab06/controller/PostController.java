package vn.edu.iuh.fit.lab06.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.lab06.models.Post;
import vn.edu.iuh.fit.lab06.models.User;
import vn.edu.iuh.fit.lab06.repositories.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.iuh.fit.lab06.service.UserServices;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserServices userServices;

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        System.out.println(posts);
        model.addAttribute("posts", posts);
        return "post/postListing";
    }

    @GetMapping("/details/{postId}")
    public String getPostById(@PathVariable Long postId, Model model) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        model.addAttribute("post", post);
        return "post/postDetails";
    }

    @GetMapping("/newsubposts/{parentId}")
    public String showNewSubPostForm(@PathVariable Long parentId, @ModelAttribute Post subPost, Model model) {
        Post parentPost = postRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent Post not found with id: " + parentId));
        model.addAttribute("post", parentPost);
        model.addAttribute("subPost", new Post());
        System.out.println("t");
        return "post/newSubPost";
    }


    @PostMapping("/newsubposts")
    public String createNewSubPost(@ModelAttribute("subPost") Post subPost, Model model, @RequestParam("parentId") long parentId, @RequestParam("authorId") long authorId) {
        subPost.setParent(postRepository.findById(parentId).orElseThrow());
        subPost.setCreatedAt(Instant.now());
        subPost.setPublished(true);
        subPost.setPublishedAt(Instant.now());
        subPost.setUpdatedAt(Instant.now());
        subPost.setAuthor(userServices.findById(authorId).orElseThrow());
        postRepository.save(subPost);
        return "redirect:/posts/details/" + subPost.getParent().getId();
    }
    @GetMapping("/subposts/{postId}")
    public String viewSubPosts(@PathVariable Long postId, Model model) {
        Post parentPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        Set<Post> subPosts = parentPost.getPosts();
        model.addAttribute("post", parentPost);
        model.addAttribute("subPosts", subPosts);
        return "post/subPost";
    }

    @GetMapping("/add")
    public String showAddPostForm(@ModelAttribute("newPost") Post newPost, Model model) {
        model.addAttribute("newPost", new Post());
        return "post/addPost";
    }

    @PostMapping("/add")
    public String createNewPost(@ModelAttribute("newPost") Post newPost, Model model,@RequestParam("authorId") Long authorId) {
        newPost.setAuthor(userServices.findById(authorId).orElseThrow());
        newPost.setCreatedAt(Instant.now());
        newPost.setPublishedAt(Instant.now());
        newPost.setPublished(true);
        postRepository.save(newPost);
        return "redirect:/posts/posts";
    }
    @GetMapping("/update/{postId}")
    public String showUpdateForm(@PathVariable Long postId, Model model) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        model.addAttribute("post", post);
        return "post/update";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute Post updatedPost,@RequestParam("authorId") long authorId) {
        updatedPost.setAuthor(userServices.findById(authorId).orElseThrow());
        updatedPost.setUpdatedAt(Instant.now());
        postRepository.save(updatedPost);
        return "redirect:/posts/details/" + updatedPost.getId();
    }
}
