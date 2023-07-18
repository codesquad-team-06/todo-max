/* eslint-disable no-alert */
import React, { useState, useEffect, MouseEvent } from "react";
import { styled } from "styled-components";
import Column from "./Column.tsx";
import { Card } from "../types.ts";

type ColumnData = {
  columnId: number;
  name: string;
  cards: Card[];
};

export default function Board() {
  const [board, setBoard] = useState<ColumnData[]>([]);
  const [isDraggingCardId, setIsDraggingCardId] = useState<number | null>(null);
  const [currMouseCoords, setCurrMouseCoords] = useState<[number, number]>([
    200, 200,
  ]);

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

  const addNewCardHandler = (newCard: Card) => {
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

  const editCardHandler = (updatedCard: Card) => {
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

  const mouseMoveHandler = (evt: MouseEvent) => {
    if (isDraggingCardId) {
      setCurrMouseCoords([evt.clientX, evt.clientY]);

      // 잔상 위치 실시간으로 결정.

      // 마우스 위치 및 위치에 있는 카드에 따라 잔상 위치 옮기기?
    }
  };

  const isDraggingCardIdHandler = (cardId: number | null) => {
    setIsDraggingCardId(cardId);
  };

  return (
    <StyledBoard onMouseMove={mouseMoveHandler}>
      {board.map(
        ({
          columnId,
          name,
          cards,
        }: {
          columnId: number;
          name: string;
          cards: Card[];
        }) => (
          <Column
            {...{
              key: columnId,
              name,
              cards,
              isDraggingCardId,
              currMouseCoords,
              addNewCardHandler,
              editCardHandler,
              deleteCardHandler,
              isDraggingCardIdHandler,
            }}
          />
        )
      )}
    </StyledBoard>
  );
}

const StyledBoard = styled.main`
  width: 100%;
  padding-top: 32px;
  display: flex;
  flex-grow: 1;
  gap: 24px;
  overflow-x: scroll;

  &::-webkit-scrollbar {
    display: none;
  }
`;
