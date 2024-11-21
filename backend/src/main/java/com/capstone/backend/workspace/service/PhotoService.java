package com.capstone.backend.workspace.service;

import com.capstone.backend.workspace.controller.WorkspaceController;
import com.capstone.backend.workspace.dto.PhotoDTO;
import com.capstone.backend.workspace.model.Photo;
import com.capstone.backend.workspace.model.Workspace;
import com.capstone.backend.workspace.repository.PhotoRepository;
import com.capstone.backend.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final WorkspaceRepository workspaceRepository;

    private static final String UPLOAD_DIR = "/uploads/";

    public String uploadPhoto(MultipartFile file, Long workspaceId) throws IOException {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));

        // 파일 저장
        String originalFileName = file.getOriginalFilename();
        String filePath = UPLOAD_DIR + originalFileName;
        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);

        // Photo 엔티티 저장
        Photo photo = new Photo();
        photo.setFileName(originalFileName);
        photo.setFilePath(filePath);
        photo.setWorkspace(workspace);

        photoRepository.save(photo);

        return filePath;
    }


    /**
     * 특정 워크스페이스에 연결된 사진 목록을 가져옵니다.
     *
     * @param workspaceId 워크스페이스 ID
     * @return 사진 목록 (PhotoDTO 리스트)
     * @throws IllegalArgumentException 워크스페이스가 존재하지 않을 경우 예외 발생
     */
    public List<PhotoDTO> getPhotosByWorkspaceId(Long workspaceId) {
//        // 워크스페이스 확인
//        Optional<Photo> workspaceOptional = photoRepository.findById(workspaceId);
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new IllegalArgumentException("해당 ID의 워크스페이스를 찾을 수 없습니다.");
        }

        // 사진 목록 가져오기
        List<Photo> photos = photoRepository.findByWorkspaceId(workspaceId);

        // Photo -> PhotoDTO 변환
        return photos.stream()
                .map(photo -> new PhotoDTO(photo.getId(), photo.getFileName(), photo.getFilePath()))
                .collect(Collectors.toList());

    }

}
