import React, { MouseEvent } from "react";
import { styled } from "styled-components";

export default function IconButton({
  className,
  src,
  alt,
  disabled,
  onClick,
}: {
  className: string;
  src: string;
  alt: string;
  disabled?: boolean;
  onClick?: (evt: MouseEvent) => void;
}) {
  return (
    <StyledIconButton
      {...{ className, disabled, onClick, $disabled: disabled }}>
      <img {...{ src, alt }} />
    </StyledIconButton>
  );
}

IconButton.defaultProps = {
  disabled: false,
  onClick: undefined,
};

const StyledIconButton = styled.button<{ $disabled: boolean | undefined }>`
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
    filter: ${({ theme: { filter }, $disabled }) =>
      $disabled ? "" : filter.blue};
  }

  &.delete-button:hover {
    filter: ${({ theme: { filter }, $disabled }) =>
      $disabled ? "" : filter.red};
  }
`;
