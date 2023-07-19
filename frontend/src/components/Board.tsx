/* eslint-disable no-alert */
import React, { useState, useEffect, MouseEvent } from "react";
import { styled } from "styled-components";
import Column from "./Column.tsx";
import { API_URL } from "../index.tsx";
import { CardType } from "../types.ts";

type ColumnData = {
  columnId: number;
  name: string;
  cards: CardType[];
};

export default function Board() {
  const [board, setBoard] = useState<ColumnData[]>([]);
  const [dragCardId, setDragCardId] = useState<number | null>(null);
  const [currMouseCoords, setCurrMouseCoords] = useState<[number, number]>([
    0, 0,
  ]);

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
    if (dragCardId) {
      updateMouseCoordsHandler(evt.clientX, evt.clientY);

      // 잔상 위치 실시간으로 결정.
      // document.elementFromPoint()

      // 마우스 움직이면서 해당 위치에 있는 카드에 따라 잔상 위치 옮기기
    }
  };

  const dragCardIdHandler = (cardId: number | null) => {
    setDragCardId(cardId);
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
          cards: CardType[];
        }) => (
          <Column
            {...{
              key: columnId,
              name,
              cards,
              dragCardId,
              currMouseCoords,
              addNewCardHandler,
              editCardHandler,
              deleteCardHandler,
              updateMouseCoordsHandler,
              dragCardIdHandler,
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
