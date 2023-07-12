import React from "react";
import { styled } from "styled-components";

export default function Modal({ children }: { children: any }) {
  return (
    <Dim>
      <ContentBox>
        {children}
        <BtnWrapper>
          <DefaultBtn>취소</DefaultBtn>
          <DeleteBtn>삭제</DeleteBtn>
        </BtnWrapper>
      </ContentBox>
    </Dim>
  );
}

const Dim = styled.div`
  display: none;
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(20, 33, 43, 0.3);
`;

const ContentBox = styled.div`
  position: absolute;
  z-index: 10;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;

  width: 320px;
  height: 126px;
  background-color: ${({ theme: { colors } }) => colors.grey50};
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};
  font: ${({ theme: { font } }) => font.displayMD16};
  color: ${({ theme: { colors } }) => colors.grey600};
`;

const BtnWrapper = styled.div`
  display: flex;
  gap: 8px;
`;

const DefaultBtn = styled.button`
  width: 132px;
  height: 32px;

  border: none;
  border-radius: ${({ theme: { objectStyles } }) => objectStyles.radius.s};

  background-color: ${({ theme: { colors } }) => colors.grey100};
  font: ${({ theme: { font } }) => font.displayBold14};
  color: ${({ theme: { colors } }) => colors.grey600};
`;

const DeleteBtn = styled(DefaultBtn)`
  background-color: ${({ theme: { colors } }) => colors.red};
  color: ${({ theme: { colors } }) => colors.grey50};
`;
