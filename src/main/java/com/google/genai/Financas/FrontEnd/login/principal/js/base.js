document.addEventListener("DOMContentLoaded", () => {
  initActiveLinks();
  initSidebar();
  initCompanyNameAnimation();
  initProfilePopup();
  initProfilePicture();
  initThemeSwitcher();
});

/* ===== Ativar link da página atual ===== */
function initActiveLinks() {
  const links = document.querySelectorAll(".nav-link");
  const currentPage = window.location.pathname.split("/").pop();

  links.forEach(link => {
    if (link.getAttribute("href") === currentPage) {
      link.classList.add("active");
    }
  });
}

/* ===== Sidebar principal ===== */
function initSidebar() {
  const hamburger = document.querySelector(".hamburger");
  const userIcon = document.querySelector(".user-icon");
  const sidebar = document.getElementById("userSidebar");
  const closeBtn = document.querySelector("#userSidebar .close-btn");
  const overlay = document.getElementById("overlay");

  function openSidebar() {
    sidebar?.classList.add("active");
    hamburger?.classList.add("active");
    overlay?.classList.add("active");
    if (overlay) overlay.dataset.mode = "sidebar";
  }

  function closeSidebar() {
    sidebar?.classList.remove("active");
    hamburger?.classList.remove("active");
    if (overlay?.dataset.mode === "sidebar") {
      overlay.classList.remove("active");
      overlay.dataset.mode = "";
    }
  }

  if (hamburger) hamburger.addEventListener("click", () => {
    if (sidebar?.classList.contains("active")) closeSidebar();
    else openSidebar();
  });

  if (userIcon) userIcon.addEventListener("click", () => {
    if (sidebar?.classList.contains("active")) closeSidebar();
    else openSidebar();
  });

  if (closeBtn) closeBtn.addEventListener("click", closeSidebar);

  if (overlay) {
    overlay.addEventListener("click", e => {
      if (overlay.dataset.mode === "sidebar") closeSidebar();
    });
  }
}

/* ===== Animação do nome da empresa ===== */
function initCompanyNameAnimation() {
  const companyName = document.querySelector(".company-name");
  if (companyName) {
    companyName.style.animationPlayState = "running";
    companyName.classList.remove("animated");
    void companyName.offsetWidth;
    companyName.classList.add("animated");
  }
}

/* ===== Popup de Personalizar Perfil ===== */
function initProfilePopup() {
  const profilePopup = document.getElementById("profilePopup");
  const userAvatar = document.getElementById("userAvatar");
  const overlay = document.getElementById("overlay");
  const openProfileSettings = document.getElementById("openProfileSettings");
  const closeBtn = profilePopup?.querySelector(".close-btn");

  // Avatar abre overlay em modo "profile"
  if (userAvatar && overlay) {
    userAvatar.addEventListener("click", e => {
      e.preventDefault();
      overlay.classList.add("active");
      overlay.dataset.mode = "profile";
    });
  }

  // No overlay, clicar em "Personalizar Perfil" abre popup
  if (openProfileSettings && profilePopup) {
    openProfileSettings.addEventListener("click", e => {
      e.preventDefault();
      profilePopup.classList.add("active");
    });
  }

  // Fechar popup pelo "X" e remover overlay se estiver em modo profile
  function closePopup() {
    profilePopup?.classList.remove("active");
    if (overlay?.dataset.mode === "profile") {
      overlay.classList.remove("active");
      overlay.dataset.mode = "";
    }
  }
  if (closeBtn) closeBtn.addEventListener("click", closePopup);

  // Clique no overlay fecha popup e overlay apenas em modo profile
  if (overlay) {
    overlay.addEventListener("click", e => {
      const panel = document.querySelector("#userSidebar");
      if (panel && panel.contains(e.target)) return;
      if (overlay.dataset.mode === "profile") {
        profilePopup?.classList.remove("active");
        overlay.classList.remove("active");
        overlay.dataset.mode = "";
      }
    });
  }

  // Evitar fechamento ao clicar dentro do popup
  if (profilePopup) {
    profilePopup.addEventListener("click", e => e.stopPropagation());
  }

  // ESC fecha tudo em modo profile
  document.addEventListener("keydown", e => {
    if (e.key === "Escape") {
      if (overlay?.dataset.mode === "profile") {
        profilePopup?.classList.remove("active");
        overlay.classList.remove("active");
        overlay.dataset.mode = "";
      }
    }
  });
}

/* ===== Trocar foto de perfil ===== */
function initProfilePicture() {
  const uploadPic = document.getElementById("uploadPic");
  const editPic = document.getElementById("editPic");
  const applyBtn = document.getElementById("applyImage");

  const savedImage = localStorage.getItem("userImage");
  const savedFit = localStorage.getItem("userImageFit");

  if (savedImage) {
    document.querySelectorAll("#userAvatar, #profilePic, .profile-pic").forEach(img => {
      img.src = savedImage;
      img.style.objectFit = savedFit || "cover";
    });
    if (editPic) {
      editPic.src = savedImage;
      editPic.style.objectFit = savedFit || "cover";
    }
  }

  if (uploadPic && editPic) {
    uploadPic.addEventListener("change", function () {
      const file = this.files[0];
      if (file && file.type === "image/png") {
        const reader = new FileReader();
        reader.onload = e => {
          editPic.src = e.target.result;
        };
        reader.readAsDataURL(file);
      } else {
        alert("Por favor, selecione apenas imagens PNG.");
        this.value = "";
      }
    });
  }

  document.querySelectorAll(".preset").forEach(img => {
    img.addEventListener("click", function () {
      const src = this.getAttribute("src");
      if (editPic) editPic.src = src;
    });
  });

  document.querySelectorAll("input[name='fitOption']").forEach(option => {
    option.addEventListener("change", function () {
      if (editPic) editPic.style.objectFit = this.value;
    });
  });

  if (applyBtn && editPic) {
    applyBtn.addEventListener("click", () => {
      const src = editPic.src;
      const fit = editPic.style.objectFit;

      document.querySelectorAll("#userAvatar, #profilePic, .profile-pic").forEach(img => {
        img.src = src;
        img.style.objectFit = fit;
      });

      localStorage.setItem("userImage", src);
      localStorage.setItem("userImageFit", fit);

      alert("Imagem aplicada em todos os lugares!");
    });
  }
}

/* ===== Trocar tema ===== */
function initThemeSwitcher() {
  const themeOptions = document.querySelectorAll("input[name='siteTheme']");

  // Recupera tema salvo
  const savedTheme = localStorage.getItem("siteTheme");
  if (savedTheme) {
    document.body.classList.remove("original-theme", "dark-theme", "summer-theme");
    document.body.classList.add(`${savedTheme}-theme`);

    const savedInput = document.querySelector(`input[name='siteTheme'][value='${savedTheme}']`);
    if (savedInput) savedInput.checked = true;
  } else {
    // Se não houver tema salvo, aplica original
    document.body.classList.add("original-theme");
    const originalInput = document.querySelector("input[name='siteTheme'][value='original']");
    if (originalInput) originalInput.checked = true;
  }

  // Listener para troca de tema
  themeOptions.forEach(option => {
    option.addEventListener("change", function () {
      // Remove todos os temas antes de aplicar o novo
      document.body.classList.remove("original-theme", "dark-theme", "summer-theme");

      if (this.value === "dark") {
        document.body.classList.add("dark-theme");
        localStorage.setItem("siteTheme", "dark");
      } else if (this.value === "summer") {
        document.body.classList.add("summer-theme");
        localStorage.setItem("siteTheme", "summer");
      } else {
        document.body.classList.add("original-theme");
        localStorage.setItem("siteTheme", "original");
      }
    });
  });
}

