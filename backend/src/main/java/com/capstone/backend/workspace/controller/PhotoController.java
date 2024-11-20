package com.capstone.backend.workspace.controller;

import com.capstone.backend.workspace.dto.PhotoDTO;
import com.capstone.backend.workspace.model.Photo;
import com.capstone.backend.workspace.repository.PhotoRepository;
import com.capstone.backend.workspace.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/{workspaceId}/photos")
    public ResponseEntity<List<PhotoDTO>> getPhotosByWorkspace(@PathVariable Long workspaceId) {
        try {
            List<PhotoDTO> photos = photoService.getPhotosByWorkspace(workspaceId);
            return ResponseEntity.ok(photos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}