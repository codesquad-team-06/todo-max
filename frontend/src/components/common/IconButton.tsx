import React from "react";
import { styled } from "styled-components";

export default function IconButton({ src, alt }: { src: string; alt: string }) {
  return (
    <StyledIconButton>
      <img {...{ src, alt }} />
    </StyledIconButton>
  );
}

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
`;
