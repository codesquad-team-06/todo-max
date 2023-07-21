import React from "react";
import { styled, ThemeProvider } from "styled-components";
import designSystem from "./styles/designSystem.ts";
import GlobalStyles from "./styles/GlobalStyles.ts";
import Header from "./components/Header.tsx";
import Board from "./components/Board.tsx";
import { ModalProvider } from "./context/ModalContext.tsx";

export default function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <GlobalStyles />
      <StyledApp>
        <ModalProvider>
          <MainWrapper>
            <Header />
            <Board />
          </MainWrapper>
        </ModalProvider>
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  width: 100%;
  height: 100vh;
  background-color: ${({ theme: { colors } }) => colors.grey100};
  overflow: hidden;
  position: relative;
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
