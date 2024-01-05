import React from "react";
import logo from "./logo.svg";
import "./assets/style.css";
import A from "./pages/A";
import { Route, Routes } from "react-router-dom";
import Main from "./pages/Main";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Main/>}/>
        <Route path="/chat/:roomId" element={<A/>}/>
      </Routes>
    </>
  );
}

export default App;
