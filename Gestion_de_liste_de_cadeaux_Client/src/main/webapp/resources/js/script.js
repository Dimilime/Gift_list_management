const searchInput = document.querySelector("#result");
const searchResult = document.querySelector("#result");
const copyLinkInput = document.querySelector("#copyLinkInput");
const copyLinkBtn = document.querySelector("#copyLinkBtn");
const link = copyLinkInput.ariaValueText;


copyLinkInput.addEventListener("focus", () => copyLinkInput.ariaSelected)