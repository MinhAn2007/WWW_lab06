package vn.edu.iuh.fit.lab06.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.lab06.repositories.PostRepository;

@Service
@NoArgsConstructor @AllArgsConstructor
public class PostServices {
    private PostRepository postRepository;
}

