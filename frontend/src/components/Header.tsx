import React, { useState } from "react";
import { styled } from "styled-components";
import ActivityHistory from "./ActivityHistory.tsx";
import IconButton from "./common/IconButton.tsx";
import historyButtonIcon from "../assets/history.svg";
import AnimationWrapper from "./AnimationWrapper.tsx";

export default function Header() {
  const [isHistoryActive, setIsHistoryActive] = useState(false);

  const toggleHistory = () => {
    setIsHistoryActive(!isHistoryActive);
  };

  return (
    <>
      <StyledHeader>
        <H1>Todo List</H1>
        <IconButton
          className="history-button"
          src={historyButtonIcon}
          alt="사용자 활동 기록 조회"
          onClick={toggleHistory}
        />
      </StyledHeader>
      <AnimationWrapper show={isHistoryActive}>
        <ActivityHistory {...{ isHistoryActive, toggleHistory }} />
      </AnimationWrapper>
    </>
  );
}

const StyledHeader = styled.header`
  width: 100%;
  padding: 18px 0;
  display: flex;
  justify-content: space-between;
`;

const H1 = styled.h1`
  color: ${({ theme: { colors } }) => colors.grey900};
  font: ${({ theme: { font } }) => font.displayBold24};
  text-transform: uppercase;
`;
