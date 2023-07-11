import React from "react";
import { styled } from "styled-components";
import historyButtonIcon from "../assets/history.svg";

export default function Header() {
  return (
    <StyledHeader>
      <H1>Todo List</H1>

      <HistoryButton>
        <img src={historyButtonIcon} alt="사용자 활동 기록 조회" />
      </HistoryButton>
    </StyledHeader>
  );
}

const StyledHeader = styled.header`
  width: 100%;
  padding: 18px 80px;
  display: flex;
  justify-content: space-between;
`;

const H1 = styled.h1`
  color: ${({ theme: { colors } }) => colors.grey900};
  font: ${({ theme: { font } }) => font.displayBold24};
  text-transform: uppercase;
`;

const HistoryButton = styled.button`
  width: 24px;
  height: 24px;
  padding: 0;
  background: none;
  border: none;
  cursor: pointer;

  img {
    width: inherit;
    height: inherit;
  }
`;
