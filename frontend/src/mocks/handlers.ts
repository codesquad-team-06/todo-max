import { rest } from "msw";

export const handlers = [
  // TODO: 카드 목록 불러오기
  rest.get("/cards", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(allData));
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
const allData = [
  {
    column_id: 1,
    name: "해야할 일",
    cards: [
      {
        id: 1,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 1024,
        column_id: 1,
      },
      {
        id: 2,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 2048,
        column_id: 1,
      },
      {
        id: 3,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 4096,
        column_id: 1,
      },
    ],
  },
  {
    column_id: 2,
    name: "하고있는 일",
    cards: [
      {
        id: 4,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 1024,
        column_id: 2,
      },
      {
        id: 5,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 2048,
        column_id: 2,
      },
      {
        id: 6,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 4096,
        column_id: 2,
      },
    ],
  },
  {
    column_id: 3,
    name: "완료한 일",
    cards: [
      {
        id: 7,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 1024,
        column_id: 3,
      },
      {
        id: 8,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 2048,
        column_id: 3,
      },
      {
        id: 9,
        title: "ERD 설계하기",
        content: "팀원들과 회의를 통해 ERD 설계를 완성하기",
        position: 4096,
        column_id: 3,
      },
    ],
  },
];
// 추가 요청 성공
const successCardAdd = {
  card: {
    id: 1,
    title: "ERD 설계하기",
    content: "팀원들과 협업하여 ERD 설계 완성하기",
    position: 1024,
    column_id: 1,
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
    title: "ERD 및 API 설계하기",
    content: "팀원들과 협업하여 ERD 및 API 설계 완성하기",
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
    is_deleted: true,
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
    position: 2560,
    column_id: 1,
  },
  success: true,
};
// Todo 다른 칼럼 카드 이동
const successDiffColumnMove = {
  card: {
    id: 3,
    position: 1536,
    column_id: 2,
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
    id: 4,
    card_title: "ERD 및 API 설계하기",
    prev_column: "하고있는 일",
    next_column: "하고있는 일",
    timestamp: "1분 전",
    action_name: "삭제",
  },
  {
    id: 3,
    card_title: "ERD 설계하기",
    prev_column: "하고있는 일",
    next_column: "하고있는 일",
    timestamp: "3분 전",
    action_name: "수정",
  },
  {
    id: 2,
    card_title: "ERD 설계하기",
    prev_column: "해야할 일",
    next_column: "하고있는 일",
    timestamp: "5분 전",
    action_name: "이동",
  },
  {
    id: 1,
    card_title: "ERD 설계하기",
    prev_column: "해야할 일",
    next_column: "해야할 일",
    timestamp: "10분 전",
    action_name: "등록",
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
