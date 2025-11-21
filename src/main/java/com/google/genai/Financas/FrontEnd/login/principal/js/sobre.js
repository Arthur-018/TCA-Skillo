document.addEventListener("DOMContentLoaded", () => {
  const header = document.querySelector("header");
  const main = document.querySelector(".page-sobre");

  if (header && main) {
    const headerHeight = header.offsetHeight;

    // Aplica padding-top dinamicamente
    main.style.paddingTop = `${headerHeight + 20}px`; // +20px de margem extra
  }

  // Ajusta automaticamente se a janela for redimensionada
  window.addEventListener("resize", () => {
    if (header && main) {
      const headerHeight = header.offsetHeight;
      main.style.paddingTop = `${headerHeight + 20}px`;
    }
  });
});
