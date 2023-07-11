const designSystem = {
  colors: {
    grey50: "#FEFEFE",
    grey100: "#F7F7FC",
    grey200: "#EFF0F6",
    grey300: "#D9DBE9",
    grey400: "#BEC1D5",
    grey500: "#A0A3BD",
    grey600: "#6E7191",
    grey700: "#4E4B66",
    grey800: "#2A2A44",
    grey900: "#14142B",
    blue: "#007AFF",
    navy: "#0025E6",
    red: "#FF3B30",
  },
  filter: {
    red: "brightness(0) saturate(100%) invert(30%) sepia(38%) saturate(3720%) hue-rotate(341deg) brightness(107%) contrast(104%)",
    blue: "brightness(0) saturate(100%) invert(34%) sepia(80%) saturate(4075%) hue-rotate(201deg) brightness(103%) contrast(104%)",
  },
  opacity: {
    hover: 0.8,
    disabled: 0.3,
  },
  font: {
    displayBold24: "700 24px Pretendard, sans-serif",
    displayBold16: "700 16px Pretendard, sans-serif",
    displayBold14: "700 14px Pretendard, sans-serif",
    displayBold12: "700 12px Pretendard, sans-serif",
    displayMD16: "500 16px/22px Pretendard, sans-serif",
    displayMD14: "500 14px Pretendard, sans-serif",
    displayMD12: "500 12px Pretendard, sans-serif",
    selectedBold16: "700 16px Pretendard, sans-serif",
    selectedBold14: "700 14px Pretendard, sans-serif",
    availableMD16: "500 16px/22px Pretendard, sans-serif",
    availableMD14: "500 14px Pretendard, sans-serif",
  },
  objectStyles: {
    radius: {
      s: "8px",
      m: "16px",
      l: "50%",
    },
    dropShadow: {
      normal: "0px 1px 4px 0px rgba(110, 128, 145, 0.24)",
      up: "0px 2px 8px 0px rgba(110, 128, 145, 0.16), 0px 2px 8px 0px rgba(110, 128, 145, 0.16)",
      floating:
        "0px 16px 16px 0px rgba(110, 128, 145, 0.24), 0px 0px 4px 0px rgba(110, 128, 145, 0.08)",
    },
  },
};

export default designSystem;
