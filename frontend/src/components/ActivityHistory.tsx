import React from "react";
import { styled } from "styled-components";

export default function ActivityHistory() {
  return (
    <Layer>
      <TitleArea>
        <h3>사용자 활동 기록</h3>
        <CloseBtn>삭제</CloseBtn>
      </TitleArea>
    </Layer>
  );
}

const Layer = styled.div`
  display: none;
  position: absolute;
  top: 64px;
  right: 60px;

  width: 336px;
  height: 680px;
  padding: 8px;

  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.m};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.floating};
`;

const TitleArea = styled.div`
  padding: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
  h3 {
    font: ${({ theme: { font } }) => font.displayBold16};
  }
`;

const CloseBtn = styled.button`
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border: none;
  width: 65px;
  height: 32px;
  padding: 8px;
  font: ${({ theme: { font } }) => font.displayBold14};
`;
