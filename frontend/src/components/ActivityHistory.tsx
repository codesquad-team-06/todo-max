import React from "react";
import { styled } from "styled-components";
import ActivityHistoryItem from "./ActivityHistoryItem.tsx";
import closeButtonIcon from "../assets/closed.svg";

export default function ActivityHistory() {
  return (
    <Layer>
      <TitleArea>
        <h3>사용자 활동 기록</h3>
        <CloseBtn>
          <img src={closeButtonIcon} alt="닫기 버튼" />
          <span>닫기</span>
        </CloseBtn>
      </TitleArea>
      <ListArea>
        <ActivityHistoryItem />
        <ActivityHistoryItem />
        <ActivityHistoryItem />
        <ActivityHistoryItem />
        <ActivityHistoryItem />
      </ListArea>
      <ButtonArea>
        <button type="submit">기록 전체 삭제</button>
      </ButtonArea>
    </Layer>
  );
}

const Layer = styled.div`
  position: absolute;
  top: 64px;
  right: 60px;

  width: 366px;
  padding: 8px;

  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.m};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.floating};
`;

const TitleArea = styled.div`
  width: 350px;
  padding: 8px 8px 8px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
  h3 {
    font: ${({ theme: { font } }) => font.displayBold16};
  }
`;

const CloseBtn = styled.button`
  width: 65px;
  height: 32px;
  padding: 8px;
  display: flex;
  align-items: center;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border: none;
  font: ${({ theme: { font } }) => font.displayBold14};
  color: ${({ theme: { colors } }) => colors.grey600};
  cursor: pointer;
`;

const ListArea = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ButtonArea = styled.div`
  width: 350px;
  padding: 4px 8px;
  display: flex;
  justify-content: flex-end;

  button {
    padding: 8px;
    background: none;
    border: none;
    font: ${({ theme: { font } }) => font.displayBold14};
    color: ${({ theme: { colors } }) => colors.red};
    cursor: pointer;
  }
`;
