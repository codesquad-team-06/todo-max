import React, { useState } from "react";
import { styled } from "styled-components";
import ColumnCardDisplay from "./ColumnCardDisplay.tsx";
import ColumnCardMode from "./ColumnCardMode.tsx";

// TODO: Receive card `id, title, content` as props from `Column`.
export default function ColumnCard() {
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
            cardId: 1,
            cardTitle: "I am card title",
            cardContent: "I am card content",
            toggleEditMode,
          }}
        />
      ) : (
        <ColumnCardDisplay
          {...{
            cardTitle: "I am card title",
            cardContent: "I am card content",
            toggleEditMode,
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
