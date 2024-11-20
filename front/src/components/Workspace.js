// src/components/WorkspaceList.js
import React, { useState, useEffect } from "react";
import axios from "axios";

const WorkspaceList = () => {
  const [workspaces, setWorkspaces] = useState([]);
  const [workspaceName, setWorkspaceName] = useState("");

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
            {workspace.name}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default WorkspaceList;
