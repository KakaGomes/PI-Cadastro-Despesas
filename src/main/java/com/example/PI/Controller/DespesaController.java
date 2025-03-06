package com.example.PI.Controller;

import com.example.PI.Entities.DespesaEntities;
import com.example.PI.Entities.FornecedorEntities;
import com.example.PI.Entities.Usuario;
import com.example.PI.Repositories.DespesaRepository;
import com.example.PI.Repositories.FornecedorRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

@Controller
@RequestMapping("/compras")
public class DespesaController {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public List<DespesaEntities> listarCompras() {
        return despesaRepository.findAll();
    }

    @GetMapping("/novo")
    public String mostrarFormularioCompra(Model model) {
        List<FornecedorEntities> fornecedores = fornecedorRepository.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "cadastro_compras"; 
    }
     
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String cadastrarCompra(
            @RequestParam Long fornecedor_id,
            @RequestParam String numero_pedido,
            @RequestParam double valor,
            @RequestParam String data_pedido,
            @RequestParam(required = false) String data_pagamento,
            @RequestParam(required = false) String data_entrega,
            @RequestParam(required = false) String descricao,
            HttpSession session) { // Adiciona a sessão para pegar o usuário logado

        // Recupera o usuário logado na sessão
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "Erro: Usuário não autenticado!";
        }

        Optional<FornecedorEntities> fornecedorOpt = fornecedorRepository.findById(fornecedor_id);
        if (fornecedorOpt.isEmpty()) {
            return "Erro: Fornecedor não encontrado!";
        }

        DespesaEntities novaCompra = new DespesaEntities();
        novaCompra.setFornecedor(fornecedorOpt.get());
        novaCompra.setNumeroPedido(numero_pedido);
        novaCompra.setValor(BigDecimal.valueOf(valor));
        novaCompra.setDataPedido(LocalDate.parse(data_pedido));
        if (data_pagamento != null) novaCompra.setDataPagamento(LocalDate.parse(data_pagamento));
        if (data_entrega != null) novaCompra.setDataEntrega(LocalDate.parse(data_entrega));
        novaCompra.setDescricao(descricao);
        novaCompra.setStatus(DespesaEntities.StatusCompra.PENDENTE);
        novaCompra.setComprador(usuarioLogado.getUsuario()); 

        despesaRepository.save(novaCompra);

        return "redirect:/diretor";
    }

    @GetMapping("/fornecedores")
    @ResponseBody
    public List<FornecedorEntities> getFornecedores() {
        return fornecedorRepository.findAll();
    }

    @GetMapping("/pendentes")
    @ResponseBody
    public List<DespesaEntities> listarComprasPendentes() {
        return despesaRepository.findByStatus(DespesaEntities.StatusCompra.PENDENTE);
    }

    @GetMapping("/aprovadas")
    @ResponseBody
    public List<DespesaEntities> listarComprasAprovadas() {
        return despesaRepository.findByStatus(DespesaEntities.StatusCompra.APROVADA);
    }

    @PutMapping("/aprovar/{id}")
    @ResponseBody
    public String aprovarCompra(@PathVariable Long id) {
        Optional<DespesaEntities> compraOpt = despesaRepository.findById(id);
        if (compraOpt.isPresent()) {
            DespesaEntities compra = compraOpt.get();
            compra.setStatus(DespesaEntities.StatusCompra.APROVADA);
            despesaRepository.save(compra);
            return "redirect:/diretor";
        }
        return "Erro: Compra não encontrada.";
    }

    @PutMapping("/cancelar/{id}")
    @ResponseBody
    public String cancelarCompra(@PathVariable Long id) {
        Optional<DespesaEntities> compraOpt = despesaRepository.findById(id);
        if (compraOpt.isPresent()) {
            DespesaEntities compra = compraOpt.get();
            compra.setStatus(DespesaEntities.StatusCompra.CANCELADA);
            despesaRepository.save(compra);
            return "Compra cancelada!";
        }
        return "Erro: Compra não encontrada.";
    }

    @GetMapping("/exportar-pdf")
    public void exportarComprasAprovadasPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=compras_aprovadas.pdf");

        List<DespesaEntities> comprasAprovadas = despesaRepository.findByStatus(DespesaEntities.StatusCompra.APROVADA);

        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (DocumentException e) {
            throw new IOException(e);
        }

        document.open();

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font dataFont = new Font(Font.HELVETICA, 12);

        Paragraph title = new Paragraph("Relatório de Compras Aprovadas", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 2, 2, 2, 2});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell(new Phrase("Fornecedor", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Valor", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Data Pedido", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Data Pagamento", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Data Entrega", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Número Pedido", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        for (DespesaEntities compra : comprasAprovadas) {
            table.addCell(new Phrase(compra.getFornecedor().getNome(), dataFont));
            table.addCell(new Phrase("R$ " + compra.getValor().toString(), dataFont));
            table.addCell(new Phrase(compra.getDataPedido() != null ? compra.getDataPedido().toString() : "Não definida", dataFont));
            table.addCell(new Phrase(compra.getDataPagamento() != null ? compra.getDataPagamento().toString() : "Não definida", dataFont));
            table.addCell(new Phrase(compra.getDataEntrega() != null ? compra.getDataEntrega().toString() : "Não definida", dataFont));
            table.addCell(new Phrase(compra.getNumeroPedido(), dataFont));
        }

        document.add(table);
        document.close();
    }



@GetMapping("/relatorio")
@ResponseBody
public ResponseEntity<?> filtrarCompras(
        @RequestParam(required = false) String fornecedor,
        @RequestParam(required = false) String dataInicio,
        @RequestParam(required = false) String dataFim) {

    try {
        LocalDate inicio = (dataInicio != null && !dataInicio.isEmpty()) ? LocalDate.parse(dataInicio) : null;
        LocalDate fim = (dataFim != null && !dataFim.isEmpty()) ? LocalDate.parse(dataFim) : null;

        List<DespesaEntities> compras = despesaRepository.filtrarCompras(fornecedor, inicio, fim);

        if (compras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma compra encontrada.");
        }

        return ResponseEntity.ok(compras);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar compras: " + e.getMessage());
    }
}


}