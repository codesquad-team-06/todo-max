import { setupWorker } from "msw";
import { handlers } from "./handlers";

const browserServiceWorker = setupWorker(...handlers);
export default browserServiceWorker;
