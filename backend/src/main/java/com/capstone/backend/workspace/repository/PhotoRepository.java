package com.capstone.backend.workspace.repository;

import com.capstone.backend.workspace.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByWorkspaceId(Long workspaceId);
}