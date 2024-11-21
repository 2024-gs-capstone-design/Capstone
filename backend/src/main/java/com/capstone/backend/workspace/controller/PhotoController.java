package com.capstone.backend.workspace.controller;

import com.capstone.backend.workspace.dto.PhotoDTO;
import com.capstone.backend.workspace.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    // workspace 정보 가져오기
    @GetMapping("/{workspaceId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByWorkspaceId(@PathVariable Long workspaceId) {
        try {
            List<PhotoDTO> photos = photoService.getPhotosByWorkspaceId(workspaceId);
            return ResponseEntity.ok(photos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhotoToWorkspace(
            @RequestParam("file") MultipartFile file,
            @RequestParam("workspaceId") Long workspaceId
    ) {
        try {
            String filePath = photoService.uploadPhoto(file, workspaceId);
            return ResponseEntity.ok("파일 업로드 성공: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패: " + e.getMessage());
        }
    }
}