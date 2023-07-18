import React, { useState, useRef, MouseEvent } from "react";
import { styled } from "styled-components";
import ColumnCardDisplay from "./ColumnCardDisplay.tsx";
import ColumnCardMode from "./ColumnCardMode.tsx";
import { Card } from "../../types.ts";

export default function ColumnCard({
  cardDetails,
  currMouseCoords,
  isDraggingCardId,
  editCardHandler,
  deleteCardHandler,
  isDraggingCardIdHandler,
}: {
  cardDetails: {
    id: number;
    title: string;
    content: string;
  };
  currMouseCoords: [number, number];
  isDraggingCardId: number | null;
  editCardHandler: (card: Card) => void;
  deleteCardHandler: (deletedCardInfo: {
    id: number;
    columnId: number;
  }) => void;
  isDraggingCardIdHandler: (cardId: number | null) => void;
}) {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const columnCardRef = useRef<HTMLLIElement | null>(null);

  const toggleEditMode = () => {
    setIsEditMode(!isEditMode);
  };

  const mouseDownHandler = (evt: MouseEvent) => {
    // Dragging 시작
    isDraggingCardIdHandler(cardDetails.id);
    // 그랩된 카드는 absolute로 마우스 따라서 드래그 중

    // 마우스 위치에 따라 잔상 위치 옮기기
  };

  const mouseUpHandler = (evt: MouseEvent) => {
    isDraggingCardIdHandler(cardDetails.id);
  };

  return (
    <StyledColumnCard
      ref={columnCardRef}
      $currentCardRef={columnCardRef}
      $isGrabbed={isDraggingCardId === cardDetails.id}
      $currMouseCoords={currMouseCoords}
      onMouseDown={mouseDownHandler}
      onMouseUp={mouseUpHandler}>
      {isEditMode ? (
        <ColumnCardMode
          {...{
            mode: "edit",
            cardDetails,
            toggleEditMode,
            editCardHandler,
          }}
        />
      ) : (
        <ColumnCardDisplay
          {...{
            cardDetails,
            toggleEditMode,
            deleteCardHandler,
          }}
        />
      )}
    </StyledColumnCard>
  );
}

const StyledColumnCard = styled.li<{
  $currentCardRef: React.RefObject<HTMLLIElement | null>;
  $isGrabbed: boolean;
  $currMouseCoords: [number, number];
}>`
  width: inherit;
  min-height: 104px;
  padding: 16px;
  position: ${({ $isGrabbed }) => ($isGrabbed ? "absolute" : "static")};
  top: ${({ $currMouseCoords, $currentCardRef }) => {
    const columnCardHeight =
      $currentCardRef.current?.getBoundingClientRect().height ?? 0;
    return `${$currMouseCoords[1] - columnCardHeight / 2}px;`;
  }};
  left: ${({ $currMouseCoords, $currentCardRef }) => {
    const columnCardWidth =
      $currentCardRef.current?.getBoundingClientRect().width ?? 0;
    return `${$currMouseCoords[0] - columnCardWidth / 2}px;`;
  }};
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};
  cursor: ${({ $isGrabbed }) => ($isGrabbed ? "grabbing" : "grab")};
  user-select: none;
`;
