document.addEventListener("DOMContentLoaded", () => {
  const chatForm = document.getElementById("chatForm");
  const chatInput = document.getElementById("chatInput");
  const chatWindow = document.getElementById("chatWindow");
  const sendPDFButton = document.getElementById("sendPDF");
  const pdfViewer = document.getElementById("pdfViewer");
  const pdfFrame = document.getElementById("pdfFrame");
  const dropZone = document.getElementById("dropZone");
  const fileInput = document.getElementById("fileInput");

  // ===== Função para adicionar mensagens ao chat =====
  function addMessage(content, sender = "user", isHTML = false) {
    const msg = document.createElement("div");
    msg.classList.add("message", sender);
    msg.innerHTML = isHTML ? content : content;
    chatWindow.appendChild(msg);
    chatWindow.scrollTop = chatWindow.scrollHeight;
  }

  // ===== Simulação de resposta da IA =====
  function aiResponse(userText) {
    setTimeout(() => {
      let reply;
      if (userText.toLowerCase().includes("extrato")) {
        reply = "📊 Entendido! Assim que você enviar seus PDFs, vou analisar seus gastos e receitas.";
      } else {
        reply = "🤖 Estou processando sua mensagem. Em breve trarei insights financeiros.";
      }
      addMessage(reply, "system");
    }, 1000);
  }

  // ===== Evento de envio do chat =====
  chatForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const text = chatInput.value.trim();
    if (!text) return;
    addMessage(text, "user");
    chatInput.value = "";
    aiResponse(text);
  });

  // ===== Função para lidar com múltiplos PDFs =====
  function handleFiles(files) {
    let pdfs = JSON.parse(localStorage.getItem("uploadedPDFs")) || [];

    Array.from(files).forEach(file => {
      if (file.type === "application/pdf") {
        const reader = new FileReader();
        reader.onload = () => {
          pdfs.push(reader.result);
          localStorage.setItem("uploadedPDFs", JSON.stringify(pdfs));

          // Mostra último PDF no visualizador principal
          pdfFrame.src = reader.result;
          pdfViewer.style.display = "block";
        };
        reader.readAsDataURL(file);
      } else {
        alert("Por favor, envie apenas arquivos PDF.");
      }
    });
  }

  // ===== Drag & Drop =====
  dropZone.addEventListener("click", () => fileInput.click());

  dropZone.addEventListener("dragover", (e) => {
    e.preventDefault();
    dropZone.classList.add("dragover");
  });

  dropZone.addEventListener("dragleave", () => {
    dropZone.classList.remove("dragover");
  });

  dropZone.addEventListener("drop", (e) => {
    e.preventDefault();
    dropZone.classList.remove("dragover");
    handleFiles(e.dataTransfer.files);
  });

  fileInput.addEventListener("change", (e) => {
    handleFiles(e.target.files);
  });

  // ===== Enviar PDFs como mensagens no chat =====
  sendPDFButton.addEventListener("click", () => {
    let pdfs = JSON.parse(localStorage.getItem("uploadedPDFs")) || [];

    if (pdfs.length > 0) {
      pdfs.forEach((pdf, index) => {
        const pdfMessage = document.createElement("div");
        pdfMessage.classList.add("message", "user");
        pdfMessage.innerHTML = `
          📄 PDF ${index + 1} carregado:<br>
          <iframe src="${pdf}" width="100%" height="200px" style="border:1px solid #ccc; border-radius:6px;"></iframe>
          <br><button class="delete-pdf">Excluir PDF ${index + 1}</button>
        `;
        chatWindow.appendChild(pdfMessage);
        chatWindow.scrollTop = chatWindow.scrollHeight;

        // Evento para excluir PDF específico
        const deleteBtn = pdfMessage.querySelector(".delete-pdf");
        deleteBtn.addEventListener("click", () => {
          pdfs.splice(index, 1);
          localStorage.setItem("uploadedPDFs", JSON.stringify(pdfs));
          pdfMessage.remove();
          addMessage(`❌ PDF ${index + 1} removido.`, "system");
        });
      });

      // Esconde visualizador principal
      pdfViewer.style.display = "none";
      pdfFrame.src = "";

      // Resposta da IA simulada
      setTimeout(() => {
        addMessage("✅ Recebi seus PDFs! Vou analisar os dados em breve.", "system");
      }, 1000);
    } else {
      alert("Nenhum PDF foi carregado ainda. Arraste ou selecione arquivos primeiro.");
    }
  });

  // ===== Se já houver PDFs salvos, mostrar último no viewer =====
  const savedPDFs = JSON.parse(localStorage.getItem("uploadedPDFs")) || [];
  if (savedPDFs.length > 0) {
    pdfFrame.src = savedPDFs[savedPDFs.length - 1]; // mostra o último
    pdfViewer.style.display = "block";
  }
});

document.addEventListener("DOMContentLoaded", () => {
  const globalOverlay = document.getElementById("globalDropOverlay");
  const fileInput = document.getElementById("fileInput"); // já existente
  const dropZone = document.getElementById("dropZone");   // já existente

  // Mostrar overlay quando arrastar arquivos
  window.addEventListener("dragover", (e) => {
    e.preventDefault();
    globalOverlay.style.display = "flex";
  });

  // Esconder overlay quando sair da janela
  window.addEventListener("dragleave", (e) => {
    if (e.relatedTarget === null) {
      globalOverlay.style.display = "none";
    }
  });

  // Soltar arquivo em qualquer lugar da página
  window.addEventListener("drop", (e) => {
    e.preventDefault();
    globalOverlay.style.display = "none";

    if (e.dataTransfer.files.length > 0) {
      const files = e.dataTransfer.files;
      // Reaproveita função existente para lidar com PDFs
      handleFiles(files);
    }
  });
});

