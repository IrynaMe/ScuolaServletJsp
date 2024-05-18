package main.servlet;

import java.time.LocalDate;

public class Persona {
	private String cf;
	private String nome;
	private String cognome;
	private String sesso;
	private String statoNascita;
	private String provinciaNascita;
	private String comuneNascita;
	private LocalDate dataNascita;
	private String email;
	private int abilitato; //1-true, 0 false- default 1 in db
	private Entita entita;

	public Persona(String cf, String nome, String cognome, String sesso, String statoNascita,
                   String provinciaNascita, String comuneNascita, LocalDate dataNascita,
                   String email) {
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.statoNascita = statoNascita;
		this.provinciaNascita = provinciaNascita;
		this.comuneNascita = comuneNascita;
		this.dataNascita = dataNascita;
		this.email = email;

	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(int abilitato) {
		this.abilitato = abilitato;
	}

	public Entita getEntita() {
		return entita;
	}

	public void setEntita(Entita entita) {
		this.entita = entita;
	}

	@Override
	public String toString() {
		return  entita +": "+
				"codice fiscale='" + cf + '\'' +
				", nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", sesso='" + sesso + '\'' +
				", statoNascita='" + statoNascita + '\'' +
				", provinciaNascita='" + provinciaNascita + '\'' +
				", comuneNascita='" + comuneNascita + '\'' +
				", dataNascita=" + dataNascita +
				", email='" + email+
				", abilitato='"+abilitato;
	}
} //