import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';
import Workspace from './components/Workspace';
import WorkspaceDetails from './components/WorkspaceDetails';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path="/workspace" element={<Workspace />} />
        <Route path="/workspacedetails" element={<WorkspaceDetails />} />
      </Routes>
    </Router>
  );
}

export default App;