package br.org.arquitetura.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public interface IDto extends Serializable {

	public int getId();

	public void setId(@NotNull @Max(Integer.MAX_VALUE) int id);
}
