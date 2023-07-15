import React, { useState } from "react";
import { styled } from "styled-components";
import ColumnCardDisplay from "./ColumnCardDisplay.tsx";
import ColumnCardMode from "./ColumnCardMode.tsx";

export default function ColumnCard(cardDetails: {
  id: number;
  title: string;
  content: string;
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
          }}
        />
      ) : (
        <ColumnCardDisplay
          {...{
            cardDetails,
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
