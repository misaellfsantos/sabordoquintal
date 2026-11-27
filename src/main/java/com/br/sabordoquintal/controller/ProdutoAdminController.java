
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.Produto;
import com.br.sabordoquintal.service.ProdutoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.Optional;

@Controller
@RequestMapping("/admin/produtos")
public class ProdutoAdminController {

    private final ProdutoService produtoService;

    private final Path pastaImagens =
            Paths.get("src/main/resources/static/uploads");

    public ProdutoAdminController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // LISTAR PRODUTOS
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("produtos", produtoService.listar());
        model.addAttribute("produto", new Produto());

        return "admin/produtos";
    }

    // CADASTRAR OU ATUALIZAR PRODUTO
    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute Produto produto,
            @RequestParam(value = "arquivoImagem", required = false)
            MultipartFile arquivoImagem,
            Model model) {

        try {

            // Recuperar imagem anterior durante a edição
            if (produto.getId() != null) {

                Optional<Produto> existente =
                        produtoService.buscarPorId(produto.getId());

                if (existente.isPresent()) {

                    if (arquivoImagem == null || arquivoImagem.isEmpty()) {
                        produto.setImagem(existente.get().getImagem());
                    }
                }
            }

            // SALVAR NOVA IMAGEM
            if (arquivoImagem != null && !arquivoImagem.isEmpty()) {

                String tipo = arquivoImagem.getContentType();

                if (tipo == null || !tipo.startsWith("image/")) {
                    throw new RuntimeException(
                            "Selecione um arquivo de imagem válido."
                    );
                }

                Files.createDirectories(pastaImagens);

                String nomeOriginal = arquivoImagem.getOriginalFilename();

                String extensao = "";

                if (nomeOriginal != null && nomeOriginal.contains(".")) {
                    extensao = nomeOriginal.substring(
                            nomeOriginal.lastIndexOf(".")
                    ).toLowerCase();
                }

                String nomeArquivo = UUID.randomUUID() + extensao;

                Path caminho = pastaImagens.resolve(nomeArquivo);

                Files.copy(arquivoImagem.getInputStream(), caminho);

                produto.setImagem("/uploads/" + nomeArquivo);
            }

            produtoService.salvar(produto);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao salvar imagem do produto.", e
            );
        }

        return "redirect:/admin/produtos";
    }

    // EDITAR PRODUTO
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Optional<Produto> produto =
                produtoService.buscarPorId(id);

        if (produto.isEmpty()) {
            return "redirect:/admin/produtos";
        }

        model.addAttribute("produto", produto.get());
        model.addAttribute("produtos", produtoService.listar());

        return "admin/produtos";
    }

    // EXCLUIR PRODUTO
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        produtoService.excluir(id);

        return "redirect:/admin/produtos";
    }
}
