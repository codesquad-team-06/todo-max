import React from "react";
import { styled } from "styled-components";

export default function ActionButton({
  className,
  content,
}: {
  className: string;
  content: string;
}) {
  return (
    <StyledActionButton className={className}>{content}</StyledActionButton>
  );
}

const StyledActionButton = styled.button`
  width: 132px;
  height: 32px;
  padding: 0;
  background: none;
  border: none;
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  font: ${({ theme: { font } }) => font.displayBold14};
  cursor: pointer;

  &.cancel-button {
    background-color: ${({ theme: { colors } }) => colors.grey100};
    color: ${({ theme: { colors } }) => colors.grey600};
  }

  &.add-button {
    background-color: ${({ theme: { colors } }) => colors.blue};
    color: ${({ theme: { colors } }) => colors.grey50};
  }

  &.delete-button {
    background-color: ${({ theme: { colors } }) => colors.red};
    color: ${({ theme: { colors } }) => colors.grey50};
  }
`;
