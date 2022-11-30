package br.com.estudantes.api.excecoes;


public class EntidadeCamposErroDetalhe extends ErroDetalhe{
	private String campo;
	private String msgCampo;
	
	public EntidadeCamposErroDetalhe(Builder builder) {
		setTitulo(builder.titulo);
		setStatus(builder.status);
		setDetalhe(builder.detalhe);
		setTimestamp(builder.timestamp);
		setMsgDesenvolvedor(builder.msgDesenvolvedor);
		this.campo = builder.campo;
		this.msgCampo = builder.msgCampo;
	}

	public static final class Builder{
		private String titulo;
		private int status;
		private String detalhe;
		private long timestamp;
		private String msgDesenvolvedor;
		private String campo;
		private String msgCampo;
		
		private Builder() {
		}
		
		public static Builder newBuilder() {
			return new Builder();
		}
		
		public Builder titulo(String titulo) {
			this.titulo = titulo;
			return this;
		}
		
		public Builder status(int status) {
			this.status = status;
			return this;
		}
		
		public Builder detalhe(String detalhe) {
			this.detalhe = detalhe;
			return this;
		}
		
		public Builder timestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public Builder msgDesenvolvedor(String msgDesenvolvedor) {
			this.msgDesenvolvedor = msgDesenvolvedor;
			return this;
		}
		
		public Builder campo(String campo) {
			this.campo = campo;
			return this;
		}
		
		public Builder msgCampo(String msgCampo) {
			this.msgCampo = msgCampo;
			return this;
		}

		public EntidadeCamposErroDetalhe build() {
			return new EntidadeCamposErroDetalhe(this);
		}
	}
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getMsgCampo() {
		return msgCampo;
	}
	public void setMsgCampo(String msgCampo) {
		this.msgCampo = msgCampo;
	}
	
	
}
