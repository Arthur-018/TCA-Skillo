document.addEventListener("DOMContentLoaded", () => {
  // ===== FAQ interativo =====
  const faqItems = document.querySelectorAll(".faq-item");
  faqItems.forEach(item => {
    const question = item.querySelector(".faq-question");
    const answer = item.querySelector(".faq-answer");
    question.addEventListener("click", () => {
      const isOpen = answer.style.maxHeight;
      document.querySelectorAll(".faq-answer").forEach(ans => ans.style.maxHeight = null);
      if (!isOpen) {
        answer.style.maxHeight = answer.scrollHeight + "px";
      }
    });
  });

  // ===== Gráfico scatter simples =====
  const ctx1 = document.getElementById("graficoInvestimentos")?.getContext("2d");
  if (ctx1) {
    new Chart(ctx1, {
      type: "scatter",
      data: {
        datasets: [
          { label: "Iniciantes", data: [{ x: 10, y: 20 }], backgroundColor: "#4CAF50" },
          { label: "Intermediários", data: [{ x: 50, y: 50 }], backgroundColor: "#FFC107" },
          { label: "Experientes", data: [{ x: 90, y: 80 }], backgroundColor: "#F44336" }
        ]
      },
      options: {
        plugins: { tooltip: { enabled: true } },
        scales: {
          x: { title: { display: true, text: "Nível de Risco (%)" }, min: 0, max: 100 },
          y: { title: { display: true, text: "Potencial de Retorno (%)" }, min: 0, max: 100 }
        }
      }
    });
  }

  // ===== Gráfico avançado com áreas =====
  const ctx2 = document.getElementById("graficoPerfis")?.getContext("2d");
  let graficoPerfis;
  if (ctx2) {
    graficoPerfis = new Chart(ctx2, {
      type: "line",
      data: {
        labels: Array.from({ length: 11 }, (_, i) => i * 10),
        datasets: [
          {
            label: "Zona Iniciantes",
            data: [20,20,20,20,20,20,20,20,20,20,20],
            fill: true,
            backgroundColor: "rgba(76, 175, 80, 0.2)",
            borderColor: "#4CAF50",
            tension: 0.3
          },
          {
            label: "Zona Intermediários",
            data: [30,40,50,55,60,65,70,70,70,70,70],
            fill: true,
            backgroundColor: "rgba(255, 193, 7, 0.2)",
            borderColor: "#FFC107",
            tension: 0.3
          },
          {
            label: "Zona Experientes",
            data: [40,50,60,70,80,85,90,95,95,95,95],
            fill: true,
            backgroundColor: "rgba(244, 67, 54, 0.2)",
            borderColor: "#F44336",
            tension: 0.3
          }
        ]
      },
      options: {
        responsive: true,
        interaction: { mode: "index", intersect: false },
        plugins: {
          tooltip: {
            callbacks: {
              label: function(context) {
                return `${context.dataset.label}: Retorno ~${context.parsed.y}%`;
              }
            }
          },
          legend: { position: "bottom" }
        },
        scales: {
          x: { title: { display: true, text: "Nível de Risco (%)" }, min: 0, max: 100 },
          y: { title: { display: true, text: "Potencial de Retorno (%)" }, min: 0, max: 100 }
        }
      }
    });
  }

  // ===== Seleção de perfil =====
  const perfilSelect = document.getElementById("perfilSelect");
  if (perfilSelect && graficoPerfis) {
    perfilSelect.addEventListener("change", () => {
      const perfil = perfilSelect.value;
      graficoPerfis.data.datasets.forEach(ds => {
        ds.backgroundColor = ds.label.toLowerCase().includes(perfil)
          ? ds.backgroundColor.replace("0.2", "0.5")
          : ds.backgroundColor.replace("0.5", "0.2");
      });
      graficoPerfis.update();
    });
  }
});
