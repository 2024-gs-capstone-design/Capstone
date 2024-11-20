package com.capstone.backend.workspace.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class  Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName; // 파일 이름

    private String filePath; // 파일 경로

    @ManyToOne
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace; // 워크스페이스와 연결
}
