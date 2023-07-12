import React from "react";
import { styled } from "styled-components";
import IconButton from "../common/IconButton.tsx";
import deleteButtonIcon from "../../assets/closed.svg";
import editButtonIcon from "../../assets/edit.svg";

export default function ColumnCardDisplay() {
  return (
    <StyledColumnCardDisplay>
      <div className="card-info-container">
        <h3 className="card-title">Card title</h3>
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
    </StyledColumnCardDisplay>
  );
}

const StyledColumnCardDisplay = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  gap: 16px;

  .card-info-container {
    flex-grow: 1;

    .card-title {
      margin-bottom: 8px;
      font: ${({ theme: { font } }) => font.displayBold14};
      color: ${({ theme: { colors } }) => colors.grey900};
      overflow-wrap: anywhere;
    }

    .card-content {
      margin-bottom: 16px;
      font: ${({ theme: { font } }) => font.displayMD14};
      color: ${({ theme: { colors } }) => colors.grey600};
      overflow-wrap: anywhere;
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
