// src/components/WorkspaceList.js
import React, { useState, useEffect } from "react";
import axios from "axios";

const WorkspaceList = () => {
  const [workspaces, setWorkspaces] = useState([]);
  const [workspaceName, setWorkspaceName] = useState("");
  const [selectedWorkspace, setSelectedWorkspace] = useState(null); // 선택된 워크스페이스
  const [photos, setPhotos] = useState([]); // 선택된 워크스페이스의 사진 목록

  // 워크스페이스 목록 가져오기
  useEffect(() => {
    fetchWorkspaces();
  }, []);

  const fetchWorkspaces = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/workspace");
      setWorkspaces(response.data);
    } catch (error) {
      console.error("워크스페이스 목록을 가져오는 중 오류 발생:", error);
    }
  };

  // 워크스페이스 생성 요청
  const createWorkspace = async (e) => {
    e.preventDefault(); // 폼 제출 기본 동작 방지

    if (!workspaceName) {
      alert("워크스페이스 이름을 입력하세요.");
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/workspace/create", workspaceName,{
        headers: { "Content-Type": "text/plain" },
      });
      setWorkspaceName(""); // 입력 필드 초기화
      fetchWorkspaces(); // 새로 생성된 워크스페이스 목록 갱신
    } catch (error) {
      console.error("워크스페이스 생성 중 오류 발생:", error);
    }
  };

  // 워크스페이스 선택 및 사진 목록 가져오기
  const fetchPhotos = async (workspaceId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/workspace/${workspaceId}/photos`
      );
      setSelectedWorkspace(workspaceId); // 선택된 워크스페이스 설정
      setPhotos(response.data); // 사진 목록 업데이트
    } catch (error) {
      console.error("사진 목록을 가져오는 중 오류 발생:", error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>워크스페이스 목록</h2>
      <form onSubmit={createWorkspace} style={{ marginBottom: "20px" }}>
        <input
          type="text"
          placeholder="워크스페이스 이름 입력"
          value={workspaceName}
          onChange={(e) => setWorkspaceName(e.target.value)}
          style={{ padding: "8px", marginRight: "10px" }}
        />
        <button type="submit" style={{ padding: "8px 15px" }}>
          워크스페이스 생성
        </button>
      </form>

      <ul>
        {workspaces.map((workspace) => (
          <li
            key={workspace.id}
            style={{ margin: "10px 0", cursor: "pointer", color: "blue" }}
            onClick={() => fetchPhotos(workspace.id)} // 클릭 시 사진 목록 가져오기
          >
            {workspace.name}
          </li>
        ))}
      </ul>

      {selectedWorkspace && (
        <div style={{ marginTop: "20px" }}>
          <h3>선택된 워크스페이스 ID: {selectedWorkspace}</h3>
          <h4>사진 목록:</h4>
          {photos.length > 0 ? (
            <ul>
              {photos.map((photo) => (
                <li key={photo.id}>
                  <p>파일 이름: {photo.fileName}</p>
                  <p>파일 경로: {photo.filePath}</p>
                </li>
              ))}
            </ul>
          ) : (
            <p>해당 워크스페이스에 사진이 없습니다.</p>
          )}
        </div>
      )}
    </div>
  );
};

export default WorkspaceList;
