import React, { useState } from "react";
import { styled } from "styled-components";
import ColumnCardDisplay from "./ColumnCardDisplay.tsx";
import ColumnCardMode from "./ColumnCardMode.tsx";
import { Card } from "../../types.ts";

export default function ColumnCard({
  cardDetails,
  editCardHandler,
  deleteCardHandler,
}: {
  cardDetails: {
    id: number;
    title: string;
    content: string;
  };
  editCardHandler: (card: Card) => void;
  deleteCardHandler: (deletedCardInfo: {
    id: number;
    columnId: number;
  }) => void;
}) {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);

  const toggleEditMode = () => {
    setIsEditMode(!isEditMode);
  };

  return (
    <StyledColumnCard>
      {isEditMode ? (
        <ColumnCardMode
          {...{
            mode: "edit",
            cardDetails,
            toggleEditMode,
            editCardHandler,
          }}
        />
      ) : (
        <ColumnCardDisplay
          {...{
            cardDetails,
            toggleEditMode,
            deleteCardHandler,
          }}
        />
      )}
    </StyledColumnCard>
  );
}

const StyledColumnCard = styled.li`
  width: 100%;
  min-height: 104px;
  padding: 16px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};
`;
