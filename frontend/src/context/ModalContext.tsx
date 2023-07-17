import React, { useMemo, createContext } from "react";
import Modal from "../components/Modal.tsx";
import useModal from "../hooks/useModal.tsx";

type ModalContextType = {
  isModalActive: boolean;
  modalContent: string;
  openModal: (content?: string) => void;
  closeModal: () => void;
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
});

export function ModalProvider({ children }: { children: React.ReactElement }) {
  const { isModalActive, modalContent, openModal, closeModal } = useModal();

  const contextValue = useMemo(
    () => ({ isModalActive, modalContent, openModal, closeModal }),
    [isModalActive, modalContent, openModal, closeModal]
  );

  return (
    <ModalContext.Provider value={contextValue}>
      <Modal />
      {children}
    </ModalContext.Provider>
  );
}
