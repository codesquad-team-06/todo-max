import React, { useState, FormEvent, MouseEvent } from "react";
import { styled } from "styled-components";
import ActionButton from "../common/ActionButton.tsx";

const ModeKR = {
  add: "등록",
  edit: "저장",
};

export default function ColumnCardMode({
  mode,
  newCardToggleHandler,
}: {
  mode: "add" | "edit";
  newCardToggleHandler: (evt: MouseEvent) => void;
}) {
  const [cardTitle, setCardTitle] = useState("");
  const [cardContent, setCardContent] = useState("");
  // TODO: `.card-title` input state
  // TODO: `.card-content` input state

  const submitHandler = (evt: FormEvent) => {
    evt.preventDefault();
  };

  const calcHeight = (value) => {
    const numberOfLineBreaks = (value.match(/\n/g) || []).length;
    // min-height + lines x line-height + padding + border
    const newHeight = 14 + numberOfLineBreaks * 20 + 12 + 2;
    return newHeight;
  };

  return (
    <StyledColumnCardMode onSubmit={submitHandler}>
      <div className="card-info-container">
        <input
          className="card-title"
          type="text"
          placeholder="제목을 입력하세요"
          onChange={(evt) => {
            setCardTitle(evt.target.value);
          }}
        />
        <textarea
          className="card-content"
          placeholder="내용을 입력하세요"
          maxLength={500}
          onChange={(evt) => {
            setCardContent(evt.target.value);
          }}
          onKeyUp={(evt) => {
            // eslint-disable-next-line no-param-reassign
            const target = evt.target as HTMLTextAreaElement;
            target.style.height = `${calcHeight(target.value)}px`;
          }}
        />
      </div>

      <div className="buttons-container">
        <ActionButton
          className="cancel-button"
          content="취소"
          type="button"
          onClick={(evt) => {
            newCardToggleHandler(evt);
          }}
        />
        <ActionButton
          className={`${mode}-button`}
          content={ModeKR[mode]}
          type="submit"
          disabled={!cardTitle || !cardContent} // TODO: if `.card-title` or `.card-content` is empty, `disabled`.
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
