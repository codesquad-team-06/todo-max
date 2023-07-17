/* eslint-disable no-alert */
import React, { useState, useEffect } from "react";
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

  const addNewCardHandler = ({
    id,
    title,
    content,
    position,
    columnId,
  }: Card) => {
    setBoard((prevBoard: ColumnData[]) => {
      const newBoard: ColumnData[] = [...prevBoard];
      const newCard = {
        id,
        title,
        content,
        position,
        columnId,
      };
      const columnIndex = columnId - 1;

      const updatedCardList = [newCard, ...newBoard[columnIndex].cards];
      newBoard[columnIndex].cards = updatedCardList;

      return newBoard;
    });
  };

  return (
    <StyledBoard>
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
          <Column {...{ key: columnId, name, cards, addNewCardHandler }} />
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
