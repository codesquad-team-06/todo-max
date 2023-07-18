import React, { useState, useRef, MouseEvent } from "react";
import { styled } from "styled-components";
import CardDisplay from "./CardDisplay.tsx";
import CardMode from "./CardMode.tsx";
import { CardType } from "../../types.ts";

export default function Card({
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
  editCardHandler: (card: CardType) => void;
  deleteCardHandler: (deletedCardInfo: {
    id: number;
    columnId: number;
  }) => void;
  isDraggingCardIdHandler: (cardId: number | null) => void;
}) {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const cardRef = useRef<HTMLLIElement | null>(null);

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
    <StyledCard
      ref={cardRef}
      $currentCardRef={cardRef}
      $isGrabbed={isDraggingCardId === cardDetails.id}
      $currMouseCoords={currMouseCoords}
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
  $currentCardRef: React.RefObject<HTMLLIElement | null>;
  $isGrabbed: boolean;
  $currMouseCoords: [number, number];
};

const StyledCard = styled.li.attrs<StyledCardProps>(
  (props): React.HTMLAttributes<HTMLLIElement> => {
    const { $currentCardRef, $isGrabbed, $currMouseCoords } = props;

    const cardHeight =
      $currentCardRef.current?.getBoundingClientRect().height ?? 0;
    const cardWidth =
      $currentCardRef.current?.getBoundingClientRect().width ?? 0;

    return {
      style: {
        position: $isGrabbed ? "absolute" : "static",
        top: `${$currMouseCoords[1] - cardHeight / 2}px`,
        left: `${$currMouseCoords[0] - cardWidth / 2}px`,
        cursor: $isGrabbed ? "grabbing" : "grab",
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
