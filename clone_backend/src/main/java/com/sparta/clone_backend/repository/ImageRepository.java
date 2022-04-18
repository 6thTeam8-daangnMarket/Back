package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Image;
import com.sparta.clone_backend.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository  extends JpaRepository<Image, Long> {
    Image findByImageUrl(String imageUrl);

}
