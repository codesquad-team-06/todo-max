/* eslint-disable no-alert */
import React, { FormEvent, useContext } from "react";
import { styled } from "styled-components";
import IconButton from "../common/IconButton.tsx";
import deleteButtonIcon from "../../assets/closed.svg";
import editButtonIcon from "../../assets/edit.svg";
import { ModalContext } from "../../context/ModalContext.tsx";

export default function ColumnCardDisplay({
  cardId,
  cardTitle,
  cardContent,
  toggleEditMode,
}: {
  cardId: number;
  cardTitle: string;
  cardContent: string;
  toggleEditMode: () => void;
}) {
  const { openModal } = useContext(ModalContext);

  const deleteCardRequest = async () => {
    const response = await fetch(`/cards/${cardId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });

    const data = await response.json();

    if (data.success) {
      return { cardId: data.cardId };
    }

    const {
      errorCode: { status, code, message },
    } = data;
    throw Error(`${status} ${code}: ${message}`);
  };

  const handleSubmit = async (evt: FormEvent) => {
    evt.preventDefault();

    try {
      const { cardId } = await deleteCardRequest();
      // TODO: Update cards context
    } catch (error) {
      alert(error);
    }
  };

  return (
    <StyledColumnCardDisplay>
      <div className="card-info-container">
        <h3 className="card-title">{cardTitle}</h3>
        <p className="card-content">{cardContent}</p>
        <p className="card-author">author by web</p>
      </div>

      <div className="buttons-container">
        <IconButton
          className="delete-button"
          src={deleteButtonIcon}
          alt="카드 삭제"
          onClick={() => openModal("선택한 카드를 삭제할까요?", handleSubmit)}
        />
        <IconButton
          className="edit-button"
          src={editButtonIcon}
          alt="카드 수정"
          onClick={toggleEditMode}
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
