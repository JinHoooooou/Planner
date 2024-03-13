const GET_DETAILS = "/plan";
const GET_PLANS = "/plan/list";
const POST_CREATE_DETAIL = "/detail/create";
let planId = new URLSearchParams(window.location.search).get("planId");
let writer = "";
