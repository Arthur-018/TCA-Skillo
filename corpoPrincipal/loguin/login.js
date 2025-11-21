// ==============================
// Login/Cadastro - JS organizado
// ==============================

// Wrapper exclusivo do login
const loginPage = document.getElementById("loginPage");
const loginFlip = loginPage?.querySelector("#loginFlip");

// Botões da porta deslizante
const registerButton = loginPage?.querySelector("#register");
const loginButton    = loginPage?.querySelector("#login");

// Alternância da porta
if (registerButton && loginFlip) {
  registerButton.addEventListener("click", () => {
    loginFlip.classList.remove("close");
    loginFlip.classList.add("active");
  });
}
if (loginButton && loginFlip) {
  loginButton.addEventListener("click", () => {
    loginFlip.classList.remove("active");
    loginFlip.classList.add("close");
  });
}

// ==============================
// Utilitários de validação
// ==============================
function validarEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
}
function validarSenha(senha) {
  return senha.length >= 6;
}
function mostrarMensagem(container, texto, tipo = "erro") {
  if (!container) return;
  container.textContent = texto;
  container.style.color = tipo === "erro" ? "red" : "green";
}

// ==============================
// Elementos: Login
// ==============================
const loginForm  = document.getElementById("loginForm");
const emailInput = document.getElementById("email");
const senhaInput = document.getElementById("senha");
const checkSenha = document.getElementById("checkSenha");
const btnLogin   = document.getElementById("btnLogin");
const msgLogin   = document.getElementById("mensagemLogin");

// Revelar senha (login)
if (checkSenha && senhaInput) {
  checkSenha.addEventListener("change", () => {
    senhaInput.type = checkSenha.checked ? "text" : "password";
  });
}

// ==============================
// Elementos: Cadastro
// ==============================
const registerForm        = document.getElementById("registerForm");
const nomeCadastroInput   = document.getElementById("nomeCadastro");
const emailCadastroInput  = document.getElementById("emailCadastro");
const senhaCadastroInput  = document.getElementById("senhaCadastro");
const checkSenhaCadastro  = document.getElementById("checkSenhaCadastro");
const aceitoTermosCadastro= document.getElementById("aceitoTermosCadastro");
const labelTermos         = document.getElementById("labelTermos");
const msgCadastro         = document.getElementById("mensagemCadastro");
const btnCadastrar        = registerForm?.querySelector("button[type='submit']");

// Revelar senha (cadastro)
if (checkSenhaCadastro && senhaCadastroInput) {
  checkSenhaCadastro.addEventListener("change", () => {
    senhaCadastroInput.type = checkSenhaCadastro.checked ? "text" : "password";
  });
}

// Habilitar botão de cadastro ao aceitar termos
if (btnCadastrar && aceitoTermosCadastro) {
  btnCadastrar.disabled = true;
  btnCadastrar.style.opacity = "0.5";

  aceitoTermosCadastro.addEventListener("change", () => {
    const permitido = aceitoTermosCadastro.checked;
    btnCadastrar.disabled = !permitido;
    btnCadastrar.style.opacity = permitido ? "1" : "0.5";
  });
}

// Abrir termos ao clicar no label (fora do input)
if (labelTermos) {
  labelTermos.addEventListener("click", (e) => {
    if (e.target.tagName.toLowerCase() !== "input") {
      window.open("termos.html", "_blank");
    }
  });
}

// Envio do cadastro (demonstração – somente validações front)
if (registerForm) {
  registerForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const nome  = nomeCadastroInput?.value.trim() || "";
    const email = emailCadastroInput?.value.trim() || "";
    const senha = senhaCadastroInput?.value.trim() || "";

    if (nome.length < 2) {
      mostrarMensagem(msgCadastro, "Digite um nome válido.");
      return;
    }
    if (!validarEmail(email)) {
      mostrarMensagem(msgCadastro, "Digite um e-mail válido.");
      return;
    }
    if (!validarSenha(senha)) {
      mostrarMensagem(msgCadastro, "A senha deve ter pelo menos 6 caracteres.");
      return;
    }
    if (!aceitoTermosCadastro?.checked) {
      mostrarMensagem(msgCadastro, "Você precisa aceitar os termos.");
      return;
    }

    mostrarMensagem(msgCadastro, "Cadastro realizado com sucesso! ✅", "sucesso");
    // Futuro: enviar dados para backend via fetch()
  });
}

// ==============================
// Login funcional de demonstração
// ==============================
const DEMO_EMAIL = "oaksquareoriginal@gmail.com";
const DEMO_SENHA = "Esquilo1234";

// Preferir submit do form para cobrir Enter no teclado e clique no botão
if (loginForm) {
  loginForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const email = emailInput?.value.trim() || "";
    const senha = senhaInput?.value.trim() || "";

    // Validações front básicas
    if (!validarEmail(email)) {
      mostrarMensagem(msgLogin, "Digite um e-mail válido.");
      return;
    }
    if (!validarSenha(senha)) {
      mostrarMensagem(msgLogin, "A senha deve ter pelo menos 6 caracteres.");
      return;
    }

    // Exceção de demonstração
    if (email === DEMO_EMAIL && senha === DEMO_SENHA) {
      mostrarMensagem(msgLogin, "Login realizado com sucesso! ✅", "sucesso");
      // Redireciona para a página principal de demonstração
      window.location.href = "principal/principal.html";
    } else {
      mostrarMensagem(msgLogin, "Este email não está vinculado ao sistema.");
    }
  });
}

// Também captura clique no botão (caso haja prevenção padrão diferente)
if (btnLogin && loginForm) {
  btnLogin.addEventListener("click", (e) => {
    // Deixa o submit central cuidar
    e.preventDefault();
    loginForm.dispatchEvent(new Event("submit", { bubbles: true }));
  });
}