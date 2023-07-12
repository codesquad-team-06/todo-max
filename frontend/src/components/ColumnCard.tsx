import React from "react";
import { styled } from "styled-components";
import IconButton from "./common/IconButton.tsx";
import deleteButtonIcon from "../assets/closed.svg";
import editButtonIcon from "../assets/edit.svg";

export default function ColumnCard() {
  return (
    <StyledColumnCard>
      <div className="card-info-container">
        <h3>Card title</h3>
        <p className="card-content">Card content</p>
        <p className="card-author">author by web</p>
      </div>

      <div className="buttons-container">
        <IconButton
          className="delete-button"
          src={deleteButtonIcon}
          alt="카드 삭제"
        />
        <IconButton
          className="edit-button"
          src={editButtonIcon}
          alt="카드 수정"
        />
      </div>
    </StyledColumnCard>
  );
}

const StyledColumnCard = styled.li`
  width: 100%;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  gap: 16px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};

  .card-info-container {
    flex-grow: 1;

    h3 {
      margin-bottom: 8px;
      font: ${({ theme: { font } }) => font.displayBold14};
      color: ${({ theme: { colors } }) => colors.grey900};
    }

    .card-content {
      margin-bottom: 16px;
      font: ${({ theme: { font } }) => font.displayMD14};
      color: ${({ theme: { colors } }) => colors.grey600};
    }

    .card-author {
      font: ${({ theme: { font } }) => font.displayMD12};
      color: ${({ theme: { colors } }) => colors.grey500};
    }
  }

  .buttons-container {
    display: flex;
    flex-direction: column;
  }
`;
