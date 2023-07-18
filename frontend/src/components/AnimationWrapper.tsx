import React, { useEffect, useState } from "react";
import { styled, keyframes, css } from "styled-components";

export default function AnimationWrapper({
  show,
  children,
}: {
  show: boolean;
  children: React.ReactNode;
}) {
  const [shouldRender, setRender] = useState(show);

  useEffect(() => {
    if (show) setRender(true);
  }, [show]);

  const onAnimationEnd = () => {
    if (!show) setRender(false);
  };

  return shouldRender && <div onAnimationEnd={onAnimationEnd}>{children}</div>;
}
