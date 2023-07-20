/* eslint-disable no-alert */
import React, { useState, useEffect, MouseEvent } from "react";
import { styled } from "styled-components";
import Column from "./Column.tsx";
import { CardType } from "../types.ts";

type ColumnData = {
  columnId: number;
  name: string;
  cards: CardType[];
};

export default function Board() {
  const [board, setBoard] = useState<ColumnData[]>([]);
  const [currMouseCoords, setCurrMouseCoords] = useState<[number, number]>([
    0, 0,
  ]);
  const [dragCard, setDragCard] = useState<{
    cardRef: React.RefObject<HTMLLIElement> | null;
    cardDetails: {
      id: number;
      title: string;
      content: string;
    } | null;
  }>({ cardRef: null, cardDetails: null });
  const [currBelowCardId, setCurrBelowCardId] = useState<number | null>(null);
  const [currCardShadowInsertPosition, setCurrCardShadowInsertPosition] =
    useState<"before" | "after" | null>(null);

  useEffect(() => {
    const fetchBoard = async () => {
      try {
        const response = await fetch("/cards");
        const boardData = await response.json();

        if (response.status === 200) {
          setBoard(boardData);
          return;
        }

        throw Error("데이터를 불러올 수 없습니다.");
      } catch (error) {
        alert(error);
      }
    };

    fetchBoard();
  }, []);

  const addNewCardHandler = (newCard: CardType) => {
    setBoard((prevBoard: ColumnData[]) => {
      const newBoard: ColumnData[] = prevBoard.map((column) => {
        if (column.columnId === newCard.columnId) {
          return {
            ...column,
            cards: [newCard, ...column.cards],
          };
        }
        return column;
      });
      return newBoard;
    });
  };

  const editCardHandler = (updatedCard: CardType) => {
    setBoard((prevBoard) => {
      const newBoard = [...prevBoard];
      const columnIndex = updatedCard.columnId - 1;
      const targetCardIndex = newBoard[columnIndex].cards.findIndex(
        (card) => card.id === updatedCard.id
      );
      newBoard[columnIndex].cards[targetCardIndex] = updatedCard;
      return newBoard;
    });
  };

  const deleteCardHandler = ({
    id,
    columnId,
  }: {
    id: number;
    columnId: number;
  }) => {
    setBoard((prevBoard) => {
      const newBoard = [...prevBoard];
      const columnIndex = columnId - 1;
      const updatedCards = newBoard[columnIndex].cards.filter(
        (card) => card.id !== id
      );
      newBoard[columnIndex].cards = updatedCards;
      return newBoard;
    });
  };

  const updateMouseCoordsHandler = (x: number, y: number) => {
    setCurrMouseCoords([x, y]);
  };

  const mouseMoveHandler = (evt: MouseEvent) => {
    if (dragCard.cardDetails && dragCard.cardRef) {
      updateMouseCoordsHandler(evt.clientX, evt.clientY);

      // 현재 커서로 잡고 있는 카드의 포인터 이벤트 제거
      // getCardFromPoint()로 드래그 중인 카드가 아닌 밑에 있는 카드를 가져오기 위함
      dragCard.cardRef.current!.style.pointerEvents = "none";

      // 드래그 중 마우스 좌표 밑 카드 확인 (드래그 카드 말고 밑에 있는 카드)
      const belowCard = getCardFromPoint(evt.clientX, evt.clientY);
      if (belowCard) {
        setCurrBelowCardId((prevBelowCardId) => {
          const newBelowCardId = Number(belowCard?.dataset.id);
          if (prevBelowCardId !== newBelowCardId) {
            return newBelowCardId;
          }
          return prevBelowCardId;
        });
      }

      // `belowCard`의 전/후 위치 판별
      const cardShadowInsertPosition =
        (belowCard?.getBoundingClientRect().y ?? 0) +
          (belowCard?.getBoundingClientRect().height ?? 0) / 2 >
        evt.clientY
          ? "before"
          : "after";

      if (belowCard) {
        setCurrCardShadowInsertPosition((prevInsertPosition) => {
          if (prevInsertPosition !== cardShadowInsertPosition) {
            return cardShadowInsertPosition;
          }
          return prevInsertPosition;
        });
      }

      dragCard.cardRef.current!.style.pointerEvents = "auto";
    }
  };

  // Get the element that is at the specified coordinates.
  function getCardFromPoint(x: number, y: number): HTMLLIElement | null {
    const elementBelow = document.elementFromPoint(x, y);
    const card = elementBelow?.closest("li");

    return card
      ? !card?.classList.contains("card-shadow")
        ? card
        : null
      : null;
  }

  const dragCardHandler = (dragCard: {
    cardRef: React.RefObject<HTMLLIElement> | null;
    cardDetails: {
      id: number;
      title: string;
      content: string;
    } | null;
  }) => {
    setDragCard(dragCard);
  };

  return (
    <StyledBoard
      $dragCardId={dragCard.cardDetails ? dragCard.cardDetails.id : null}
      onMouseMove={mouseMoveHandler}>
      {board.map(
        ({
          columnId,
          name,
          cards,
        }: {
          columnId: number;
          name: string;
          cards: CardType[];
        }) => (
          <Column
            {...{
              key: columnId,
              name,
              cards,
              dragCard,
              currBelowCardId,
              currCardShadowInsertPosition,
              currMouseCoords,
              addNewCardHandler,
              editCardHandler,
              deleteCardHandler,
              updateMouseCoordsHandler,
              dragCardHandler,
            }}
          />
        )
      )}
    </StyledBoard>
  );
}

const StyledBoard = styled.main<{ $dragCardId: number | null }>`
  width: 100%;
  padding-top: 32px;
  display: flex;
  flex-grow: 1;
  gap: 24px;
  overflow-x: scroll;
  cursor: ${({ $dragCardId }) =>
    $dragCardId !== null ? "grabbing" : "default"};
  &::-webkit-scrollbar {
    display: none;
  }
`;
