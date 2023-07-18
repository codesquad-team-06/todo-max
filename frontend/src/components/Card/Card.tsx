import React, { useState, useRef, MouseEvent } from "react";
import { styled } from "styled-components";
import CardDisplay from "./CardDisplay.tsx";
import CardMode from "./CardMode.tsx";
import { CardType } from "../../types.ts";

export default function Card({
  cardDetails,
  currMouseCoords,
  dragCardId,
  editCardHandler,
  deleteCardHandler,
  updateMouseCoordsHandler,
  dragCardIdHandler,
}: {
  cardDetails: {
    id: number;
    title: string;
    content: string;
  };
  currMouseCoords: [number, number];
  dragCardId: number | null;
  editCardHandler: (card: CardType) => void;
  deleteCardHandler: (deletedCardInfo: {
    id: number;
    columnId: number;
  }) => void;
  updateMouseCoordsHandler: (x: number, y: number) => void;
  dragCardIdHandler: (cardId: number | null) => void;
}) {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const cardRef = useRef<HTMLLIElement | null>(null);

  const toggleEditMode = () => {
    setIsEditMode(!isEditMode);
  };

  const mouseDownHandler = (evt: MouseEvent) => {
    // Edge Cases
    if (isEditMode === true || (evt.target as HTMLElement).closest("button"))
      return;

    // 초기 `currMouseCoords = [0,0]` 깜빡임 방지
    updateMouseCoordsHandler(evt.clientX, evt.clientY);

    // Drag 시작 (그랩된 카드는 마우스 따라서 드래그 중)
    dragCardIdHandler(cardDetails.id);

    // 잔상 생성
  };

  const mouseUpHandler = (evt: MouseEvent) => {
    if (isEditMode === true || (evt.target as HTMLElement).closest("button"))
      return;

    dragCardIdHandler(cardDetails.id);
  };

  return (
    <StyledCard
      ref={cardRef}
      $isEditMode={isEditMode}
      $currCardRef={cardRef}
      $currMouseCoords={currMouseCoords}
      $isGrabbed={dragCardId === cardDetails.id}
      onMouseDown={mouseDownHandler}
      onMouseUp={mouseUpHandler}>
      {isEditMode ? (
        <CardMode
          {...{
            mode: "edit",
            cardDetails,
            toggleEditMode,
            editCardHandler,
          }}
        />
      ) : (
        <CardDisplay
          {...{
            cardDetails,
            toggleEditMode,
            deleteCardHandler,
          }}
        />
      )}
    </StyledCard>
  );
}

type StyledCardProps = {
  $isEditMode: boolean;
  $currCardRef: React.RefObject<HTMLLIElement | null>;
  $currMouseCoords: [number, number];
  $isGrabbed: boolean;
};

const StyledCard = styled.li.attrs<StyledCardProps>(
  ({
    $isEditMode,
    $currCardRef,
    $currMouseCoords,
    $isGrabbed,
  }): React.HTMLAttributes<HTMLLIElement> => {
    const cardHeight =
      $currCardRef.current?.getBoundingClientRect().height ?? 0;
    const cardWidth = $currCardRef.current?.getBoundingClientRect().width ?? 0;

    return {
      style: {
        position: $isGrabbed ? "absolute" : "static",
        top: `${$currMouseCoords[1] - cardHeight / 2}px`,
        left: `${$currMouseCoords[0] - cardWidth / 2}px`,
        cursor: $isEditMode ? "default" : $isGrabbed ? "grabbing" : "grab",
      },
    };
  }
)`
  width: inherit;
  min-height: 104px;
  padding: 16px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};
  user-select: none;
`;
