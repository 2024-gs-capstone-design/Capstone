package com.capstone.backend.workspace.service;

import com.capstone.backend.workspace.dto.WorkspaceDTO;
import com.capstone.backend.workspace.model.Photo;
import com.capstone.backend.workspace.model.Workspace;
import com.capstone.backend.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final Path rootLocation = Paths.get("uploads"); // 파일 저장 경로

    // 워크스페이스 생성
    public void createWorkspace(String name) {
        if (workspaceRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 워크스페이스 이름입니다.");
        }

        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspaceRepository.save(workspace);
    }


    // 워크스페이스 가져오기
    public List<WorkspaceDTO> getAllWorkspaces() {
        return workspaceRepository.findAll()
                .stream()
                .map(workspace -> new WorkspaceDTO(workspace.getId(), workspace.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 워크스페이스 삭제 메서드
     * @param workspaceId 삭제할 워크스페이스 ID
     * @throws IllegalArgumentException 존재하지 않는 워크스페이스일 경우 예외 발생
     */
    public void deleteWorkspace(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new IllegalArgumentException("해당 워크스페이스가 존재하지 않습니다.");
        }

        // 워크스페이스 삭제 (연관된 Photo도 삭제됨)
        workspaceRepository.deleteById(workspaceId);

    }
}
