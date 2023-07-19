import React, { useContext, FormEvent, MouseEvent } from "react";
import ReactDOM from "react-dom";
import { styled } from "styled-components";
import ActionButton from "./common/ActionButton.tsx";
import { ModalContext } from "../context/ModalContext.tsx";

export default function Modal() {
  const { isModalActive, modalContent, closeModal, submitCallback } =
    useContext(ModalContext);

  return ReactDOM.createPortal(
    isModalActive && (
      <Dim
        onClick={(evt: MouseEvent) => {
          if (evt.target === evt.currentTarget) {
            closeModal();
          }
        }}>
        <ContentBox>
          <form
            onSubmit={(evt: FormEvent) => {
              submitCallback(evt);
              closeModal();
            }}>
            <h3>{modalContent}</h3>
            <ButtonWrapper>
              <ActionButton
                className="cancel-button"
                content="취소"
                type="button"
                onClick={closeModal}
              />
              <ActionButton
                className="delete-button"
                content="삭제"
                type="submit"
              />
            </ButtonWrapper>
          </form>
        </ContentBox>
      </Dim>
    ),
    document.querySelector("#modal-portal")!
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

  width: 320px;
  height: 126px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  font: ${({ theme: { font } }) => font.displayMD16};
  color: ${({ theme: { colors } }) => colors.grey600};

  form {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 8px;
`;
