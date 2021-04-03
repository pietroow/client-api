package io.platformbuilders.client_api.domain.cliente;

import io.platformbuilders.client_api.domain.cliente.dto.ClienteCriarAtualizarDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_cliente", schema = "client_api")
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String nome;
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Cliente() {
        this.id = UUID.randomUUID();
    }

    public Cliente(String nome, String email, LocalDate dataNascimento) {
        this();
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public Cliente(ClienteCriarAtualizarDTO clienteCriarDTO) {
        this();
        this.nome = clienteCriarDTO.getNome();
        this.email = clienteCriarDTO.getEmail();
        this.dataNascimento = clienteCriarDTO.getDataNascimento();
    }

    public void update(ClienteCriarAtualizarDTO clienteAtualizarDTO) {
        this.nome = clienteAtualizarDTO.getNome();
        this.email = clienteAtualizarDTO.getEmail();
        this.dataNascimento = clienteAtualizarDTO.getDataNascimento();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}


