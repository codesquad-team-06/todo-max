import React, { useMemo, createContext, FormEvent } from "react";
import Modal from "../components/Modal.tsx";
import useModal from "../hooks/useModal.tsx";

type ModalContextType = {
  isModalActive: boolean;
  modalContent: string;
  openModal: (content: string, submitHandler: (evt: FormEvent) => void) => void;
  closeModal: () => void;
  submitCallback: (evt: FormEvent) => void;
};

export const ModalContext = createContext<ModalContextType>({
  isModalActive: false,
  modalContent: "",
  openModal: () => {
    throw new Error("openModal function must be overridden");
  },
  closeModal: () => {
    throw new Error("closeModal function must be overridden");
  },
  submitCallback: (evt: FormEvent) => {
    throw new Error("submitCallback function must be overridden");
  },
});

export function ModalProvider({ children }: { children: React.ReactElement }) {
  const { isModalActive, modalContent, openModal, closeModal, submitCallback } =
    useModal();

  const contextValue = useMemo(
    () => ({
      isModalActive,
      modalContent,
      openModal,
      closeModal,
      submitCallback,
    }),
    [isModalActive, modalContent, openModal, closeModal, submitCallback]
  );

  return (
    <ModalContext.Provider value={contextValue}>
      <Modal />
      {children}
    </ModalContext.Provider>
  );
}
