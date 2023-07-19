import React from "react";
import { styled } from "styled-components";

export default function ActivityHistoryItem({
  historyItem: { cardTitle, prevColumn, nextColumn, timestamp, actionName },
}: {
  historyItem: {
    cardTitle: string;
    prevColumn: string;
    nextColumn: string;
    timestamp: string;
    actionName: string;
  };
}) {
  return (
    <StyledItem>
      <img src="" alt="사용자 이미지" />
      <div className="history-content-container">
        <span>UserName</span>
        <p>
          <strong className="history-keyword">{cardTitle}</strong>을(를){" "}
          <strong className="history-keyword">{prevColumn}</strong>에서{" "}
          <strong className="history-keyword">{nextColumn}</strong>으로{" "}
          <strong className="history-keyword">{actionName}</strong>
          하였습니다.
        </p>
        <span className="timestamp">{timestamp}</span>
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

  .timestamp {
    font: ${({ theme: { font } }) => font.displayMD12};
    color: ${({ theme: { colors } }) => colors.grey500};
  }
`;
