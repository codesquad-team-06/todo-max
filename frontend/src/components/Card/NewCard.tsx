import React from "react";
import { styled } from "styled-components";
import CardMode from "./CardMode.tsx";
import { CardType } from "../../types.ts";

export default function NewCard({
  toggleNewCard,
  addNewCardHandler,
}: {
  toggleNewCard: () => void;
  addNewCardHandler: (card: CardType) => void;
}) {
  return (
    <StyledNewCard>
      <CardMode {...{ mode: "add", toggleNewCard, addNewCardHandler }} />
    </StyledNewCard>
  );
}

const StyledNewCard = styled.li`
  width: 100%;
  min-height: 104px;
  padding: 16px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};
`;
