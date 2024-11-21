package com.capstone.backend.workspace.repository;

import com.capstone.backend.workspace.model.Photo;
import com.capstone.backend.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByName(String name);

}