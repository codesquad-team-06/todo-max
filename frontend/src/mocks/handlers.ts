import { rest } from "msw";

export const handlers = [
  // TODO: 카드 목록 불러오기
  rest.get("/cards", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(board));
  }),
  // 새로운 카드 추가
  rest.post("/cards", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successCardAdd)); // or failedCardAdd
  }),
  // 기존 카드 수정
  rest.put("/cards/1", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successCardUpdate)); // or failedCardUpdate
  }),
  // 기존 카드 삭제
  rest.delete("/cards/1", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successCardDelete)); // or failedCardDelete
  }),
  // 같은 칼럼 내 카드 이동
  rest.put("/cards/move/1", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successSameColumnMove)); // or failedMove
  }),
  // 다른 칼럼 내 카드 이동
  rest.put("/cards/move/3", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successDiffColumnMove)); // or failedMove
  }),
  // History 목록 불러오기
  rest.get("/histories", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(historyData));
  }),
  // History 삭제
  rest.delete("/histories", async (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successHistoryDelete)); // or failedHistoryDelete
  }),
];

// 초기 데이터
const board = [
  {
    columnId: 1,
    name: "해야할 일",
    cards: [
      {
        id: 1,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 1024,
        columnId: 1,
      },
      {
        id: 2,
        title: "Git 공부하기",
        content: "Git 명령어들에 대해 학습하기",
        position: 2048,
        columnId: 1,
      },
      {
        id: 3,
        title: "issue 생성",
        content: "Modal 기능 구현 관련 이슈 생성",
        position: 4096,
        columnId: 1,
      },
    ],
  },
  {
    columnId: 2,
    name: "하고있는 일",
    cards: [
      {
        id: 4,
        title: "Drag & Drop 관련 아이디어 회의",
        content: "팀원들과 회의를 통해 Drag & Drop 관련 아이디어 회의하기",
        position: 1024,
        columnId: 2,
      },
      {
        id: 5,
        title: "아웃스탠딩 구독 갱신하기",
        content: "아웃스탠딩 구독 갱신",
        position: 2048,
        columnId: 2,
      },
      {
        id: 6,
        title: "미니 세미나 준비",
        content: "주제 정해서 미니 세미나 준비하기",
        position: 4096,
        columnId: 2,
      },
    ],
  },
  {
    columnId: 3,
    name: "완료한 일",
    cards: [
      {
        id: 7,
        title: "인디아나 존스 관람",
        content: "조조영화 관람하기",
        position: 1024,
        columnId: 3,
      },
      {
        id: 8,
        title: "Disco Elysium",
        content: "Disco Elysium 게임 완료",
        position: 2048,
        columnId: 3,
      },
      {
        id: 9,
        title: "Dave the Diver",
        content: "투토리얼 해보기",
        position: 4096,
        columnId: 3,
      },
    ],
  },
];
// 추가 요청 성공
const successCardAdd = {
  card: {
    id: 10,
    title: "식재료 주문하기",
    content: "쿠팡에서 필요한 것들 주문",
    position: 1024,
    columnId: 1,
  },
  success: true,
};
// 추가 요청 실패
const failedCardAdd = {
  success: false,
  errorCode: {
    status: 400,
    code: "Bad Request",
    message: "글자 수를 초과했습니다. 카드 내용을 500자 이하로 입력해주세요.",
  },
};
//수정 요청 성공
const successCardUpdate = {
  card: {
    id: 1,
    title: "Updated Title!",
    content: "Updated Content!",
    position: 1024,
    columnId: 1,
  },
  success: true,
};
// 수정 요청 실패
const failedCardUpdate = {
  success: false,
  errorCode: {
    status: 400,
    code: "Bad Request",
    message: "내용을 입력해주세요.",
  },
};
// 삭제 요청 성공
const successCardDelete = {
  card: {
    id: 1,
    title: "ERD 설계하기",
    content: "팀원들과 협업하여 ERD 설계 완성하기",
    position: 1024,
    columnId: 1,
  },
  success: true,
};
// 삭제 요청 실패
const failedCardDelete = {
  success: false,
  errorCode: {
    status: 404,
    code: "Not Found",
    message: "존재하지 않는 카드입니다.",
  },
};
// Todo 같은 칼럼 카드 이동
const successSameColumnMove = {
  card: {
    id: 1,
    title: "blah",
    content: "blah blah blah",
    position: 5000,
    columnId: 1,
  },
  success: true,
};
// Todo 다른 칼럼 카드 이동
const successDiffColumnMove = {
  card: {
    id: 3,
    title: "Issue 생성",
    content: "Modal 기능 구현 관련 이슈 생성",
    position: 3000,
    columnId: 2,
  },
  success: true,
};
// Todo 카드 이동 실패
const failedMove = {
  success: false,
  errorCode: {
    status: 404,
    code: "Not Found",
    message: "존재하지 않는 컬럼입니다.",
  },
};
// History 불러오기
const historyData = [
  {
    id: 7,
    cardTitle: "ERD 및 API 설계하기",
    prevColumn: "하고있는 일",
    nextColumn: "하고있는 일",
    elapsedTime: "방금 전",
    actionName: "삭제",
  },
  {
    id: 6,
    cardTitle: "ERD 및 API 설계하기",
    prevColumn: "하고있는 일",
    nextColumn: "하고있는 일",
    elapsedTime: "1분 전",
    actionName: "삭제",
  },
  {
    id: 5,
    cardTitle: "ERD 및 API 설계하기",
    prevColumn: "하고있는 일",
    nextColumn: "하고있는 일",
    elapsedTime: "1분 전",
    actionName: "삭제",
  },
  {
    id: 4,
    cardTitle: "ERD 및 API 설계하기",
    prevColumn: "하고있는 일",
    nextColumn: "하고있는 일",
    elapsedTime: "1분 전",
    actionName: "삭제",
  },
  {
    id: 3,
    cardTitle: "ERD 설계하기",
    prevColumn: "하고있는 일",
    nextColumn: "하고있는 일",
    elapsedTime: "3분 전",
    actionName: "수정",
  },
  {
    id: 2,
    cardTitle: "ERD 설계하기",
    prevColumn: "해야할 일",
    nextColumn: "하고있는 일",
    elapsedTime: "5분 전",
    actionName: "이동",
  },
  {
    id: 1,
    cardTitle: "ERD 설계하기",
    prevColumn: "해야할 일",
    nextColumn: "해야할 일",
    elapsedTime: "10분 전",
    actionName: "등록",
  },
];
// Activity History 삭제
const successHistoryDelete = {
  success: true,
};
// Activity History 삭제 실패
const failedHistoryDelete = {
  success: false,
  errorCode: {
    status: 404,
    code: "Not Found",
    message: "존재하지 않는 활동 기록입니다.",
  },
};
