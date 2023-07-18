import React, { ReactElement, useEffect, useState } from "react";

export default function AnimationWrapper({
  isShowing,
  children,
}: {
  isShowing: boolean;
  children: ReactElement;
}) {
  const [shouldRender, setRender] = useState(isShowing);

  useEffect(() => {
    if (isShowing) setRender(true);
  }, [isShowing]);

  const onAnimationEnd = () => {
    if (!isShowing) setRender(false);
  };

  return shouldRender && <div onAnimationEnd={onAnimationEnd}>{children}</div>;
}
