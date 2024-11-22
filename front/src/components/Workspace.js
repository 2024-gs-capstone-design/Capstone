// src/components/WorkspaceList.js
import React, { useState, useEffect } from "react";
import axios from "axios";

const Workspace = () => {
  const [workspaces, setWorkspaces] = useState([]);
  const [workspaceName, setWorkspaceName] = useState("");
  const [selectedWorkspace, setSelectedWorkspace] = useState(null); // 선택된 워크스페이스
  const [photos, setPhotos] = useState([]); // 선택된 워크스페이스의 사진 목록
  const [fileInputs, setFileInputs] = useState({}); // 워크스페이스별 파일 입력 상태 관리
  const [selectedPhoto, setSelectedPhoto] = useState(null); // 클릭한 사진
  

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

  // 파일 추가 요청
  const addFileToWorkspace = async (workspaceId) => {
    const selectedFile = fileInputs[workspaceId];

    if (!selectedFile) {
      alert("파일을 선택하세요.");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile); // 파일 추가
    formData.append("workspaceId", workspaceId); // 워크스페이스 ID 추가

    try {
      await axios.post("http://localhost:8080/api/photos/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      alert("파일 추가 성공!");
    } catch (error) {
      console.error("파일 추가 중 오류 발생:", error);
    }
  };

  
  const handleFileChange = (workspaceId, file) => { // 파일 선택 이벤트 핸들러
    setFileInputs((prevState) => ({
      ...prevState,
      [workspaceId]: file,
    }));
  };

  // 워크스페이스 선택 및 사진 목록 가져오기
  const fetchPhotos = async (workspaceId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/photos/${workspaceId}`
      );
      setSelectedWorkspace(workspaceId); // 선택된 워크스페이스 설정
      setPhotos(response.data); // 사진 목록 업데이트
	  console.log(response)
    } catch (error) {
      console.error("사진 목록을 가져오는 중 오류 발생:", error);
    }
  };

  // 워크스페이스 삭제 요청
	const deleteWorkspace = async (workspaceId) => {
		const confirmDelete = window.confirm("정말 이 워크스페이스를 삭제하시겠습니까?");
		if (!confirmDelete) return;

		try {
			await axios.delete(`http://localhost:8080/api/workspace/${workspaceId}`);
			alert("워크스페이스가 삭제되었습니다.");
			// 삭제 후 워크스페이스 목록 갱신
			fetchWorkspaces();
		} catch (error) {
			console.error("워크스페이스 삭제 중 오류 발생:", error);
			alert("워크스페이스 삭제에 실패했습니다.");
		}
	};

  // 사진 클릭 이벤트 핸들러
  const handlePhotoClick = (photo) => {
    setSelectedPhoto(photo); // 클릭된 사진 데이터 저장
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
          <li key={workspace.id} style={{ margin: "10px 0" }}>
            <div>
              <strong>{workspace.name}</strong>
              <button
                onClick={() => fetchPhotos(workspace.id)} // 워크스페이스 선택 시 사진 목록 가져오기
                style={{ marginLeft: "10px" }}
              >
                선택
              </button>
			  <button onClick={() => deleteWorkspace(workspace.id)}>삭제</button>
              <div style={{ marginTop: "10px" }}>
                <input
                  type="file"
                  onChange={(e) =>
                    handleFileChange(workspace.id, e.target.files[0])
                  }
                  style={{ marginRight: "10px" }}
                />
                <button
                  onClick={() => addFileToWorkspace(workspace.id)} // 선택된 워크스페이스에 파일 추가
                  style={{ padding: "8px 15px" }}
                >
                  파일 추가
                </button>
				
              </div>
            </div>
          </li>
        ))}
      </ul>

      {/* {selectedWorkspace && (
        <div style={{ marginTop: "20px" }}>
          <h3>선택된 워크스페이스의 파일 목록</h3>
          <ul>
            {photos.map((photo) => (
              <li key={photo.id}>
                {photo.fileName} - {photo.filePath}
              </li>
            ))}
          </ul>
        </div>
      )} */}

      {selectedWorkspace && (
        <div style={{ marginTop: "20px" }}>
          <h3>선택된 워크스페이스의 파일 목록</h3>
          <ul>
            {photos.map((photo) => (
              <li
                key={photo.id}
                onClick={() => handlePhotoClick(photo)}
                style={{
                  cursor: "pointer",
                  textDecoration: "underline",
                  color: "blue",
                }}
              >
                {photo.fileName}
              </li>
            ))}
          </ul>
        </div>
      )}

      {selectedPhoto && (
        <div style={{ marginTop: "20px" }}>
          <h3>선택된 사진</h3>
          <img
            src={`http://localhost:8080/upload/photos/${selectedPhoto.fileName}`}
            alt={selectedPhoto.fileName}
            style={{ maxWidth: "100%", maxHeight: "400px" }}
          />
        </div>
      )}
    </div>
  );
};

export default Workspace;
