/* eslint-disable no-alert */
import React, { useEffect, useState, useContext, FormEvent } from "react";
import { styled } from "styled-components";
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

export default function ActivityHistory() {
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
    <Layer>
      <TitleContainer>
        <h3>사용자 활동 기록</h3>
        <CloseBtn onClick={closeModal}>
          <img src={closeButtonIcon} alt="닫기 버튼" />
          <span>닫기</span>
        </CloseBtn>
      </TitleContainer>
      <ListContainer>
        {history.map(
          ({
            id,
            cardTitle,
            prevColumn,
            nextColumn,
            timestamp,
            actionName,
          }) => (
            <ActivityHistoryItem
              {...{
                key: id,
                cardTitle,
                prevColumn,
                nextColumn,
                timestamp,
                actionName,
              }}
            />
          )
        )}
      </ListContainer>
      <ButtonContainer>
        <button
          type="submit"
          onClick={() =>
            openModal("모든 사용자 활동 기록을 삭제할까요?", handleSubmit)
          }>
          기록 전체 삭제
        </button>
      </ButtonContainer>
    </Layer>
  );
}

const Layer = styled.div`
  position: absolute;
  top: 64px;
  right: 60px;

  width: 366px;
  padding: 8px;

  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.m};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.floating};
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
  display: flex;
  flex-direction: column;
  align-items: center;
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
