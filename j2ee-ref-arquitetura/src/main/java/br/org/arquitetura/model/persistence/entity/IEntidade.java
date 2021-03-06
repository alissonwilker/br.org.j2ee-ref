package br.org.arquitetura.model.persistence.entity;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public interface IEntidade extends Serializable {

    int getId();

    void setId(@NotNull @Max(Integer.MAX_VALUE) int id);
}
