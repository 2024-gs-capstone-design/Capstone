package com.capstone.backend.workspace.controller;

import com.capstone.backend.workspace.dto.WorkspaceDTO;
import com.capstone.backend.workspace.model.Workspace;
import com.capstone.backend.workspace.service.WorkspaceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
public class WorkspaceController {


    private final WorkspaceService workspaceService;

//    @PostMapping("/create") // 워크스페이스 생성
//    public ResponseEntity<String> createWorkspace(@RequestBody WorkspaceDTO workspaceDTO) {
//        try {
//            workspaceService.createWorkspace(workspaceDTO.getName());
//            return ResponseEntity.ok("워크스페이스 생성");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    // 워크스페이스 생성
    @PostMapping("/create")
    public ResponseEntity<String> createWorkspace(@RequestBody String name) {
        workspaceService.createWorkspace(name);
        return ResponseEntity.ok("워크스페이스가 생성되었습니다.");
    }

    // 워크스페이스 목록 가져오기
    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        List<WorkspaceDTO> workspaces = workspaceService.getAllWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam String workspaceName,
            @RequestParam MultipartFile file
    ) {
        try {
            workspaceService.saveFile(workspaceName, file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok("파일이 업로드되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("파일 업로드 실패: " + e.getMessage());
        }
    }
}
