document.addEventListener("DOMContentLoaded", () => {
  const header = document.querySelector("header");
  const main = document.querySelector(".page-ajuda");

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
document.addEventListener("DOMContentLoaded", () => {
  const faqItems = document.querySelectorAll(".faq-item");

  faqItems.forEach(item => {
    const question = item.querySelector(".faq-question");
    question.addEventListener("click", () => {
      item.classList.toggle("active");
    });
  });

  // Ajuste dinâmico do padding-top (como já fizemos antes)
  const header = document.querySelector("header");
  const main = document.querySelector(".page-ajuda");
  if (header && main) {
    const headerHeight = header.offsetHeight;
    main.style.paddingTop = `${headerHeight + 20}px`;
    window.addEventListener("resize", () => {
      main.style.paddingTop = `${header.offsetHeight + 20}px`;
    });
  }
});
