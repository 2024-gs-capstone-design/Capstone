package com.capstone.backend.workspace.controller;

import com.capstone.backend.workspace.dto.WorkspaceDTO;
import com.capstone.backend.workspace.model.Workspace;
import com.capstone.backend.workspace.service.WorkspaceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /**
     * 워크스페이스 삭제 API
     * @param workspaceId 삭제할 워크스페이스 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<String> deleteWorkspace(@PathVariable Long workspaceId) {
        try {
            workspaceService.deleteWorkspace(workspaceId);
            return ResponseEntity.ok("워크스페이스가 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("삭제 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류로 인해 워크스페이스를 삭제할 수 없습니다.");
        }
    }

}
