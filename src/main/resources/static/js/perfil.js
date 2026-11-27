
/* ==================================================
   ABRIR MINHAS RESERVAS
   ================================================== */

async function abrirReservas() {

    const telaPerfil = document.getElementById("telaPerfil");
    const telaReservas = document.getElementById("telaReservas");

    if (telaPerfil) {
        telaPerfil.classList.remove("ativa");
    }

    if (telaReservas) {
        telaReservas.classList.add("ativa");
    }

    document.body.style.overflow = "hidden";

    const telefone = (
        localStorage.getItem("telefoneUsuario") || ""
    ).replace(/\D/g, "");

    const email = localStorage.getItem("emailUsuario") || "";

    const lista = document.getElementById("listaReservas");

    if (!lista) return;

    if (!telefone) {
        lista.innerHTML = "<p>Não foi possível identificar seu telefone de cadastro.</p>";
        return;
    }

    lista.innerHTML = "<p>Carregando suas reservas...</p>";

    try {

        const resposta = await fetch(
            "/api/reservas/minhas?telefone=" +
            encodeURIComponent(telefone)
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível buscar suas reservas.");
        }

        const reservas = await resposta.json();

        mostrarReservas(reservas);

    } catch (erro) {

        console.error("Erro ao carregar reservas:", erro);

        lista.innerHTML = `
            <div class="sem-reservas">
                <p>Não foi possível carregar suas reservas.</p>
                <p>Tente novamente mais tarde.</p>
            </div>
        `;
    }
}


/* ==================================================
   MOSTRAR RESERVAS
   ================================================== */

function mostrarReservas(reservas) {

    const lista = document.getElementById("listaReservas");

    if (!lista) return;

    lista.innerHTML = "";

    if (!Array.isArray(reservas) || reservas.length === 0) {

        lista.innerHTML = `
            <div class="sem-reservas">
                <i class="bi bi-calendar-x"
                   style="font-size:40px;color:#6b3d22;">
                </i>

                <p style="margin-top:15px;">
                    Você ainda não possui nenhuma reserva.
                </p>
            </div>
        `;

        return;
    }

    reservas.forEach(function(reserva, indice) {

        const data = reserva.dataReserva || "Não informado";

        const horario = reserva.horario || "Não informado";

        const pessoas =
            reserva.quantidadePessoas || "Não informado";

        const ambiente = reserva.ambiente || "Não informado";

        const mesa = reserva.mesa || "Não informado";

        const nome = reserva.nomeCliente || "Não informado";

        const status = reserva.status || "PENDENTE";

        const item = document.createElement("div");

        item.className = "reserva-item";

        const titulo = document.createElement("div");

        titulo.className = "reserva-titulo";
        titulo.textContent = "Reserva " + (indice + 1);

        item.appendChild(titulo);

        const campos = [
            ["Cliente", nome, "bi-person-fill"],
            ["Data", data, "bi-calendar3"],
            ["Horário", horario, "bi-clock"],
            ["Pessoas", pessoas, "bi-people-fill"],
            ["Ambiente", ambiente, "bi-geo-alt-fill"],
            ["Mesa", mesa, "bi-grid-3x3-gap-fill"],
            ["Status", status, "bi-info-circle"]
        ];

        campos.forEach(function(campo) {

            const linha = document.createElement("div");
            linha.className = "reserva-dado";

            const icone = document.createElement("i");
            icone.className = "bi " + campo[2];

            const strong = document.createElement("strong");
            strong.textContent = campo[0] + ":";

            const span = document.createElement("span");
            span.textContent = campo[1];

            linha.appendChild(icone);
            linha.appendChild(strong);
            linha.appendChild(span);

            item.appendChild(linha);

        });

        lista.appendChild(item);

    });
}
