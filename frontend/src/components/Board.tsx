/* eslint-disable no-alert */
import React, { useState, useEffect, useRef, MouseEvent } from "react";
import { styled } from "styled-components";
import cloneDeep from "lodash.clonedeep";
import Column from "./Column.tsx";
import { CardType } from "../types.ts";
import { API_URL } from "../index.tsx";

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
    cardRef: React.RefObject<HTMLLIElement>;
    cardDetails: {
      id: number;
      title: string;
      content: string;
      columnId: number;
    };
  } | null>(null);
  const [currBelowCardId, setCurrBelowCardId] = useState<number | null>(null);
  const [currCardShadowInsertPosition, setCurrCardShadowInsertPosition] =
    useState<"before" | "after" | null>(null);
  const shadowRef = useRef<HTMLLIElement | null>(null);

  useEffect(() => {
    const fetchBoard = async () => {
      try {
        const response = await fetch(`${API_URL}/cards`);
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
      const staleCardIndex = newBoard[columnIndex].cards.findIndex(
        (card) => card.id === updatedCard.id
      );
      newBoard[columnIndex].cards[staleCardIndex] = updatedCard;
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

  const moveCardHandler = (updatedCard: CardType, prevColumnId: number) => {
    setBoard((prevBoard) => {
      const newBoard = cloneDeep(prevBoard);
      const prevColumnIndex = prevColumnId - 1;
      const updatedColumnIndex = updatedCard.columnId - 1;

      const removeCardFromPrevColumn = () => {
        newBoard[prevColumnIndex].cards = newBoard[
          prevColumnIndex
        ].cards.filter((card) => card.id !== updatedCard.id);
      };

      const insertCardAtPosition = (
        cards: CardType[],
        index: number,
        cardToInsert: CardType
      ) => {
        const updatedCards = [...cards];
        updatedCards.splice(index, 0, cardToInsert);
        return updatedCards;
      };

      // Remove card from previous column
      removeCardFromPrevColumn();

      // Determine the index where the card should be inserted
      let updatedCardIndex = newBoard[updatedColumnIndex].cards.findIndex(
        (card) => card.position < updatedCard.position
      );

      // Exception handling: When the card should be inserted at the end
      updatedCardIndex =
        updatedCardIndex !== -1
          ? updatedCardIndex
          : newBoard[updatedColumnIndex].cards.length;

      // Insert the card into the new column at the determined index
      newBoard[updatedColumnIndex].cards = insertCardAtPosition(
        newBoard[updatedColumnIndex].cards,
        updatedCardIndex,
        updatedCard
      );

      return newBoard;
    });
  };

  const mouseMoveHandler = (evt: MouseEvent) => {
    if (dragCard?.cardDetails && dragCard?.cardRef) {
      updateMouseCoordsHandler(evt.clientX, evt.clientY);

      // 현재 커서로 잡고 있는 카드의 포인터 이벤트 제거
      // getCardFromPoint()로 드래그 중인 카드가 아닌 밑에 있는 카드를 가져오기 위함
      dragCard.cardRef.current!.style.pointerEvents = "none";

      // 드래그 중 마우스 좌표 밑 카드 확인 (드래그 카드 말고 밑에 있는 카드)
      updateCardShadowPosition(evt.clientX, evt.clientY);

      dragCard.cardRef.current!.style.pointerEvents = "auto";
    }
  };

  // Get the element that is at the specified coordinates.
  const getCardFromPoint = (x: number, y: number): HTMLLIElement | null => {
    const elementBelow = document.elementFromPoint(x, y);
    const card = elementBelow?.closest("li");

    return card
      ? !card?.classList.contains("card-shadow")
        ? card
        : null
      : null;
  };

  const updateMouseCoordsHandler = (x: number, y: number) => {
    setCurrMouseCoords([x, y]);
  };

  const dragCardHandler = (
    dragCard: {
      cardRef: React.RefObject<HTMLLIElement>;
      cardDetails: {
        id: number;
        title: string;
        content: string;
        columnId: number;
      };
    } | null
  ) => {
    setDragCard(dragCard);
  };

  const resetCardShadowHandler = () => {
    setCurrBelowCardId(null);
    setCurrCardShadowInsertPosition(null);
  };

  const updateCardShadowPosition = (x: number, y: number) => {
    const belowCard = getCardFromPoint(x, y);
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
      y
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
  };

  return (
    <StyledBoard
      $dragCardId={dragCard ? dragCard.cardDetails.id : null}
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
              shadowRef,
              columnId,
              name,
              cards,
              dragCard,
              currBelowCardId,
              currCardShadowInsertPosition,
              currMouseCoords,
              addNewCardHandler,
              editCardHandler,
              deleteCardHandler,
              moveCardHandler,
              updateMouseCoordsHandler,
              dragCardHandler,
              resetCardShadowHandler,
              updateCardShadowPosition,
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
