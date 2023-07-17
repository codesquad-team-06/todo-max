import { useState } from "react";

export default function useModal() {
  const [isModalActive, setIsModalActive] = useState(false);
  const [modalContent, setModalContent] = useState("I'm the Modal Content");

  const openModal = (content?: string) => {
    setIsModalActive(true);

    if (content) {
      setModalContent(content);
    }
  };

  const closeModal = () => {
    setIsModalActive(false);
  };

  return { isModalActive, modalContent, openModal, closeModal };
}
