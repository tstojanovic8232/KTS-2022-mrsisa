package com.Reservations.DTO;

public class PromenaLozinkeDTO {
	private String staraLozinka;
	
	private String novaLozinka;
	
	private String novaPonovo;

	public PromenaLozinkeDTO() {
	}

	public PromenaLozinkeDTO(String staraLozinka, String novaLozinka, String novaPonovo) {
		this.staraLozinka = staraLozinka;
		this.novaLozinka = novaLozinka;
		this.novaPonovo = novaPonovo;
	}

	public String getStaraLozinka() {
		return staraLozinka;
	}

	public void setStaraLozinka(String staraLozinka) {
		this.staraLozinka = staraLozinka;
	}

	public String getNovaLozinka() {
		return novaLozinka;
	}

	public void setNovaLozinka(String novaLozinka) {
		this.novaLozinka = novaLozinka;
	}

	public String getNovaPonovo() {
		return novaPonovo;
	}

	public void setNovaPonovo(String novaPonovo) {
		this.novaPonovo = novaPonovo;
	}

	@Override
	public String toString() {
		return "PromenaLozinkeDTO [staraLozinka=" + staraLozinka + ", novaLozinka=" + novaLozinka + ", novaPonovo="
				+ novaPonovo + "]";
	}
	
	
}
