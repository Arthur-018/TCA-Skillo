// Configurações e estado inicial
// ===============================
const mainPage = document.getElementById("mainPage");

const logo = mainPage.querySelector("#logo");
const mascote = mainPage.querySelector("#mascote");
const chatContainer = mainPage.querySelector("#chatContainer");
const mainSite = mainPage.querySelector("#mainSite");
const logoHint = mainPage.querySelector("#logoHint");
const btnLogin = document.getElementById("btnLogin");

mainSite.style.display = "none";
mascote.style.display = "none";

let logado = false;
let mascoteVisivel = false;
let chatInterval; // controla mensagens automáticas

// ===============================
// Função para criar balões de fala
// ===============================
function falar(texto) {
  const div = document.createElement("div");
  div.className = "chatBox";
  div.innerText = texto;
  chatContainer.appendChild(div);

  const maxBaloes = 5; // limite de balões na tela
  if (chatContainer.children.length > maxBaloes) {
    const primeiro = chatContainer.firstElementChild;
    primeiro.classList.add("fadeOut");
    primeiro.addEventListener("animationend", () => {
      if (primeiro.parentNode === chatContainer) {
        chatContainer.removeChild(primeiro);
      }
    }, { once: true });
  }
}

// ===============================
// Dica inicial
// ===============================
if (logoHint) {
  logoHint.innerText = "Bata na árvore para chamar Nuto, seu assistente financeiro 🐿️";
}

let shakeInterval;
let hintTimeout = setTimeout(() => {
  function startShakeLoop() {
    if (!logoHint) return;
    logoHint.classList.add("shake");
    setTimeout(() => logoHint.classList.remove("shake"), 1500);
  }
  startShakeLoop();
  shakeInterval = setInterval(startShakeLoop, 3500);
}, 10000);

// ===============================
// Mascote – aparece no canto e fala
// ===============================
function mostrarMascote() {
  mascote.style.display = "flex";
  mascote.classList.add("fixo");
  mascoteVisivel = true;

  falar("Olá! Eu sou Nuto 🐿️, seu assistente.");
  iniciarMensagens();
}

function iniciarMensagens() {
  // Frases iniciais (até 80s)
  const frasesIniciais = [
    "e obrigado por escolher a Oaksquare para investir, isso vai ser stonks! 📈",
    "Clique no botão de login para começar sua jornada 💼",
    "Vamos lá, não tenha medo de dar o primeiro passo 🚀",
    "Quanto antes você começar, mais rápido chega ao seu objetivo 🌳",
    "Ainda esperando... o botão de login está logo ali 👀",
    "Ei, estou aqui para ajudar, não me deixe falando sozinho 😅",
    "Você sabia que disciplina financeira é liberdade futura? 📊",
    "Já se passaram 80 segundos... estou ficando entediado 😴"
  ];

  // História extensa (30 falas)
  const historiaCompleta = [
    "Quer saber mais sobre mim? Eu sou um esquilo curioso, e adoro guardar segredos 🌰",
    "Um segredo de dentro do código: Rafael Barreto fez o visual do site e as animações ✨. Eu acho que ele tem bom gosto, até para escolher as cores!",
    "Sabia que esquilos enterram sementes e ajudam a nascer novas árvores? 🌳 Eu faço isso com ideias também.",
    "Outro segredo: Arthur criou o sistema de investimentos 💹. Eu acho que ele pensa como um esquilo, sempre guardando para o futuro!",
    "Você sabia que a linguagem Java originalmente se chamava Oak? 🍂 Eu gosto disso, porque combina com carvalhos e comigo 🐿️",
    "James Gosling foi quem criou o Java em 1995 ☕. Eu acho que ele devia gostar de árvores também.",
    "Mais um segredo: Danilo desenvolveu a inteligência artificial do sistema 🤖. Eu acho que ele me deu um cérebro esperto, mas ainda gosto de nozes!",
    "Eu comecei a trabalhar na Oaksquare para ajudar pessoas a crescerem como árvores 🌳. Afinal, esquilos e carvalhos são parceiros naturais.",
    "E não posso esquecer: Moisés estruturou o banco de dados 🗄️. Eu acho que ele é como um esquilo que organiza todas as nozes direitinho!",
    "O nome Oaksquare vem do carvalho (Oak) e dos esquilos que guardam sementes 🐿️. Eu gosto de pensar que sou parte dessa história.",
    "Às vezes esqueço onde guardei minhas nozes... e isso vira floresta 😅. Acho que é parecido com esquecer senhas de banco!",
    "Rafael também fez as animações que me fazem flutuar. Eu gosto, me sinto leve como uma folha 🍂",
    "Arthur pensou em cada detalhe dos investimentos. Eu acho que ele deve ser bom em guardar nozes também 💹",
    "Danilo me deu inteligência artificial. Eu ainda não sei se isso me torna mais esperto que um esquilo normal 🤔",
    "Moisés fez o banco de dados. Eu acho que ele é como um esquilo que nunca perde uma noz 🗄️",
    "Sabia que esquilos podem saltar até 3 metros? Eu gostaria de saltar até o botão de login, mas prefiro esperar você clicar 😅",
    "Eu gosto de pensar que cada economia é como enterrar uma semente. Um dia vira uma árvore 🌳",
    "James Gosling criou o Java tomando café ☕. Eu acho que café e nozes combinam bem!",
    "O primeiro nome de Java era Oak. Eu gosto disso, porque eu moro em carvalhos 🐿️",
    "Rafael me deixou bonito com esse visual. Eu acho que ele entende de estilo ✨",
    "Arthur me ensinou a falar de investimentos. Eu acho que ele é como um esquilo que planeja o inverno 💹",
    "Danilo me deu inteligência. Eu acho que ele é como um esquilo cientista 🤖",
    "Moisés organizou tudo no banco de dados. Eu acho que ele é como um esquilo bibliotecário 🗄️",
    "Eu gosto de contar histórias. Você sabia que esquilos têm dentes que nunca param de crescer? 😬",
    "Oaksquare é como uma floresta digital. Eu sou o esquilo que guia você 🌳",
    "Às vezes fico entediado, mas gosto de conversar. Você quer saber mais sobre mim?",
    "Eu posso falar de finanças, de esquilos, de Java, ou até dos criadores do código. O que você prefere?",
    "Eu acho que cada um dos criadores deixou um pedaço de si aqui. Eu sou o resultado disso 🐿️",
    "E eu continuo aqui, esperando você clicar no login... mas feliz em contar histórias 😅"
  ];

  let index = 0;
  let filaAleatoria = [];

  // Função para embaralhar array (Fisher-Yates)
  function embaralhar(array) {
    let copia = array.slice();
    for (let i = copia.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [copia[i], copia[j]] = [copia[j], copia[i]];
    }
    return copia;
  }

  clearInterval(chatInterval);

  chatInterval = setInterval(() => {
    if (index < frasesIniciais.length) {
      falar(frasesIniciais[index]);
      index++;
    } else {
      // Se a fila acabou, embaralha de novo
      if (filaAleatoria.length === 0) {
        filaAleatoria = embaralhar(historiaCompleta);
      }
      // Pega a próxima fala da fila
      const proxima = filaAleatoria.shift();
      falar(proxima);
    }
  }, 10000); // intervalo de 10 segundos
}

function esconderMascote() {
  mascote.style.display = "none";
  mascote.classList.remove("fixo");
  mascoteVisivel = false;
  clearInterval(chatInterval);
  chatContainer.innerHTML = "";
}

// ===============================
// Evento da Logo
// ===============================
logo.addEventListener("click", () => {
  clearTimeout(hintTimeout);
  clearInterval(shakeInterval);
  if (logoHint) {
    logoHint.style.display = "none";
    logoHint.classList.remove("shake");
  }

  if (!logado) {
    mostrarMascote();
    logado = true;
  } else {
    if (!mascoteVisivel) {
      mostrarMascote();
    } else {
      falar("Até mais! Vou descansar 🌙");
      setTimeout(() => {
        esconderMascote();
      }, 1200);
    }
  }
});
// ===============================
// Evento do Botão de Login
// ===============================
btnLogin.addEventListener("click", () => {
  window.location.href = "./loguin/login.html";
  logado = true;
});

