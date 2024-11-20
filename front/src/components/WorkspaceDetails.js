// src/components/WorkspaceDetails.js
import React, { useState, useEffect } from "react";
import axios from "axios";

const WorkspaceDetails = ({ workspaceId, onBack }) => {
  const [photos, setPhotos] = useState([]);

  // 특정 워크스페이스의 사진 데이터 가져오기
  useEffect(() => {
    fetchPhotos();
  }, []);

  const fetchPhotos = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/workspace/${workspaceId}/photos`
      );
      setPhotos(response.data);
    } catch (error) {
      console.error("사진 데이터를 가져오는 중 오류 발생:", error);
    }
  };

  return (
    <div>
      <button onClick={onBack}>← 뒤로</button>
      <h2>워크스페이스 사진 목록</h2>
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
        <p>등록된 사진이 없습니다.</p>
      )}
    </div>
  );
};

export default WorkspaceDetails;
