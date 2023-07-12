import React from "react";
import { ThemeProvider } from "styled-components";
import designSystem from "./styles/designSystem.ts";
import GlobalStyles from "./styles/GlobalStyles.ts";
import "./App.css";
import Modal from "./Modal.tsx";

function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <GlobalStyles />
      <div className="App">
        <Modal>
          <h2>모든 사용자 활동기록을 삭제할까요?</h2>
        </Modal>
      </div>
    </ThemeProvider>
  );
}

export default App;
