import React, { useState, ChangeEvent, FormEvent } from "react";
import { styled } from "styled-components";
import ActionButton from "../common/ActionButton.tsx";

const ModeKR = {
  add: "등록",
  edit: "저장",
};

export default function ColumnCardMode({
  mode,
  cardId,
  cardTitle,
  cardContent,
  toggleEditMode,
}: {
  mode: "add" | "edit";
  cardId: number;
  cardTitle: string;
  cardContent: string;
  toggleEditMode: () => void;
}) {
  const [newCardTitle, setNewCardTitle] = useState(cardTitle);
  const [newCardContent, setNewCardContent] = useState(cardContent);

  const handleNewCardTitleChange = (evt: ChangeEvent<HTMLInputElement>) => {
    setNewCardTitle(evt.target.value);
  };

  const handleNewCardContentChange = (
    evt: ChangeEvent<HTMLTextAreaElement>
  ) => {
    setNewCardContent(evt.target.value);
  };

  const handleSubmit = (evt: FormEvent) => {
    evt.preventDefault();

    // TODO (common operation for both "add" and "edit" modes)
    // If "add" mode, POST to /cards. (title, content, column_id)
    // If "edit" mode, PUT to /cards/{id}. (id, title, content)
    // If request was successfully handled, use the received card details in the response object to update the cards context.
  };

  return (
    <StyledColumnCardMode onSubmit={handleSubmit}>
      <div className="card-info-container">
        <input
          className="card-title"
          type="text"
          placeholder="제목을 입력하세요"
          value={newCardTitle}
          onChange={handleNewCardTitleChange}
        />
        <textarea
          className="card-content"
          placeholder="내용을 입력하세요"
          rows={1}
          value={newCardContent}
          onChange={handleNewCardContentChange}
        />
      </div>

      <div className="buttons-container">
        <ActionButton
          className="cancel-button"
          content="취소"
          type="button"
          onClick={toggleEditMode}
        />
        <ActionButton
          className={`${mode}-button`}
          content={ModeKR[mode]}
          type="submit"
          disabled={newCardTitle === "" || newCardContent === ""}
        />
      </div>
    </StyledColumnCardMode>
  );
}

const StyledColumnCardMode = styled.form`
  width: 100%;
  height: 100%;

  .card-info-container {
    flex-grow: 1;

    .card-title {
      width: 100%;
      height: 16px;
      margin-bottom: 8px;
      background: transparent;
      border: none;
      font: ${({ theme: { font } }) => font.displayBold14};
      color: ${({ theme: { colors } }) => colors.grey900};

      &::placeholder {
        font: ${({ theme: { font } }) => font.displayBold14};
        color: ${({ theme: { colors } }) => colors.grey900};
      }
    }

    .card-content {
      width: 100%;
      max-height: 200px;
      margin-bottom: 16px;
      background: transparent;
      border: none;
      font: ${({ theme: { font } }) => font.displayMD14};
      color: ${({ theme: { colors } }) => colors.grey600};
      resize: vertical;
      overflow: auto;
      cursor: text;

      &::placeholder {
        font: ${({ theme: { font } }) => font.displayMD14};
        color: ${({ theme: { colors } }) => colors.grey600};
      }
    }

    .card-author {
      font: ${({ theme: { font } }) => font.displayMD12};
      color: ${({ theme: { colors } }) => colors.grey500};
    }
  }

  .buttons-container {
    display: flex;
    gap: 8px;
  }
`;
