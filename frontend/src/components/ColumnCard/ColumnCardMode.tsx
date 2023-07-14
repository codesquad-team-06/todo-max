/* eslint-disable no-alert */
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
  newCardToggleHandler,
}: {
  mode: "add" | "edit";
  cardId: number;
  cardTitle: string;
  cardContent: string;
  toggleEditMode: () => void;
  newCardToggleHandler: () => void;
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

  // TODO: "POST" to "/cards". Request payload: {title, content, column_id}
  // const createCardRequest = () => {};
  const addNewCardRequest = async () => {
    const response = await fetch("/cards", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: cardTitle,
        content: cardContent,
        column_id: 1,
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
  }

  const editCardRequest = async () => {
    const res = await fetch(`/cards/${cardId}`, {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        id: cardId,
        title: cardTitle,
        content: cardContent,
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
        const { id, title, content } = await addNewCardRequest();
        // TODO: Update cards context
      } else if (mode === "edit") {
        // edit card request
        // TODO: Update cards context
      }
      newCardToggleHandler();
    } catch (error) {
      alert(error);
    }

    newCardToggleHandler();
  }

  const calcHeight = (value: string) => {
    const numberOfLineBreaks = (value.match(/\n/g) || []).length;
    // min-height + lines x line-height + padding + border
    const newHeight = 14 + numberOfLineBreaks * 20 + 12 + 2;
    return newHeight;
  };

  return (
    <StyledColumnCardMode
      onSubmit={(evt: React.FormEvent<Element>) => {
        submitHandler(evt);
      }}>
      } else if (mode === "edit") {
        const { id, title, content } = await editCardRequest();

        // TODO: Update cards context
      }

      toggleEditMode();
    } catch (error) {
      alert(error);
    }
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
          maxLength={500}
          onKeyUp={(evt) => {
            // eslint-disable-next-line no-param-reassign
            const target = evt.target as HTMLTextAreaElement;
            target.style.height = `${calcHeight(target.value)}px`;
            target.style.maxHeight = "128px";
          }}
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
          onClick={mode === "add" ? newCardToggleHandler : toggleEditMode}
        />
        <ActionButton
          className={`${mode}-button`}
          content={ModeKR[mode]}
          type="submit"
          disabled={!cardTitle || !cardContent}
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
