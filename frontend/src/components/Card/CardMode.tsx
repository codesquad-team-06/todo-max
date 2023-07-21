/* eslint-disable no-alert */
import React, { useState, ChangeEvent, FormEvent } from "react";
import { styled } from "styled-components";
import ActionButton from "../common/ActionButton.tsx";
import { CardType } from "../../types.ts";
import { API_URL } from "../../index.tsx";

const ModeKR = {
  add: "등록",
  edit: "저장",
};

CardMode.defaultProps = {
  cardDetails: {},
  toggleEditMode: undefined,
  toggleNewCard: undefined,
  addNewCardHandler: undefined,
  editCardHandler: undefined,
};

export default function CardMode({
  mode,
  cardDetails,
  toggleEditMode,
  toggleNewCard,
  addNewCardHandler,
  editCardHandler,
}: {
  mode: "add" | "edit";
  cardDetails?: {
    id: number;
    title: string;
    content: string;
  };
  toggleEditMode?: () => void;
  toggleNewCard?: () => void;
  addNewCardHandler?: (card: CardType) => void;
  editCardHandler?: (card: CardType) => void;
}) {
  const [newCardTitle, setNewCardTitle] = useState(
    mode === "add" ? "" : cardDetails?.title
  );
  const [newCardContent, setNewCardContent] = useState(
    mode === "add" ? "" : cardDetails?.content
  );

  const handleNewCardTitleChange = (evt: ChangeEvent<HTMLInputElement>) => {
    setNewCardTitle(evt.target.value);
  };

  const handleNewCardContentChange = (
    evt: ChangeEvent<HTMLTextAreaElement>
  ) => {
    setNewCardContent(evt.target.value);
  };

  // TODO: "POST" to "/cards". Request payload: {title, content, columnId}
  const addNewCardRequest = async () => {
    const response = await fetch(`${API_URL}/cards`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: newCardTitle,
        content: newCardContent,
        columnId: 1,
      }),
    });

    const data = await response.json();

    if (data.success) {
      return data.card;
    }

    const {
      errorCode: { status, code, message },
    } = data;
    throw Error(`${status} ${code}: ${message}`);
  };

  const editCardRequest = async () => {
    const { id, title, content } = cardDetails!;

    const res = await fetch(`${API_URL}/cards/${id}`, {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        id,
        title: newCardTitle,
        content: newCardContent,
      }),
    });
    const data = await res.json();

    if (data.success) {
      return data.card;
    }

    const {
      errorCode: { status, code, message },
    } = data;
    throw Error(`${status} ${code}: ${message}`);
  };

  const handleSubmit = async (evt: FormEvent) => {
    evt.preventDefault();

    try {
      if (mode === "add") {
        const newCard = await addNewCardRequest();
        addNewCardHandler && addNewCardHandler(newCard);
        toggleNewCard && toggleNewCard();
      } else if (mode === "edit") {
        const updatedCard = await editCardRequest();
        editCardHandler && editCardHandler(updatedCard);
        toggleEditMode && toggleEditMode();
      }
    } catch (error) {
      alert(error);
    }
  };

  const calcHeight = (value: string) => {
    const numberOfLineBreaks = (value.match(/\n/g) || []).length;
    // min-height + lines x line-height + padding + border
    const newHeight = 14 + numberOfLineBreaks * 20 + 12 + 2;
    return newHeight;
  };

  return (
    <StyledCardMode onSubmit={handleSubmit}>
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
          maxLength={500}
          value={newCardContent}
          onKeyUp={(evt) => {
            // eslint-disable-next-line no-param-reassign
            const target = evt.target as HTMLTextAreaElement;
            target.style.height = `${calcHeight(target.value)}px`;
            target.style.maxHeight = "128px";
          }}
          onChange={handleNewCardContentChange}
        />
      </div>

      <div className="buttons-container">
        <ActionButton
          className="cancel-button"
          content="취소"
          type="button"
          onClick={mode === "add" ? toggleNewCard : toggleEditMode}
        />
        <ActionButton
          className={`${mode}-button`}
          content={ModeKR[mode]}
          type="submit"
          disabled={!newCardTitle || !newCardContent}
        />
      </div>
    </StyledCardMode>
  );
}

const StyledCardMode = styled.form`
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
      margin-bottom: 16px;
      background: transparent;
      border: none;
      font: ${({ theme: { font } }) => font.displayMD14};
      color: ${({ theme: { colors } }) => colors.grey600};
      resize: vertical;
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
