package br.com.fiap.library.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDTO {

    private Integer id;
    private String titulo;
    private Integer quantidadeDePaginas;
    private String ISBN;
    private ZonedDateTime dataLancamento;
    private AutorDTO autor;

    public BookDTO(CreateBookDTO dto) {
        this.titulo = dto.getTitulo();
        this.quantidadeDePaginas = dto.getQuantidadeDePaginas();
        this.ISBN = dto.getISBN();
        this.dataLancamento = dto.getDataLancamento();
    }
}