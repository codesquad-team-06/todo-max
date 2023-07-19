import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import browserServiceWorker from "./mocks/browserServiceWorker.ts";

// eslint-disable-next-line import/prefer-default-export
export const API_URL =
  process.env.NODE_ENV === "development" ? "" : "http://localhost:8080";

if (process.env.NODE_ENV === "development") {
  browserServiceWorker.start({
    onUnhandledRequest: "bypass",
  });
}

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
