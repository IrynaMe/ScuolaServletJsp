package main.servlet;

import java.time.LocalDateTime;

public class Prova {
	
	private LocalDateTime dataOra;
	private String cfAllievo;
	private String cfDocente;
	private String nomeMateria;
	private int voto;
	private int abilitato;

	public Prova(LocalDateTime dataOra, String cfAllievo, String cfDocente, String nomeMateria, int voto) {
		this.dataOra = dataOra;
		this.cfAllievo = cfAllievo;
		this.cfDocente = cfDocente;
		this.nomeMateria = nomeMateria;
		this.voto = voto;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public int getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(int abilitato) {
		this.abilitato = abilitato;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public String getCfAllievo() {
		return cfAllievo;
	}

	public void setCfAllievo(String cfAllievo) {
		this.cfAllievo = cfAllievo;
	}

	public String getCfDocente() {
		return cfDocente;
	}

	public void setCfDocente(String cfDocente) {
		this.cfDocente = cfDocente;
	}

	public String getNomeMateria() {
		return nomeMateria;
	}

	public void setNomeMateria(String nomeMateria) {
		this.nomeMateria = nomeMateria;
	}

	@Override
	public String toString() {
		return "Prova:" +
				"dataOra=" + dataOra +
				", cfAllievo='" + cfAllievo + '\'' +
				", cfDocente='" + cfDocente + '\'' +
				", nomeMateria='" + nomeMateria + '\'' +
				", voto=" + voto +
				", abilitato=" + abilitato;
	}
}
