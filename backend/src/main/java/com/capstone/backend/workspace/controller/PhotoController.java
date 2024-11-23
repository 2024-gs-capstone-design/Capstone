package com.capstone.backend.workspace.controller;

import com.capstone.backend.workspace.dto.PhotoDTO;
import com.capstone.backend.workspace.service.PhotoService;
import com.capstone.backend.workspace.service.WorkspaceService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final WorkspaceService workspaceService;

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

    // 파일 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("workspaceId") Long workspaceId) {
        System.out.println("File: " + file.getOriginalFilename());
        System.out.println("Workspace ID: " + workspaceId);
        // 파일의 MIME 타입 확인
        String mimeType = file.getContentType();
        System.out.println("업로드된 파일의 MIME 타입: " + mimeType);
        try {
            String filePath = photoService.uploadPhoto(file, workspaceId);
            return ResponseEntity.ok("파일 업로드 성공: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @GetMapping("/upload/photos/{fileName:.+}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("/Users/oooh/git/capstone/backend/src/main/resources/static/upload/photos").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}