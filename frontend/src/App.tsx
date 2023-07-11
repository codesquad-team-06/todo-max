import React from "react";
import styled, { ThemeProvider } from "styled-components";
import designSystem from "./styles/designSystem.ts";
import GlobalStyles from "./styles/GlobalStyles.ts";
import Header from "./components/Header.tsx";

export default function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <GlobalStyles />

      <StyledApp>
        <AppWrapper>
          <Header />
        </AppWrapper>
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  width: 100%;
  height: 100vh;
  background-color: ${({ theme: { colors } }) => colors.grey100};
`;

const AppWrapper = styled.div`
  width: 100%;
  max-width: 1200px;
  height: 100%;
  margin: auto;
`;
