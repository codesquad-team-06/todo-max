import React from "react";
import { styled } from "styled-components";
import ColumnCardMode from "./ColumnCardMode.tsx";

export default function NewColumnCard({
  toggleNewCard,
}: {
  toggleNewCard: () => void;
}) {
  return (
    <StyledNewColumnCard>
      <ColumnCardMode {...{ mode: "add", toggleNewCard }} />
    </StyledNewColumnCard>
  );
}

const StyledNewColumnCard = styled.li`
  width: 100%;
  min-height: 104px;
  padding: 16px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  box-shadow: ${({ theme: { objectStyles } }) =>
    objectStyles.dropShadow.normal};
`;
