import React from "react";
import styled, { ThemeProvider } from "styled-components";
import designSystem from "./styles/designSystem.ts";
import GlobalStyles from "./styles/GlobalStyles.ts";
import "./App.css";
import logo from "./logo.svg";

const Title = styled.h1`
  color: ${({ theme: { colors } }) => colors.blue};
  font: ${({ theme: { font } }) => font.displayMD16};
`;

function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <GlobalStyles />

      <div className="App">
        <header className="App-header">
          <Title>Styled Components</Title>
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.tsx</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer">
            Learn React
          </a>
        </header>
      </div>
    </ThemeProvider>
  );
}

export default App;
