import React from "react";
import { styled, ThemeProvider } from "styled-components";
import designSystem from "./styles/designSystem.ts";
import GlobalStyles from "./styles/GlobalStyles.ts";
import Header from "./components/Header.tsx";
import MainContent from "./components/MainContent.tsx";
import ActivityHistory from "./components/ActivityHistory.tsx";
import Modal from "./components/Modal.tsx";

export default function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <GlobalStyles />
      <StyledApp>
        <MainWrapper>
          <Header />
          <MainContent />
          <ActivityHistory />
          <Modal>
            <h2>모든 사용자 활동기록을 삭제할까요?</h2>
          </Modal>
        </MainWrapper>
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  width: 100%;
  height: 100vh;
  background-color: ${({ theme: { colors } }) => colors.grey100};
`;

const MainWrapper = styled.div`
  width: 100%;
  max-width: 1440px;
  height: 100%;
  margin: auto;
  padding-inline: 80px;
  display: flex;
  flex-direction: column;
`;
