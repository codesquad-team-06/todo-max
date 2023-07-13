import React from "react";
import { styled } from "styled-components";
import ActionButton from "./common/ActionButton.tsx";

export default function Modal({ target }: { target: string }) {
  let modalTitle = "";

  if (target === "history") {
    modalTitle = "모든 사용자 활동기록을 삭제할까요?";
  } else if (target === "card") {
    modalTitle = "선택한 카드를 삭제할까요?";
  }

  return (
    <Dim>
      <ContentBox>
        <h3>{modalTitle}</h3>
        <ButtonWrapper>
          <ActionButton className="cancel-button" content="취소" />
          <ActionButton className="delete-button" content="삭제" />
        </ButtonWrapper>
      </ContentBox>
    </Dim>
  );
}

const Dim = styled.div`
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(20, 33, 43, 0.3);
`;

const ContentBox = styled.div`
  position: absolute;
  z-index: 10;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;

  width: 320px;
  height: 126px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  font: ${({ theme: { font } }) => font.displayMD16};
  color: ${({ theme: { colors } }) => colors.grey600};
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 8px;
`;
