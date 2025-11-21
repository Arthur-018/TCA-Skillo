document.addEventListener("DOMContentLoaded", () => {
  // ===== Seletores principais =====
  const startButton = document.querySelector("#inicio button");
  const cardsSection = document.querySelector(".cards");
  const cards = document.querySelectorAll(".card");
  const userIcon = document.querySelector(".user-icon");
  const hamburger = document.querySelector(".hamburger");
  const sidebar = document.getElementById("userSidebar");
  const overlay = document.getElementById("overlay");
  const closeBtn = sidebar?.querySelector(".close-btn");

  // ===== Botão "Começar Agora" =====
  if (startButton && cardsSection) {
    startButton.addEventListener("click", () => {
      cardsSection.scrollIntoView({ behavior: "smooth" });
    });
  }

  // ===== Animação de entrada nos cards =====
  const observer = new IntersectionObserver(
    entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add("visible");
          observer.unobserve(entry.target); // evita reaparecer
        }
      });
    },
    { threshold: 0.3 }
  );

  cards.forEach(card => observer.observe(card));

  // ===== Sidebar e Overlay =====
  function toggleSidebar(open = true) {
    sidebar?.classList.toggle("active", open);
    overlay?.classList.toggle("active", open);
  }

  userIcon?.addEventListener("click", () => toggleSidebar(true));
  hamburger?.addEventListener("click", () => toggleSidebar(true));
  closeBtn?.addEventListener("click", () => toggleSidebar(false));
  overlay?.addEventListener("click", () => toggleSidebar(false));
});
  // Exibir apenas uma vez
  window.addEventListener("load", function() {
    if (!localStorage.getItem("cookiesChoice")) {
      document.getElementById("cookiePopup").style.display = "flex";
    }
  });

  // Botão Aceitar
  document.getElementById("acceptCookies").addEventListener("click", function() {
    localStorage.setItem("cookiesChoice", "accepted");
    document.getElementById("cookiePopup").style.display = "none";
  });

  // Botão Sou alérgico a nozes
  document.getElementById("declineCookies").addEventListener("click", function() {
    localStorage.setItem("cookiesChoice", "declined");
    document.getElementById("cookiePopup").style.display = "none";
    alert("Entendido! Nenhum kokie de nozes será usado 🐿️");
  });
   