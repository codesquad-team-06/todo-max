module.exports = {
  env: {
    browser: true,
  },
  extends: [
    "airbnb",
    "plugin:@typescript-eslint/recommended",
    "prettier",
    "react-app",
  ],
  parser: "@typescript-eslint/parser",
  plugins: ["@typescript-eslint", "prettier"],
  rules: {
    "prettier/prettier": ["error", { singleQuote: false }],
    "react/jsx-filename-extension": ["warn", { extensions: [".tsx"] }],
    "@typescript-eslint/no-non-null-assertion": "off",
    "no-shadow": "off",
    "quotes": ["error", "double"],
  },
  root: true,
};
