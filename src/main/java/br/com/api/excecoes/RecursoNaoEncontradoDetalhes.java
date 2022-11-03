package br.com.api.excecoes;

public class RecursoNaoEncontradoDetalhes {
	private String titulo;
	private int status;
	private String detalhe;
	private long timestamp;
	private String msgDesenvolvedor;
	
	private RecursoNaoEncontradoDetalhes() {
	}
	
	public RecursoNaoEncontradoDetalhes(Builder builder) {
		titulo = builder.titulo;
		status = builder.status;
		detalhe = builder.detalhe;
		timestamp = builder.timestamp;
		msgDesenvolvedor = builder.msgDesenvolvedor; 
	}

	public static final class Builder{
		private String titulo;
		private int status;
		private String detalhe;
		private long timestamp;
		private String msgDesenvolvedor;
		
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

		public RecursoNaoEncontradoDetalhes build() {
			return new RecursoNaoEncontradoDetalhes(this);
		}
	}
	
	public String getTitulo() {
		return titulo;
	}
	public int getStatus() {
		return status;
	}
	public String getDetalhe() {
		return detalhe;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public String getMsgDesenvolvedor() {
		return msgDesenvolvedor;
	}
	
	
	
}
