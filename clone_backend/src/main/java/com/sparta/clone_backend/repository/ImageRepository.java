package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository  extends JpaRepository<Image, Long> {
    Image findByImageUrl(String imageUrl);

}
