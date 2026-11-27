
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.pedido;
import com.br.sabordoquintal.model.itemPedido;
import com.br.sabordoquintal.model.clientes;
import com.br.sabordoquintal.repository.ClientesRepository;
import com.br.sabordoquintal.service.PedidoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClientesRepository clientesRepository;

    public PedidoController(
            PedidoService pedidoService,
            ClientesRepository clientesRepository) {

        this.pedidoService = pedidoService;
        this.clientesRepository = clientesRepository;
    }

    // Abrir página de pedidos do cliente
    @GetMapping("/pedido")
    public String abrirPedido() {
        return "pedido";
    }

    // Receber e salvar pedido do cliente
    @PostMapping("/api/pedidos")
    @ResponseBody
    public Map<String, Object> salvarPedido(
            @RequestBody PedidoRequest dados) {

        if (dados == null ||
                dados.email == null ||
                dados.email.isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail do cliente não informado."
            );
        }

        // Localizar cliente pelo e-mail ou cadastrar
        clientes cliente = clientesRepository
                .findByEmail(dados.email)
                .orElseGet(() -> clientesRepository.save(
                        new clientes(
                                dados.nome != null ? dados.nome : "Cliente",
                                dados.email,
                                dados.telefone
                        )
                ));

        // Atualizar dados do cliente, caso já exista
        if (dados.nome != null && !dados.nome.isBlank()) {
            cliente.setNome(dados.nome);
        }

        if (dados.telefone != null) {
            cliente.setTelefone(dados.telefone);
        }

        cliente = clientesRepository.save(cliente);

        // Criar pedido
        pedido novoPedido = new pedido(
                cliente,
                BigDecimal.ZERO,
                "Recebido"
        );

        BigDecimal total = BigDecimal.ZERO;

        List<ItemRequest> todosItens = new ArrayList<>();

        if (dados.pratos != null) {
            todosItens.addAll(dados.pratos);
        }

        if (dados.bebidas != null) {
            todosItens.addAll(dados.bebidas);
        }

        if (todosItens.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O pedido não possui itens."
            );
        }

        // Adicionar pratos e bebidas
        for (ItemRequest dadosItem : todosItens) {

            if (dadosItem.nome == null ||
                    dadosItem.nome.isBlank() ||
                    dadosItem.preco == null ||
                    dadosItem.preco.compareTo(BigDecimal.ZERO) < 0 ||
                    dadosItem.quantidade == null ||
                    dadosItem.quantidade < 1 ||
                    dadosItem.quantidade > 20) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Dados inválidos em um dos itens do pedido."
                );
            }

            itemPedido item = new itemPedido();

            item.setNomeProduto(dadosItem.nome);
            item.setQuantidade(dadosItem.quantidade);
            item.setPrecoUnitario(dadosItem.preco);
            item.setObservacao(dadosItem.observacao);
            item.setSabor(dadosItem.sabor);

            novoPedido.adicionarItem(item);

            total = total.add(item.getSubtotal());
        }

        novoPedido.setTotal(total);

        // Salvar pedido e seus itens
        pedido pedidoSalvo = pedidoService.salvar(novoPedido);

        return Map.of(
                "mensagem", "Pedido salvo com sucesso!",
                "id", pedidoSalvo.getId(),
                "total", total
        );
    }

    // Listar pedidos no painel administrativo
    @GetMapping("/admin/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        return "admin/pedidos";
    }

    // Excluir pedido
    @PostMapping("/admin/pedidos/excluir/{id}")
    public String excluirPedido(@PathVariable Long id) {
        pedidoService.excluir(id);
        return "redirect:/admin/pedidos";
    }

    // Atualizar status do pedido
    @PostMapping("/admin/pedidos/status/{id}")
    public String atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        pedido pedidoExistente = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pedido não encontrado"
                ));

        pedidoExistente.setStatus(status);
        pedidoService.salvar(pedidoExistente);

        return "redirect:/admin/pedidos";
    }

    // Estrutura dos dados recebidos da página
    public static class PedidoRequest {
        public String nome;
        public String email;
        public String telefone;

        public List<ItemRequest> pratos;
        public List<ItemRequest> bebidas;
    }

    public static class ItemRequest {
        public String nome;
        public BigDecimal preco;
        public Integer quantidade;
        public String observacao;
        public String sabor;
    }
}
