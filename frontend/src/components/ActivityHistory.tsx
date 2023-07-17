/* eslint-disable no-alert */
import React, { useEffect, useState, useContext, FormEvent } from "react";
import { styled, keyframes, css } from "styled-components";
import ActivityHistoryItem from "./ActivityHistoryItem.tsx";
import closeButtonIcon from "../assets/closed.svg";
import { ModalContext } from "../context/ModalContext.tsx";

type History = {
  id: number;
  cardTitle: string;
  prevColumn: string;
  nextColumn: string;
  timestamp: string;
  actionName: string;
};

export default function ActivityHistory({
  isHistoryActive,
  toggleHistory,
}: {
  isHistoryActive: boolean;
  toggleHistory: () => void;
}) {
  const { openModal, closeModal } = useContext(ModalContext);
  const [history, setHistory] = useState<History[]>([]);

  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const response = await fetch("/histories");
        const historyData = await response.json();

        if (response.status === 200) {
          setHistory(historyData);
          return;
        }

        throw Error("데이터를 불러올 수 없습니다.");
      } catch (error) {
        alert(error);
      }
    };

    fetchHistory();
  }, []);

  const deleteHistoryRequest = async () => {
    const response = await fetch("/histories", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        historyId: [1, 2, 3, 4, 5],
      }),
    });

    const data = await response.json();

    if (data.success) {
      return [];
    }

    const {
      errorCode: { status, code, message },
    } = data;
    throw Error(`${status} ${code}: ${message}`);
  };

  const handleSubmit = async (evt: FormEvent) => {
    evt.preventDefault();
    try {
      const data = await deleteHistoryRequest();
      setHistory(data);
    } catch (error) {
      alert(error);
    }
  };

  return (
    <Layer isHistoryActive={isHistoryActive}>
      <TitleContainer>
        <h3>사용자 활동 기록</h3>
        <CloseBtn onClick={toggleHistory}>
          <img src={closeButtonIcon} alt="닫기 버튼" />
          <span>닫기</span>
        </CloseBtn>
      </TitleContainer>
      <ListContainer>
        {history.length === 0 && (
          <EmptyHistoryItem>사용자 활동 기록이 없습니다.</EmptyHistoryItem>
        )}
        {history.length !== 0 &&
          history.map((historyItem) => (
            <ActivityHistoryItem
              {...{
                key: historyItem.id,
                historyItem,
              }}
            />
          ))}
      </ListContainer>
      {history.length !== 0 && (
        <ButtonContainer>
          <button
            type="submit"
            onClick={() =>
              openModal("모든 사용자 활동 기록을 삭제할까요?", handleSubmit)
            }>
            기록 전체 삭제
          </button>
        </ButtonContainer>
      )}
    </Layer>
  );
}

const slideIn = keyframes`
  0% {
    top: -500px;
    opacity: 0;
  }
  100% {
    top: 60px;
    opacity: 1;
  }
`;

const slideOut = keyframes`
  0% {
    top: 60px;
    opacity: 1;
  }
  100% {
    top: -500px;
    opacity: 0;
  }
`;

const Layer = styled.div<{ isHistoryActive: boolean }>`
  width: 366px;
  padding: 8px;
  position: absolute;
  top: ${({ isHistoryActive }) => (isHistoryActive ? "60px" : "-500px")};
  right: 50px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.m};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.floating};
  opacity: ${({ isHistoryActive }) => (isHistoryActive ? 1 : 0)};
  animation: ${({ isHistoryActive }) =>
    isHistoryActive
      ? css`
          ${slideIn} 0.5s ease-in
        `
      : css`
          ${slideOut} 0.5s ease-out
        `};
  visibility: ${({ isHistoryActive }) =>
    isHistoryActive ? "visible" : "hidden"};
  transition: visibility 1s linear;
`;

const TitleContainer = styled.div`
  width: 350px;
  padding: 8px 8px 8px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
  h3 {
    font: ${({ theme: { font } }) => font.displayBold16};
  }
`;

const CloseBtn = styled.button`
  width: 65px;
  height: 32px;
  padding: 8px;
  display: flex;
  align-items: center;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border: none;
  font: ${({ theme: { font } }) => font.displayBold14};
  color: ${({ theme: { colors } }) => colors.grey600};
  cursor: pointer;
`;

const ListContainer = styled.ul`
  max-height: 554px;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
`;

const ButtonContainer = styled.div`
  width: 350px;
  padding: 4px 8px;
  display: flex;
  justify-content: flex-end;

  button {
    padding: 8px;
    background: none;
    border: none;
    font: ${({ theme: { font } }) => font.displayBold14};
    color: ${({ theme: { colors } }) => colors.red};
    cursor: pointer;
  }
`;

const EmptyHistoryItem = styled.li`
  width: 100%;
  padding: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  font: ${({ theme: { font } }) => font.displayMD14};
  color: ${({ theme: { colors } }) => colors.grey500};
`;
