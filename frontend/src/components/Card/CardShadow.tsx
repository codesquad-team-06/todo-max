import React, { MouseEvent } from "react";
import { styled } from "styled-components";
import IconButton from "../common/IconButton.tsx";
import deleteButtonIcon from "../../assets/closed.svg";
import editButtonIcon from "../../assets/edit.svg";

export default function CardShadow({
  title,
  content,
  onMouseUp,
}: {
  title: string;
  content: string;
  onMouseUp: (evt: MouseEvent) => void;
}) {
  return (
    <StyledCard className="card-shadow" onMouseUp={onMouseUp}>
      <div className="card-info-container">
        <h3 className="card-title">{title}</h3>
        <p className="card-content">{content}</p>
        <p className="card-author">author by web</p>
      </div>

      <div className="buttons-container">
        <IconButton
          className="delete-button"
          src={deleteButtonIcon}
          alt="카드 삭제"
          disabled
        />
        <IconButton
          className="edit-button"
          src={editButtonIcon}
          alt="카드 수정"
          disabled
        />
      </div>
    </StyledCard>
  );
}

const StyledCard = styled.li`
  width: inherit;
  min-height: 104px;
  padding: 16px;
  display: flex;
  gap: 16px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};
  opacity: ${({ theme: { opacity } }) => opacity.disabled};
  user-select: none;

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

    button {
      cursor: grabbing;
    }
  }
`;
