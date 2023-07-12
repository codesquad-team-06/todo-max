import React, { useState } from "react";
import { styled } from "styled-components";
import ColumnCardDisplay from "./ColumnCardDisplay.tsx";
import ColumnCardMode from "./ColumnCardMode.tsx";

export default function ColumnCard() {
  const [isEditMode, setIsEditMode] = useState<boolean>(false);

  return (
    <StyledColumnCard>
      {isEditMode ? <ColumnCardMode mode="edit" /> : <ColumnCardDisplay />}
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
