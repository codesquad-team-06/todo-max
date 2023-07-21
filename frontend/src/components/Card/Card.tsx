/* eslint-disable no-alert */
import React, { useState, useRef, MouseEvent } from "react";
import { styled } from "styled-components";
import CardDisplay from "./CardDisplay.tsx";
import CardMode from "./CardMode.tsx";
import CardShadow from "./CardShadow.tsx";
import { CardType } from "../../types.ts";
import { API_URL } from "../../index.tsx";

export default function Card({
  shadowRef,
  cardDetails,
  currMouseCoords,
  dragCard,
  currBelowCardId,
  currCardShadowInsertPosition,
  editCardHandler,
  deleteCardHandler,
  moveCardHandler,
  updateMouseCoordsHandler,
  dragCardHandler,
  resetCardShadowHandler,
  updateCardShadowPosition,
}: {
  shadowRef: React.RefObject<HTMLLIElement | null>;
  cardDetails: {
    id: number;
    title: string;
    content: string;
    columnId: number;
  };
  currMouseCoords: [number, number];
  dragCard: {
    cardRef: React.RefObject<HTMLLIElement>;
    cardDetails: {
      id: number;
      title: string;
      content: string;
      columnId: number;
    };
  } | null;
  currBelowCardId: number | null;
  currCardShadowInsertPosition: "before" | "after" | null;
  editCardHandler: (card: CardType) => void;
  deleteCardHandler: (deletedCardInfo: {
    id: number;
    columnId: number;
  }) => void;
  moveCardHandler: (updatedCard: CardType, prevColumnId: number) => void;
  updateMouseCoordsHandler: (x: number, y: number) => void;
  dragCardHandler: (
    dragCard: {
      cardRef: React.RefObject<HTMLLIElement>;
      cardDetails: {
        id: number;
        title: string;
        content: string;
        columnId: number;
      };
    } | null
  ) => void;
  resetCardShadowHandler: () => void;
  updateCardShadowPosition: (x: number, y: number) => void;
}) {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);
  const cardRef = useRef<HTMLLIElement | null>(null);

  const toggleEditMode = () => {
    setIsEditMode(!isEditMode);
  };

  const mouseDownHandler = (evt: MouseEvent) => {
    // Edge Cases
    if (isEditMode === true || (evt.target as HTMLElement).closest("button")) {
      return;
    }

    // 초기 `currMouseCoords = [0,0]` 깜빡임 방지
    updateMouseCoordsHandler(evt.clientX, evt.clientY);

    // 잔상
    updateCardShadowPosition(evt.clientX, evt.clientY);

    // Drag 시작 (그랩된 카드는 마우스 따라서 드래그 중)
    dragCardHandler({
      cardRef,
      cardDetails: {
        id: cardDetails.id,
        title: cardDetails.title,
        content: cardDetails.content,
        columnId: cardDetails.columnId,
      },
    });
  };

  const determineSiblingCardId = (
    isSameColumnMove: boolean,
    currCardShadowInsertPosition: "after" | "before" | null,
    shadowRef: React.RefObject<HTMLLIElement | null>
  ) => {
    // 같은 column으로 이동
    if (isSameColumnMove) {
      if (currCardShadowInsertPosition === "before") {
        const beforeCardId =
          (shadowRef?.current?.previousElementSibling as HTMLElement)?.dataset
            .id ?? 0;
        const afterCardId =
          (
            shadowRef.current?.nextElementSibling
              ?.nextElementSibling as HTMLElement
          )?.dataset.id ?? 0;

        return [Number(beforeCardId), Number(afterCardId)];
      }

      if (currCardShadowInsertPosition === "after") {
        const beforeCardId =
          (
            shadowRef.current?.previousElementSibling
              ?.previousElementSibling as HTMLElement
          )?.dataset.id ?? 0;

        const afterCardId =
          (shadowRef.current?.nextElementSibling as HTMLElement)?.dataset.id ??
          0;

        return [Number(beforeCardId), Number(afterCardId)];
      }
    }

    // 다른 column으로 이동
    const beforeCardId =
      (shadowRef?.current?.previousElementSibling as HTMLElement)?.dataset.id ??
      0;
    const afterCardId =
      (shadowRef.current?.nextElementSibling as HTMLElement)?.dataset.id ?? 0;
    return [Number(beforeCardId), Number(afterCardId)];
  };

  const mouseUpHandler = async (evt: MouseEvent) => {
    if (isEditMode === true || (evt.target as HTMLElement).closest("button")) {
      return;
    }

    const prevColumnId = dragCard?.cardDetails.columnId ?? 0;
    const nextColumnId = Number(
      shadowRef.current?.parentElement?.dataset.columnId ?? 0
    );

    const isSameColumnMove = prevColumnId === nextColumnId;

    const [beforeCardId, afterCardId] = determineSiblingCardId(
      isSameColumnMove,
      currCardShadowInsertPosition,
      shadowRef
    );

    // `CardShadow` 처리
    dragCardHandler(null);
    resetCardShadowHandler();

    try {
      const updatedCard = await moveCardRequest(
        beforeCardId,
        afterCardId,
        prevColumnId,
        nextColumnId
      );

      moveCardHandler(updatedCard, prevColumnId);
    } catch (error) {
      alert(error);
    }
  };

  const moveCardRequest = async (
    beforeCardId: number,
    afterCardId: number,
    prevColumnId: number,
    nextColumnId: number
  ) => {
    const response = await fetch(
      `${API_URL}/cards/move/${dragCard?.cardDetails.id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: dragCard?.cardDetails.id,
          prevCardId: beforeCardId,
          nextCardId: afterCardId,
          prevColumnId,
          nextColumnId,
        }),
      }
    );

    const data = await response.json();

    if (data.success) {
      return data.card;
    }

    const {
      errorCode: { status, code, message },
    } = data;
    throw Error(`${status} ${code}: ${message}`);
  };

  return (
    <>
      {currBelowCardId === cardDetails.id &&
        currCardShadowInsertPosition === "before" && (
          <CardShadow
            ref={shadowRef}
            title={dragCard?.cardDetails.title ?? ""}
            content={dragCard?.cardDetails.content ?? ""}
            onMouseUp={mouseUpHandler}
          />
        )}
      <StyledCard
        data-id={cardDetails.id}
        ref={cardRef}
        $isEditMode={isEditMode}
        $currCardRef={cardRef}
        $currMouseCoords={currMouseCoords}
        $dragCardId={dragCard ? dragCard.cardDetails.id : null}
        $isGrabbed={dragCard?.cardDetails.id === cardDetails.id}
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
      {currBelowCardId === cardDetails.id &&
        currCardShadowInsertPosition === "after" && (
          <CardShadow
            ref={shadowRef}
            title={dragCard?.cardDetails.title ?? ""}
            content={dragCard?.cardDetails.content ?? ""}
            onMouseUp={mouseUpHandler}
          />
        )}
    </>
  );
}

type StyledCardProps = {
  $isEditMode: boolean;
  $dragCardId: number | null;
  $currCardRef: React.RefObject<HTMLLIElement | null>;
  $currMouseCoords: [number, number];
  $isGrabbed: boolean;
};

const StyledCard = styled.li.attrs<StyledCardProps>(
  ({
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
  cursor: ${({ $isEditMode, $dragCardId }) =>
    $isEditMode ? "default" : $dragCardId !== null ? "grabbing" : "grab"};
  user-select: none;
`;
