import React, { MouseEvent } from "react";
import { styled } from "styled-components";

export default function IconButton({
  className,
  src,
  alt,
  onClick,
}: {
  className: string;
  src: string;
  alt: string;
  onClick?: (evt: MouseEvent) => void;
}) {
  return (
    <StyledIconButton {...{ className, onClick }}>
      <img {...{ src, alt }} />
    </StyledIconButton>
  );
}

IconButton.defaultProps = {
  onClick: undefined,
};

const StyledIconButton = styled.button`
  width: 24px;
  height: 24px;
  padding: 0;
  background: none;
  border: none;
  cursor: pointer;

  img {
    width: inherit;
    height: inherit;
  }

  &.add-button:hover {
    filter: ${({ theme: { filter } }) => filter.blue};
  }

  &.delete-button:hover {
    filter: ${({ theme: { filter } }) => filter.red};
  }
`;
