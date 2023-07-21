import React from "react";
import { styled } from "styled-components";
import defaultUserImg from "../../assets/default.jpeg";

export default function ActivityHistoryItem({
  historyItem: { cardTitle, prevColumn, nextColumn, elapsedTime, actionName },
}: {
  historyItem: {
    cardTitle: string;
    prevColumn: string;
    nextColumn: string;
    elapsedTime: string;
    actionName: string;
  };
}) {
  const setHistoryItemTemplate = (
    title: string,
    column1: string,
    column2: string,
    action: string
  ) => {
    switch (actionName) {
      case "등록":
        return (
          <>
            <strong className="history-keyword">{title}</strong>을(를){" "}
            <strong className="history-keyword">{column1}</strong>에서{" "}
            <strong className="history-keyword">{action}</strong>
            하였습니다.
          </>
        );

      case "수정":
        return (
          <>
            <strong className="history-keyword">{title}</strong>을(를){" "}
            <strong className="history-keyword">{action}</strong>
            하였습니다.
          </>
        );
      case "이동":
        return (
          <>
            <strong className="history-keyword">{title}</strong>을(를){" "}
            <strong className="history-keyword">{column1}</strong>에서{" "}
            <strong className="history-keyword">{column2}</strong>으로{" "}
            <strong className="history-keyword">{action}</strong>
            하였습니다.
          </>
        );
      case "삭제":
        return (
          <>
            <strong className="history-keyword">{title}</strong>을(를){" "}
            <strong className="history-keyword">{column1}</strong>에서{" "}
            <strong className="history-keyword">{action}</strong>
            하였습니다.
          </>
        );
      default:
        return "해당하는 액션이 존재하지 않습니다.";
    }
  };

  return (
    <StyledItem>
      <img src={defaultUserImg} alt="사용자 이미지" />
      <div className="history-content-container">
        <span>@anonymous</span>
        <p>{setHistoryItemTemplate(cardTitle, prevColumn, nextColumn, actionName)}</p>
        <span className="elapsedTime">{elapsedTime}</span>
      </div>
    </StyledItem>
  );
}

const StyledItem = styled.li`
  width: 100%;
  padding: 16px;
  display: flex;
  gap: 16px;
  border-bottom: 1px solid ${({ theme: { colors } }) => colors.grey200};
  font: ${({ theme: { font } }) => font.displayMD14};
  color: ${({ theme: { colors } }) => colors.grey600};

  &:last-child {
    border-bottom: none;
  }

  img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .history-content-container {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  .history-keyword {
    color: ${({ theme: { colors } }) => colors.grey700};
  }

  .elapsedTime {
    font: ${({ theme: { font } }) => font.displayMD12};
    color: ${({ theme: { colors } }) => colors.grey500};
  }
`;
