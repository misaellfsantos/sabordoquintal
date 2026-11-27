
/* ==================================================
   PEGAR DADOS DO LOGIN
   ================================================== */

function pegarDadosLogin() {

    const nome = localStorage.getItem("nomeUsuario");
    const email = localStorage.getItem("emailUsuario");
    const telefone = localStorage.getItem("telefoneUsuario");

    const perfilNome = document.getElementById("perfilNome");
    const perfilNomeDado = document.getElementById("perfilNomeDado");
    const perfilEmail = document.getElementById("perfilEmail");
    const perfilTelefone = document.getElementById("perfilTelefone");
    const fotoPerfil = document.getElementById("fotoPerfil");

    if (perfilNome) perfilNome.textContent = nome || "Usuário";
    if (perfilNomeDado) perfilNomeDado.textContent = nome || "Não informado";
    if (perfilEmail) perfilEmail.textContent = email || "Não informado";
    if (perfilTelefone) perfilTelefone.textContent = telefone || "Não informado";

    const fotoSalva = localStorage.getItem("usuarioFoto");

    if (fotoPerfil) {
        fotoPerfil.src = fotoSalva || "/img/logo.png";
    }
}


/* ==================================================
   ABRIR PERFIL
   ================================================== */

function abrirPerfil() {

    const usuarioLogado = localStorage.getItem("usuarioLogado");

    if (usuarioLogado !== "true") {
        alert("Faça login para acessar o perfil.");
        window.location.href = "/login";
        return;
    }

    pegarDadosLogin();

    const telaPerfil = document.getElementById("telaPerfil");

    if (telaPerfil) {
        telaPerfil.classList.add("ativa");
        document.body.style.overflow = "hidden";
    }
}


/* ==================================================
   FECHAR PERFIL
   ================================================== */

function fecharPerfil() {

    const telaPerfil = document.getElementById("telaPerfil");

    if (telaPerfil) {
        telaPerfil.classList.remove("ativa");
    }

    document.body.style.overflow = "";
}


/* ==================================================
   FOTO DO PERFIL
   ================================================== */

function configurarFotoPerfil() {

    const inputFoto = document.getElementById("inputFoto");

    if (!inputFoto) return;

    inputFoto.addEventListener("change", function() {

        const arquivo = this.files[0];

        if (!arquivo) return;

        if (!arquivo.type.startsWith("image/")) {
            alert("Escolha uma imagem válida.");
            return;
        }

        const leitor = new FileReader();

        leitor.onload = function(evento) {

            const foto = evento.target.result;
            const imagem = document.getElementById("fotoPerfil");

            if (imagem) {
                imagem.src = foto;
            }

            localStorage.setItem("usuarioFoto", foto);
        };

        leitor.readAsDataURL(arquivo);

    });
}


/* ==================================================
   ABRIR MINHAS RESERVAS
   ================================================== */

function abrirReservas() {

    const telaPerfil = document.getElementById("telaPerfil");
    const telaReservas = document.getElementById("telaReservas");

    if (telaPerfil) {
        telaPerfil.classList.remove("ativa");
    }

    let reservas = [];

    const reservasSalvas =
        localStorage.getItem("reservasSaborQuintal") ||
        localStorage.getItem("reservaSaborQuintal") ||
        localStorage.getItem("reservas");

    if (reservasSalvas) {

        try {

            const dados = JSON.parse(reservasSalvas);

            if (Array.isArray(dados)) {
                reservas = dados;
            } else if (dados) {
                reservas = [dados];
            }

        } catch (erro) {
            console.error("Erro ao carregar reservas:", erro);
        }

    }

    const nomeCliente = (
        localStorage.getItem("nomeUsuario") || ""
    ).trim().toLowerCase();

    const emailCliente = (
        localStorage.getItem("emailUsuario") || ""
    ).trim().toLowerCase();

    const telefoneCliente = (
        localStorage.getItem("telefoneUsuario") || ""
    ).replace(/\D/g, "");

    const minhasReservas = reservas.filter(function(reserva) {

        const nomeReserva = (
            reserva.nomeUsuario ||
            reserva.nomeCliente ||
            reserva.nome ||
            reserva.cliente ||
            ""
        ).trim().toLowerCase();

        const emailReserva = (
            reserva.emailUsuario ||
            reserva.email ||
            reserva.usuarioEmail ||
            ""
        ).trim().toLowerCase();

        const telefoneReserva = (
            reserva.telefone ||
            reserva.telefoneUsuario ||
            reserva.usuarioTelefone ||
            ""
        ).replace(/\D/g, "");

        if (emailCliente && emailReserva) {
            return emailReserva === emailCliente;
        }

        if (telefoneCliente && telefoneReserva) {
            return telefoneReserva === telefoneCliente;
        }

        if (nomeCliente && nomeReserva) {
            return nomeReserva === nomeCliente;
        }

        return false;

    });

    mostrarReservas(minhasReservas);

    if (telaReservas) {
        telaReservas.classList.add("ativa");
        document.body.style.overflow = "hidden";
    }
}


/* ==================================================
   MOSTRAR RESERVAS
   ================================================== */

function mostrarReservas(reservas) {

    const lista = document.getElementById("listaReservas");

    if (!lista) return;

    lista.innerHTML = "";

    if (reservas.length === 0) {

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

        const data =
            reserva.data ||
            reserva.dia ||
            reserva.dataReserva ||
            "Não informado";

        const horario =
            reserva.horario ||
            reserva.hora ||
            "Não informado";

        const pessoas =
            reserva.pessoas ||
            reserva.quantidadePessoas ||
            reserva.quantidade ||
            "Não informado";

        const ambiente =
            reserva.ambiente ||
            reserva.local ||
            "Não informado";

        const mesa =
            reserva.mesa ||
            reserva.numeroMesa ||
            "Não informado";

        const nome =
            reserva.nomeUsuario ||
            reserva.nomeCliente ||
            reserva.nome ||
            reserva.cliente ||
            "Não informado";

        const item = document.createElement("div");

        item.className = "reserva-item";

        const campos = [
            ["Cliente", nome, "bi-person-fill"],
            ["Data", data, "bi-calendar3"],
            ["Horário", horario, "bi-clock"],
            ["Pessoas", pessoas, "bi-people-fill"],
            ["Ambiente", ambiente, "bi-geo-alt-fill"],
            ["Mesa", mesa, "bi-grid-3x3-gap-fill"]
        ];

        const titulo = document.createElement("div");

        titulo.className = "reserva-titulo";
        titulo.textContent = "Reserva " + (indice + 1);

        item.appendChild(titulo);

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


/* ==================================================
   FECHAR RESERVAS
   ================================================== */

function fecharReservas() {

    const telaReservas = document.getElementById("telaReservas");
    const telaPerfil = document.getElementById("telaPerfil");

    if (telaReservas) {
        telaReservas.classList.remove("ativa");
    }

    if (telaPerfil) {
        telaPerfil.classList.add("ativa");
        pegarDadosLogin();
    }

    document.body.style.overflow = "hidden";
}


/* ==================================================
   SAIR DA CONTA
   ================================================== */

function sairDaConta() {

    localStorage.removeItem("usuarioLogado");

    fecharPerfil();

    alert("Você saiu da sua conta. Seus dados continuam salvos.");

    window.location.href = "/login";
}


/* ==================================================
   CARREGAR TODAS AS RESERVAS
   ================================================== */

function carregarTodasReservas() {

    let reservas = [];

    const chaves = [
        "reservasSaborQuintal",
        "reservaSaborQuintal",
        "reservas"
    ];

    for (let i = 0; i < chaves.length; i++) {

        const dadosSalvos = localStorage.getItem(chaves[i]);

        if (!dadosSalvos) continue;

        try {

            const dados = JSON.parse(dadosSalvos);

            if (Array.isArray(dados)) {
                reservas = dados;
            } else if (dados) {
                reservas = [dados];
            }

            if (reservas.length > 0) break;

        } catch (erro) {
            console.error("Erro ao carregar reservas:", erro);
        }

    }

    return reservas;
}


/* ==================================================
   VERIFICAR AVISOS DE RESERVA
   ================================================== */

function verificarAvisosReserva() {

    const usuarioLogado = localStorage.getItem("usuarioLogado");

    if (usuarioLogado !== "true") return;

    const emailCliente = (
        localStorage.getItem("emailUsuario") || ""
    ).trim().toLowerCase();

    const telefoneCliente = (
        localStorage.getItem("telefoneUsuario") || ""
    ).replace(/\D/g, "");

    const nomeCliente = (
        localStorage.getItem("nomeUsuario") || ""
    ).trim().toLowerCase();

    const reservas = carregarTodasReservas();

    const agora = new Date().getTime();
    const cincoDias = 5 * 24 * 60 * 60 * 1000;

    reservas.forEach(function(reserva) {

        const emailReserva = (
            reserva.emailUsuario ||
            reserva.email ||
            reserva.usuarioEmail ||
            ""
        ).trim().toLowerCase();

        const telefoneReserva = (
            reserva.telefone ||
            reserva.telefoneUsuario ||
            reserva.usuarioTelefone ||
            ""
        ).replace(/\D/g, "");

        const nomeReserva = (
            reserva.nomeUsuario ||
            reserva.nomeCliente ||
            reserva.nome ||
            reserva.cliente ||
            ""
        ).trim().toLowerCase();

        let pertenceAoCliente = false;

        if (emailCliente && emailReserva &&
            emailCliente === emailReserva) {

            pertenceAoCliente = true;

        } else if (telefoneCliente && telefoneReserva &&
                   telefoneCliente === telefoneReserva) {

            pertenceAoCliente = true;

        } else if (nomeCliente && nomeReserva &&
                   nomeCliente === nomeReserva) {

            pertenceAoCliente = true;
        }

        if (!pertenceAoCliente) return;

        const dataReserva =
            reserva.dataReserva ||
            reserva.data ||
            reserva.dia;

        const horarioReserva =
            reserva.horario ||
            reserva.hora ||
            "00:00";

        if (!dataReserva) return;

        let dataCompleta;

        if (typeof dataReserva === "number") {

            dataCompleta = dataReserva;

        } else {

            dataCompleta = new Date(
                dataReserva + "T" + horarioReserva + ":00"
            ).getTime();

        }

        if (isNaN(dataCompleta)) return;

        const inicioAviso = dataCompleta - cincoDias;

        const identificador =
            reserva.id ||
            dataCompleta + "_" +
            (reserva.mesa || reserva.numeroMesa || "mesa");

        const chaveAviso = "avisoReservaEnviado_" + identificador;

        if (
            agora >= inicioAviso &&
            agora < dataCompleta &&
            !localStorage.getItem(chaveAviso)
        ) {

            const dataFormatada =
                new Date(dataCompleta).toLocaleDateString("pt-BR");

            const ambiente =
                reserva.ambiente ||
                reserva.local ||
                "não informado";

            const mesa =
                reserva.mesa ||
                reserva.numeroMesa ||
                "não informada";

            const pessoas =
                reserva.pessoas ||
                reserva.quantidadePessoas ||
                reserva.quantidade ||
                "não informado";

            const mensagem =
                "Sua reserva no Sabor do Quintal está próxima!\n\n" +
                "Data: " + dataFormatada + "\n" +
                "Horário: " + horarioReserva + "\n" +
                "Pessoas: " + pessoas + "\n" +
                "Ambiente: " + ambiente + "\n" +
                "Mesa: " + mesa;

            localStorage.setItem(chaveAviso, "true");

            alert(mensagem);

            if (
                "Notification" in window &&
                Notification.permission === "granted"
            ) {

                new Notification("Sabor do Quintal", {
                    body:
                        "Sua reserva está próxima: " +
                        dataFormatada +
                        " às " +
                        horarioReserva + ".",
                    icon: "/img/logo.png"
                });

            }

        }

    });
}


/* ==================================================
   PERMISSÃO PARA NOTIFICAÇÕES
   ================================================== */

function pedirPermissaoNotificacao() {

    if (
        "Notification" in window &&
        Notification.permission === "default"
    ) {
        Notification.requestPermission();
    }
}


/* ==================================================
   EVENTOS DO PERFIL E DAS RESERVAS
   ================================================== */

document.addEventListener("DOMContentLoaded", function() {

    const telaPerfil = document.getElementById("telaPerfil");
    const telaReservas = document.getElementById("telaReservas");

    configurarFotoPerfil();

    if (telaPerfil) {

        telaPerfil.addEventListener("click", function(evento) {

            if (evento.target === this) {
                fecharPerfil();
            }

        });

    }

    if (telaReservas) {

        telaReservas.addEventListener("click", function(evento) {

            if (evento.target === this) {
                fecharReservas();
            }

        });

    }

    pegarDadosLogin();

    pedirPermissaoNotificacao();

    verificarAvisosReserva();

    document.addEventListener("keydown", function(evento) {

        if (evento.key === "Escape") {

            if (telaPerfil) {
                telaPerfil.classList.remove("ativa");
            }

            if (telaReservas) {
                telaReservas.classList.remove("ativa");
            }

            document.body.style.overflow = "";
        }

    });

});


/* ==================================================
   VERIFICAR RESERVAS A CADA 1 MINUTO
   ================================================== */

setInterval(verificarAvisosReserva, 60000);
