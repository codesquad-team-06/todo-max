import { useState, FormEvent } from "react";

export default function useModal() {
  const [isModalActive, setIsModalActive] = useState(false);
  const [modalContent, setModalContent] = useState("I'm the Modal Content");
  // 모달의 submithandler callback
  const [submitCallback, setSubmitCallback] = useState<
    (evt: FormEvent) => void
  >(() => () => {
    throw Error("needs to be overriden");
  });

  const openModal = (
    content: string,
    submitHandler: (evt: FormEvent) => void
  ) => {
    setIsModalActive(true);
    setModalContent(content);
    setSubmitCallback(() => submitHandler);
  };

  const closeModal = () => {
    setIsModalActive(false);
  };

  return { isModalActive, modalContent, openModal, closeModal, submitCallback };
}
