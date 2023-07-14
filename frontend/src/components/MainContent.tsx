import React from "react";
import { styled } from "styled-components";
import Column from "./Column.tsx";

export default function MainContent() {
  // cards context

  return (
    <StyledMainContent>
      <Column />
      <Column />
      <Column />
      <Column />
      <Column />
    </StyledMainContent>
  );
}

const StyledMainContent = styled.main`
  width: 100%;
  padding-top: 32px;
  display: flex;
  flex-grow: 1;
  gap: 24px;
  overflow-x: scroll;

  &::-webkit-scrollbar {
    display: none;
  }
`;
